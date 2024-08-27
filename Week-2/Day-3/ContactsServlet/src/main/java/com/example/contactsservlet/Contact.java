package com.example.contactsservlet;

public class Contact {
    private int id;
    private String name;
    private String telNumber;

    public Contact(int id, String name, String telNumber) {
        this.id = id;
        this.name = name;
        this.telNumber = telNumber;
    }

    public Contact(String name, String telNumber) {
        this.name = name;
        this.telNumber = telNumber;
    }

    public String getName() {
        return name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public int getId() {
        return id;
    }
}
