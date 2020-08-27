package model;

/**
 @author Josh Fox
  * Student ID: #001053580
  * Inventory Management System
  * WGU C482 Software 1
 */


public class InHouse extends Part {

    private int machineId;


    //Constructor
    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    //Setter
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


    //Getter
    public int getMachineId() {
        return machineId;
    }
}
