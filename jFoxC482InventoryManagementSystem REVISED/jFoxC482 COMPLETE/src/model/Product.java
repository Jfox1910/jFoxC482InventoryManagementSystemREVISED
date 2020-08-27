package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 @author Josh Fox
  * Student ID: #001053580
  * Inventory Management System
  * WGU C482 Software 1
 */

public class Product {

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private int inv;
    private Double price;
    private int max;
    private int min;


    //Constructor
    public Product(int id, String name, Double price, int inv, int min, int max, ObservableList associatedParts) {
        this.id = id;
        this.name = name;
        this.inv = inv;
        this.price = price;
        this.max = max;
        this.min = min;
        this.associatedParts.addAll(associatedParts);
    }


    public Product(int id, String name, Double price, int inv, int min, int max) {
        this.id = id;
        this.name = name;
        this.inv = inv;
        this.price = price;
        this.max = max;
        this.min = min;
    }


    public Product() {}


    //Get All Associated Parts
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


    //Add Associated Part
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    //Delete Associated Part
    public boolean deleteAssociatedPart(int partID) {
        for (Part p : associatedParts) {
            if(p.getId() == partID) {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }


    //Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getStock() {
        return inv;
    }
    public Double getPrice() {
        return price;
    }
    public int getMax() {
        return max;
    }
    public int getMin() {return min; }


    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStock(int inv) {
        this.inv = inv;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public void setMin(int min) {
        this.min = min;
    }
}