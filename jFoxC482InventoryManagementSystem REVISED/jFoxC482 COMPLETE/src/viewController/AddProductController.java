package viewController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.Inventory;
import model.Part;
import model.Product;

/**
 *
 @author Josh Fox
 Student ID: #001053580
 Inventory Management System
 WGU C482 Software 1
 *
 */


public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;

    Product product;

    @FXML private TextField AddProductSearchStr;
    @FXML private TextField AddProductId;
    @FXML private TextField AddProductName;
    @FXML private TextField AddProductStock;
    @FXML private TextField AddProductPrice;
    @FXML private TextField AddProductMax;
    @FXML private TextField AddProductMin;
    @FXML private TableView<Part> TableView1;
    @FXML private TableColumn<Part, Integer> AddProdIdColumn1;
    @FXML private TableColumn<Part, String> AddProdNameColumn1;
    @FXML private TableColumn<Part, Integer> AddProdInvColumn1;
    @FXML private TableColumn<Part, Double> AddProdPriceColumn1;
    @FXML private TableView<Part> TableView2;
    @FXML private TableColumn<Part, Integer> AddProdIdColumn2;
    @FXML private TableColumn<Part, String> AddProdNameColumn2;
    @FXML private TableColumn<Part, Integer> AddProdInvColumn2;
    @FXML private TableColumn<Part, Double> AddProdPriceColumn2;

    ObservableList<Part> search = FXCollections.observableArrayList();
    private int productCounter;


    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @FXML
    void onActionSearchProduct(ActionEvent event) {
        try {
            try{
                int id = Integer.parseInt(AddProductSearchStr.getText());
                search.add(Inventory.lookupPart(id));
            }
            catch(Exception e){
                String name = AddProductSearchStr.getText();
                search = Inventory.lookupPart(name);
            }
            if(search.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            }
            else {
                TableView1.setItems(search);
                AddProdIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
                AddProdNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
                AddProdInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
                AddProdPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }


        //EXCEPTION SET 1 REQUIREMENT
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Product does not exist!");
        }
        if (AddProductSearchStr.getText().equals("")){
            TableView1.setItems(Inventory.getAllParts());
        }
    }


    //Add a selected part to the current product
    @FXML
    void onActionAddPart(ActionEvent event) {

        Part part = TableView1.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        else {
            product.getAllAssociatedParts().add(part);
        }
    }


    //Delete selected part from the current product
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Part part = TableView2.getSelectionModel().getSelectedItem();
        if(part == null)
            return;

        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to delete the product you have selected!");
        alert.setContentText("Are you sure you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            product.deleteAssociatedPart(part.getId());
        }
    }


    //Save the product method
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {

            int id = Inventory.ProductCounter();
            String name = AddProductName.getText();
            int stock = Integer.parseInt(AddProductStock.getText());
            Double price = Double.parseDouble(AddProductPrice.getText());
            int max = Integer.parseInt(AddProductMax.getText());
            int min = Integer.parseInt(AddProductMin.getText());

            Inventory.addProduct(new Product(id, name, price, stock, min, max, product.getAllAssociatedParts()));


            //EXCEPTION SET 1 REQUIREMENT
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Quantity minimum is larger than maximum.");
                alert.showAndWait();
                return;
            } if (stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Inventory quantity exceeds maximum stock allowed.");
                alert.showAndWait();
                return;
            }
            changeScreen(event, "MainScreen.fxml");
        }


    //Exit to the main screen
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {

        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Changes not Saved");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        product = new Product();

        //Increments the ID field
        AddProductId.setText(Integer.toString(Inventory.ProductCounter()));

        //Populate TableView1
        TableView1.setItems(Inventory.getAllParts());
        AddProdIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdInvColumn1.setCellValueFactory(new PropertyValueFactory<>("inv"));
        AddProdPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate TableView2
        TableView2.setItems(product.getAllAssociatedParts());
        AddProdIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdInvColumn2.setCellValueFactory(new PropertyValueFactory<>("inv"));
        AddProdPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

        productCounter = Inventory.ProductCounter();
        AddProductId.setText(Integer.toString(productCounter));
    }
}
