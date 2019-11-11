package com.example.ukartapp.Models;

import java.util.List;

public class Shopping {

    private String customer;
    private String date;
    private String quantityProducts;
    private String price;
    private List<Products> productsList;

    public Shopping(){}

    public Shopping(String customer, String date, String price, List<Products> productsList){
        this.customer = customer;
        this.date = date;
        this.quantityProducts = this.productsList.size()+"";
        this.price = price;
        this.productsList = productsList;
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

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
