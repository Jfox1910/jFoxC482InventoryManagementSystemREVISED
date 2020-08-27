package model;

/**
 @author Josh Fox
  * Student ID: #001053580
  * Inventory Management System
  * WGU C482 Software 1
 */

    public abstract class Part {

    private int partID;
    private String partName;
    private double partPrice;
    private int partInventory;
    private int minNum;
    private int maxNum;


    public Part(int id, String name, double price, int stock, int min, int max) {
        partID = id;
        partName = name;
        partPrice = price;
        partInventory = stock;
        minNum = min;
        maxNum = max;
    }


    //setters
    public void setId(int id) { partID = id; }
    public void setName(String name) { partName = name; }
    public void setPrice(double price) { partPrice = price; }
    public void setStock(int stock) { partInventory = stock; }
    public void setMin(int min) { minNum = min; }
    public void setMax(int max) { maxNum = max; }


    //getters
    public int getId() { return partID; }
    public String getName() { return partName; }
    public double getPrice() { return partPrice; }
    public int getStock() { return partInventory; }
    public int getMin() { return minNum; }
    public int getMax() { return maxNum; }
}

