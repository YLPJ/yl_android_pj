package com.liwen.dor.entity;

public class Display {
    private int ID;
    private String Name;
    private String CurrentSignalName;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCurrentSignalName() {
        return CurrentSignalName;
    }

    public void setCurrentSignalName(String currentSignalName) {
        CurrentSignalName = currentSignalName;
    }
}
