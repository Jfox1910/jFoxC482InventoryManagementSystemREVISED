package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Josh Fox
 * Student ID: #001053580
 * Inventory Management System
 * WGU C482 Software 1
 */


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> searchedProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts() { return allParts; }
    public static ObservableList<Product> getAllProducts() { return allProducts; }

    private static int partCounter = 6;
    private static int productCounter = 4;


    //PARTS---->
    public static Part lookupPart(int partId) {
        int index = 1;
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId) {
                index = i;
            }
        }
        return allParts.get(index);
    }


    public static ObservableList<Part> lookupPart(String partName) {
        searchedParts.clear();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().toLowerCase().equals(partName.toLowerCase())) {
                searchedParts.add(allParts.get(i));
            }
        }
        return searchedParts;
    }


    public static int lookupPartIndex(int inputID) {
        for (Part lookupPart : allParts) {
            if (lookupPart.getId() == inputID) {
                return allParts.indexOf(lookupPart);
            }
        }
        return -1;
    }


    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void updatePart(int moddedPart, Part modifiedPart) {
        allParts.set(moddedPart, modifiedPart);
    }


    //PRODUCTS---->
    public static Product lookupProduct(int productID) {
        for (Product p : allProducts) {
            if (p.getId() == productID) {
                return p;
            }
        }
        return null;
    }


    public static ObservableList<Product> lookupProduct(String productName) {
        searchedProducts.clear();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toLowerCase().equals(productName.toLowerCase())) {
                searchedProducts.add(allProducts.get(i));
            }
        }
        return searchedProducts;
    }


    public static int lookupProductIndex(int productID) {
        for (Product lookupProduct : allProducts) {
            if (lookupProduct.getId() == productID) {
                return allProducts.indexOf(lookupProduct);
            }
        }
        return -1;
    }


    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }
    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }


    //Part and product ID incrementers
    public static int PartCounter() {
        partCounter++;
        return partCounter;
    }


    public static int ProductCounter() {
        productCounter++;
        return productCounter;
    }
}
