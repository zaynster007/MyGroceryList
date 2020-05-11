package com.example.mygrocery.Activity.Model;

import java.util.PriorityQueue;

public class Grocery {
    private String name;
    private String Qty;
    private int Id;
    private String date;

    public Grocery() {
    }

    public Grocery(String name, String qty, int id, String date) {
        this.name = name;
        Qty = qty;
        Id = id;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
