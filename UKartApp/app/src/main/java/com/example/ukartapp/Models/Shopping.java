package com.example.ukartapp.Models;

import com.example.ukartapp.R;

import java.util.ArrayList;
import java.util.List;

public class Shopping {

    private int icon;
    private String customer;
    private String date;
    private String quantityProducts;
    private String price;
    private List<Products> productsList = new ArrayList<> ();

    private static final int[] ICONS = {R.mipmap.ic_basket, R.mipmap.ic_eggs, R.mipmap.ic_invoice, R.mipmap.ic_meat, R.mipmap.ic_snack};

    public Shopping(){}

    public Shopping(String customer, String date, String price, List<Products> productsList){
        this.icon = ICONS[(int)Math.random() * 5];
        this.customer = customer;
        this.date = date;
        this.quantityProducts = this.productsList.size()+"";
        this.price = price;
        this.productsList = productsList;
    }

    //TODO: ERASE THIS CONSTRUCTOR
    public Shopping(String customer, String date, String price){
        this.icon = ICONS[0];
        this.customer = customer;
        this.date = date;
        this.quantityProducts = this.productsList.size()+"";
        this.price = price;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
