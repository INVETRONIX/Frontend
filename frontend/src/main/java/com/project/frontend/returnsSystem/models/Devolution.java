package com.project.frontend.returnsSystem.models;

import com.project.frontend.purchasesSystem.models.Purchase;



public class Devolution {
    private Purchase purchase;

    public Devolution(Purchase purchase){
        this.purchase = purchase;
    }


    public Purchase getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }


}
