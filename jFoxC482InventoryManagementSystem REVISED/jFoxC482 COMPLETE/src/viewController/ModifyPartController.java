package viewController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * @author Josh Fox
 * Student ID: #001053580
 * Inventory Management System
 * WGU C482 Software 1
 */


public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField ModifyPartID;
    @FXML
    private TextField ModifyPartName;
    @FXML
    private TextField ModifyPartStock;
    @FXML
    private TextField ModifyPartPrice;
    @FXML
    private TextField ModifyPartMax;
    @FXML
    private TextField ModifyPartMin;
    @FXML
    private TextField ModCompNameMachID;
    @FXML
    private Label ModLabelCompName;

    private int partCounter;
    private Part modifyPart;


    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //Modify part radio buttons
    public void SetModPartInOut(ActionEvent event) {
        if (InHouseRadio.isSelected()) {
            ModLabelCompName.setText("Machine ID");
            ModCompNameMachID.clear();
            ModCompNameMachID.setPromptText("Machine ID");
        }
        if (OutsourcedRadio.isSelected()) {
            ModLabelCompName.setText("Company Name");
            ModCompNameMachID.clear();
            ModCompNameMachID.setPromptText("Company Name");
        }
    }


    //Retrieve Part
    public void GetPart(Part part) {
        modifyPart = part;
        ModifyPartID.setText(String.valueOf(part.getId()));
        ModifyPartName.setText(part.getName());
        ModifyPartStock.setText(String.valueOf(part.getStock()));
        ModifyPartPrice.setText(String.valueOf(part.getPrice()));
        ModifyPartMax.setText(String.valueOf(part.getMax()));
        ModifyPartMin.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            ModCompNameMachID.setText(String.valueOf(((InHouse) part).getMachineId()));
            ModLabelCompName.setText("Machine ID");
            InHouseRadio.setSelected(true);
        }
        else if (part instanceof Outsourced) {
            ModCompNameMachID.setText(((Outsourced) part).getCompanyName());
            OutsourcedRadio.setSelected(true);
        }
    }


    //Saves the modified part
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {
        if (InHouseRadio.isSelected()) {

            //For max and min inventory values in the exception set
            int max = Integer.parseInt(ModifyPartMax.getText());
            int min = Integer.parseInt(ModifyPartMin.getText());
            int invMax = Integer.parseInt(ModifyPartStock.getText());

            int modifiedPart = Inventory.lookupPartIndex(modifyPart.getId());
            Part newPart = new InHouse(
                    modifyPart.getId(),
                    ModifyPartName.getText(),
                    Double.parseDouble(ModifyPartPrice.getText()),
                    Integer.parseInt(ModifyPartStock.getText()),
                    Integer.parseInt(ModifyPartMin.getText()),
                    Integer.parseInt(ModifyPartMax.getText()),
                    Integer.parseInt(ModCompNameMachID.getText())
            );

            //EXCEPTION SET 1 REQUIREMENT
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Quantity minimum is larger than maximum.");
                alert.showAndWait();
                return;
            }
            if (invMax > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Inventory quantity exceeds maximum stock allowed.");
                alert.showAndWait();
                return;
            }

            Inventory.updatePart(modifiedPart, newPart);

            changeScreen(event, "MainScreen.fxml");
        }
        else if (OutsourcedRadio.isSelected()) {

            int max = Integer.parseInt(ModifyPartMax.getText());
            int min = Integer.parseInt(ModifyPartMin.getText());
            int invMax = Integer.parseInt(ModifyPartStock.getText());

            int moddedPart = Inventory.lookupPartIndex(modifyPart.getId());
            Part newPart = new Outsourced(
                    modifyPart.getId(),
                    ModifyPartName.getText(),
                    Double.parseDouble(ModifyPartPrice.getText()),
                    Integer.parseInt(ModifyPartStock.getText()),
                    Integer.parseInt(ModifyPartMin.getText()),
                    Integer.parseInt(ModifyPartMax.getText()),
                    ModCompNameMachID.getText()
        );

            //EXCEPTION SET 1 REQUIREMENT
        if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Quantity minimum is larger than maximum.");
                alert.showAndWait();
                return;
        }
        if (invMax > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Inventory quantity exceeds maximum stock allowed.");
                alert.showAndWait();
                return;
            }

            Inventory.updatePart(moddedPart, newPart);

            changeScreen(event, "MainScreen.fxml");
        }
    }


    //Exit to the main screen
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {

        //EXCEPTION SET 2 REQUIREMENT
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Changes not Saved");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            changeScreen(event, "MainScreen.fxml");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partCounter = Inventory.PartCounter();
        ModifyPartID.setText(Integer.toString(partCounter));
    }
}
