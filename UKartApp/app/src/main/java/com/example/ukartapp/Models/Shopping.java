package com.example.ukartapp.Models;

import com.example.ukartapp.R;

import java.util.ArrayList;
import java.util.List;

public class Shopping {

    private String id;
    private String customer;
    private String date;
    private String quantityProducts;
    private String price;
    private List<String> productsList = new ArrayList<> ();

    public Shopping(){}

    public Shopping(String customer, String date, String price, List<String> productsList){
        this.customer = customer;
        this.date = date;
        this.quantityProducts = productsList.size()+"";
        this.price = price;
        this.productsList = productsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantityProducts() {
        return quantityProducts;
    }

    public void setQuantityProducts(String quantityProducts) {
        this.quantityProducts = quantityProducts;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<String> productsList) {
        this.productsList = productsList;
    }
}
