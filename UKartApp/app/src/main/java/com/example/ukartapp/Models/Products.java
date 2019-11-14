package com.example.ukartapp.Models;

public class Products {

    private String id;
    private String name;
    private String description;
    private String price;
    private String qr;
    private String icon;

    public Products(){}

    public Products(String id, String name, String description, String price, String qr, String icon){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.qr = qr;
    }

    public Products(String name, String description, String price, String qr, String icon){
        this.name = name;
        this.description = description;
        this.price = price;
        this.qr = qr;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
