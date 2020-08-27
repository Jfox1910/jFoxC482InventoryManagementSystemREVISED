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

public class ModifyProductController implements Initializable {

    Stage stage;

    Product product;
    Product modifyProduct;

    ObservableList<Part> searchProduct = FXCollections.observableArrayList();
    ObservableList<Part> partsAvailableList;


    @FXML
    private TextField ModifyProductId;
    @FXML
    private TextField ModProdNameStr;
    @FXML
    private TextField ModProdInventoryInt;
    @FXML
    private TextField ModProdPriceDbl;
    @FXML
    private TextField ModProdMaxInt;
    @FXML
    private TextField ModProdMinInt;
    @FXML
    private TextField ModProdSearch;

    @FXML
    private TableView<Part> TableView1;
    @FXML
    private TableColumn<Part, Integer> ModProdIdColumn1;
    @FXML
    private TableColumn<Part, String> ModProdNameColumn1;
    @FXML
    private TableColumn<Part, Integer> ModProdInvColumn1;
    @FXML
    private TableColumn<Part, Double> ModProdPriceColumn1;

    @FXML
    private TableView<Part> TableView2;
    @FXML
    private TableColumn<Part, Integer> ModProdIdColumn2;
    @FXML
    private TableColumn<Part, String> ModProdNameColumn2;
    @FXML
    private TableColumn<Part, Integer> ModProdInvColumn2;
    @FXML
    private TableColumn<Part, Double> ModProdPriceColumn2;


    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //Retrieve Products and any associated parts
    public void getProduct(Product product2) {
        modifyProduct = product2;
        ModifyProductId.setText(String.valueOf(modifyProduct.getId()));
        ModProdNameStr.setText(modifyProduct.getName());
        ModProdInventoryInt.setText(String.valueOf(modifyProduct.getStock()));
        ModProdPriceDbl.setText(String.valueOf(modifyProduct.getPrice()));
        ModProdMaxInt.setText(String.valueOf(modifyProduct.getMax()));
        ModProdMinInt.setText(String.valueOf(modifyProduct.getMin()));

        product.getAllAssociatedParts().addAll(modifyProduct.getAllAssociatedParts());
        partsAvailableList =  FXCollections.observableArrayList(Inventory.getAllParts());

        setTableView2(modifyProduct.getAllAssociatedParts());
        setTableView1(partsAvailableList);
    }


    //set tableviews with available and added parts
    public void setTableView2(ObservableList<Part> partsAddedList) {
        ModProdIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdInvColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableView2.setItems(partsAddedList);
    }

    public void setTableView1(ObservableList<Part> partsAvailableList) {
        ModProdIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableView1.setItems(partsAvailableList);
    }


    //Search for a part from the modified section by ID or name
    @FXML
    void onActionSearchProduct(ActionEvent event) {
        try {
            try {
                int id = Integer.parseInt(ModProdSearch.getText());
                searchProduct.add(Inventory.lookupPart(id));
            } catch (Exception e) {
                String name = ModProdSearch.getText();
                searchProduct = Inventory.lookupPart(name);
            }
            if (searchProduct.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            } else {
                TableView1.setItems(searchProduct);
                ModProdIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
                ModProdNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
                ModProdInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
                ModProdPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        finally {
            if (ModProdSearch.getText().equals("")){
                TableView1.setItems(Inventory.getAllParts());
            }
        }
    }


    //Add a selected part to the current product
    public void onActionAddPart(ActionEvent event) throws IOException {
        Part selectedPart = TableView1.getSelectionModel().getSelectedItem();
        modifyProduct.addAssociatedPart(selectedPart);
        setTableView1(partsAvailableList);
        setTableView2(modifyProduct.getAllAssociatedParts());
    }


    //Delete selected part from the current product
    public void onActionDeletePart(ActionEvent event) throws IOException {

        Part part = TableView2.getSelectionModel().getSelectedItem();
        if (part == null)
            return;

        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this Part from the list?");
        alert.setContentText("Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part selectedPart = TableView2.getSelectionModel().getSelectedItem();

            modifyProduct.deleteAssociatedPart(selectedPart.getId());
            partsAvailableList.add(selectedPart);
            setTableView1(partsAvailableList);
            setTableView2(modifyProduct.getAllAssociatedParts());

        }
    }


    //Save the product and any associated parts
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {


        //for max and min exception set
        int max = Integer.parseInt(ModProdMaxInt.getText());
        int min = Integer.parseInt(ModProdMinInt.getText());
        int invMax = Integer.parseInt(ModProdInventoryInt.getText());

        modifyProduct.setName(ModProdNameStr.getText());
        modifyProduct.setStock(Integer.parseInt(ModProdInventoryInt.getText()));
        modifyProduct.setPrice(Double.parseDouble(ModProdPriceDbl.getText()));
        modifyProduct.setMax(Integer.parseInt(ModProdMaxInt.getText()));
        modifyProduct.setMin(Integer.parseInt(ModProdMinInt.getText()));

        Inventory.updateProduct(Inventory.lookupProductIndex(modifyProduct.getId()), modifyProduct);


        //EXCEPTION SET 1 REQUIREMENT
        if(min > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Quantity minimum is larger than maximum.");
            alert.showAndWait();
            return;
        } if (invMax > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Inventory quantity exceeds maximum stock allowed.");
            alert.showAndWait();
            return;
        }
        
        changeScreen(event, "MainScreen.fxml");
    }


    //Cancel button exits the modify screen and returns to the main screen
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {


        //EXCEPTION SET 2 REQUIREMENT
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Changes not Saved");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            changeScreen(event, "MainScreen.fxml");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        product = new Product();

    }
}

