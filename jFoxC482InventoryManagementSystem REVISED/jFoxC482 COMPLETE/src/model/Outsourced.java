package model;

/**
 @author Josh Fox
  * Student ID: #001053580
  * Inventory Management System
  * WGU C482 Software 1
 */


public class Outsourced extends Part {

    private String companyName;


    //Constructor
    public Outsourced(int id, String name, Double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    //Getter
    public String getCompanyName() {
        return companyName;
    }


    //Setter
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
