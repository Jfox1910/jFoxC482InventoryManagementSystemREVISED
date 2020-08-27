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
import javafx.scene.control.Alert.AlertType;
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
 * Name: Josh Fox
 * Student ID: #001053580
 * Inventory Management System
 * WGU C482 Software 1
 */

public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField MainPartSearch;
    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableView<Product> ProductsTableView;
    @FXML
    private TableColumn<Part, Integer> PartIDColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> PartPriceColumn;
    @FXML
    private TextField MainProductsSearch;
    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;
    @FXML
    private TableColumn<Product, String> ProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> ProductInventoryColumn;
    @FXML
    private TableColumn<Product, Double> ProductPriceColumn;

    ObservableList<Part> searchPart = FXCollections.observableArrayList();
    ObservableList<Product> searchProduct = FXCollections.observableArrayList();

    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //Search for a part by ID or name
    @FXML
    void onActionSearchPart(ActionEvent event) {

        try {
            try {
                int id = Integer.parseInt(MainPartSearch.getText());
                searchPart.add(Inventory.lookupPart(id));
            } catch (Exception e) {
                String name = MainPartSearch.getText();
                searchPart = Inventory.lookupPart(name);
            }


            //Alert if the part doesn't exist or the box is empty
            if (searchPart.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            }
            else {
                PartTableView.setItems(searchPart);
                PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        finally {
            if (MainPartSearch.getText().equals("")) {
                PartTableView.setItems(Inventory.getAllParts());
            }
        }
    }


    //Search for a product by ID or name
    @FXML
    void onActionSearchProduct(ActionEvent event) {

        try {
            try {
                int id = Integer.parseInt(MainProductsSearch.getText());
                searchProduct.add(Inventory.lookupProduct(id));
            }
            catch (Exception e) {
                String name = MainProductsSearch.getText();
                searchProduct = Inventory.lookupProduct(name);
            }

            //Alert if the product doesn't exist or the box is empty
            if (searchProduct.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            }
            else {
                ProductsTableView.setItems(searchProduct);
                ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        finally {
            if (MainProductsSearch.getText().equals("")) {
                ProductsTableView.setItems(Inventory.getAllProducts());
            }
        }
    }


    //Add a new part screen
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        changeScreen(event, "AddPart.fxml");
    }


    //Modify an existing part screen
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        Part part = PartTableView.getSelectionModel().getSelectedItem();
        if (part == null)
            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();
        ModifyPartController PartController = loader.getController();
        PartController.GetPart(part);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //Delete the selected part
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Part part = PartTableView.getSelectionModel().getSelectedItem();
        if (part == null)
            return;

        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("This will delete the selected Part!");
        alert.setContentText("Are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deletePart(part.getId());
        }

    }
    public boolean deletePart(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id)
                return Inventory.getAllParts().remove(part);
        }
        return false;
    }


    //Add a new product screen
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        changeScreen(event, "AddProduct.fxml");
    }


    //Modify an existing product screen
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        Product product = ProductsTableView.getSelectionModel().getSelectedItem();
        if (product == null)
            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();
        ModifyProductController ProductController = loader.getController();
        ProductController.getProduct(product);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //Delete the selected product
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        Product product = ProductsTableView.getSelectionModel().getSelectedItem();
        if (product == null)
            return;


        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("This will delete the selected Product.");
        alert.setContentText("Are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteProduct(product.getId());
        }
    }
    public boolean deleteProduct(int id) {

        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id)
                return Inventory.getAllProducts().remove(product);
        }
        return false;
    }


    //Exit the program
    @FXML
    void onActionExitProgram(ActionEvent event) {


        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("This will Exit the Program. Any unsaved changes will be lost.");
        alert.setContentText("Are you sure you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate Part TableView
        PartTableView.setItems(Inventory.getAllParts());
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate Products TableView
        ProductsTableView.setItems(Inventory.getAllProducts());
        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}