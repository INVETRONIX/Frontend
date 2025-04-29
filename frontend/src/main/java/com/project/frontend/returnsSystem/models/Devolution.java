package com.project.frontend.returnsSystem.models;

import com.project.frontend.purchasesSystem.models.Purchase;



public class Devolution {
    private String id;
    private String date;
    private Purchase purchase;

    public Devolution(String id,String date, Purchase purchase){
        this.id = id;
        this.date = date;
        this.purchase = purchase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Purchase getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
