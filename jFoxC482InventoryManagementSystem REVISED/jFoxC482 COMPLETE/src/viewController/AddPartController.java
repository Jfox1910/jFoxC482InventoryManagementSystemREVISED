package viewController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 *
 @author Josh Fox
 Student ID: #001053580
 Inventory Management System
 WGU C482 Software 1
 *
 */


public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField AddPartId;
    @FXML
    private TextField AddPartName;
    @FXML
    private TextField AddInventory;
    @FXML
    private TextField AddPrice;
    @FXML
    private TextField MinQty;
    @FXML
    private TextField MaxQty;
    @FXML
    private TextField CompNameMachID;
    @FXML
    private Label LabelCompanyName;

    private int partCounter;
    private boolean IsInHouse;


    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //Radio buttons
    public void SetInOut(ActionEvent event) {

        if(InHouseRadio.isSelected()) {
            CompNameMachID.setPromptText("Machine ID");
            CompNameMachID.clear();
            LabelCompanyName.setText("Machine ID");
        }
        if(OutsourcedRadio.isSelected()) {
            IsInHouse = false;
            CompNameMachID.clear();
            CompNameMachID.setPromptText("Company Name");
            LabelCompanyName.setText("Company Name");
        }
    }


    //Saves the new part
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        Inventory.PartCounter();
        String name = AddPartName.getText();
        int stock = Integer.parseInt(AddInventory.getText());
        double price = Double.parseDouble(AddPrice.getText());
        int max = Integer.parseInt(MaxQty.getText());
        int min = Integer.parseInt(MinQty.getText());

        try {
            if(min > max){


                //EXCEPTION SET 1 REQUIREMENT
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Quantity minimum is larger than maximum.");
                alert.showAndWait();
                return;
            }
            else{
                if (IsInHouse){
                    int machineId = Integer.parseInt(CompNameMachID.getText());
                    InHouse InHousePart = new InHouse(partCounter, name, price, stock, min, max, machineId);
                    Inventory.addPart(InHousePart);
                }
                else{
                    String companyName = CompNameMachID.getText();
                    Outsourced OutsourcedPart = new Outsourced (partCounter, name, price, stock, min, max, companyName);
                    Inventory.addPart(OutsourcedPart);
                }
            }
        }


        //EXCEPTION SET 1 REQUIREMENT
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("All fields must be filled.");
            alert.showAndWait();
            return;
        }

        changeScreen(event, "MainScreen.fxml");
    }


    //Confirms exit to the main screen
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {


        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            changeScreen(event, "MainScreen.fxml");

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partCounter = Inventory.PartCounter();
        AddPartId.setText(Integer.toString(partCounter));
    }
}