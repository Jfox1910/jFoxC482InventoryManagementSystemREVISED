package com.C482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 @author Josh Fox
  * Student ID: #001053580
  * Inventory Management System
  * WGU C482 Software 1
 */


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/viewController/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    //Pre-populated fields
    public static void main(String[] args) {

        //InHouse Parts
        Part InHousePart1 = new InHouse(1, "Lower Unit", 52.95, 10, 5, 75, 192);
        Part InHousePart2 = new InHouse(2, "Upper Unit", 120.95, 3, 2, 10, 191);
        Part InHousePart3 = new InHouse(3, "Screws", .02, 100, 25, 250, 190);

        //Outsourced Parts
        Part OutsourcedPart1 = new Outsourced(4, "Sheet Metal", 56.95, 18, 10, 20, "Baille Machining");
        Part OutsourcedPart2 = new Outsourced(5, "Anodizing", 100.95, 2, 1, 200, "A&L Finishing");
        Part OutsourcedPart3 = new Outsourced(6, "Actuator", 1800.95, 2, 2, 10, "Parker Hannifin");

        //Products and associated parts
        Product product1 = new Product(1, "PalletizUR", 78000.95, 4, 1, 4);
        product1.addAssociatedPart(InHousePart1);
        Product product2 = new Product(2, "PalletizUR Assist", 100000.95, 1, 1, 4);
        product2.addAssociatedPart(InHousePart2);
        Product product3 = new Product(3, "PalletizUR no UR", 45000.95, 4, 1, 4);
        product3.addAssociatedPart(OutsourcedPart3);
        Product product4 = new Product(4, "Shield", 100.95, 2, 1, 5);
        product4.addAssociatedPart(InHousePart3);


        //InHouse Parts
        Inventory.addPart(InHousePart1);
        Inventory.addPart(InHousePart2);
        Inventory.addPart(InHousePart3);

        //Outsourced Parts
        Inventory.addPart(OutsourcedPart1);
        Inventory.addPart(OutsourcedPart2);
        Inventory.addPart(OutsourcedPart3);

        //Products
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);

        launch(args);
    }
}


