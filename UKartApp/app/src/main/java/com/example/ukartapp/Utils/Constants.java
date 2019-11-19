package com.example.ukartapp.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

public class Constants {

    //ENTITIES
    public static final String USERS_PATH = "usuarios";
    public static final String SHOPPING_PATH = "shopping";
    public static final String PRODUCTS_PATH = "products";

    //USER FIELDS
    public static final String USER_NAME = "nombre";
    public static final String USER_LAST_NAME = "apellido";
    public static final String USER_EMAIL = "correo";
    public static final String USER_PASS = "pass";
    public static final String USER_PHONE = "telefono";

    //PRODUCT FIELDS
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_DESC = "description";
    public static final String PRODUCT_ICON = "icon";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_QR = "qr";

    //SHOPPING FIELDS
    public static final String SHOPPING_CUSTOMER = "customer";
    public static final String SHOPPING_DATE = "date";
    public static final String SHOPPING_PRICE = "price";
    public static final String SHOPPING_LIST = "productsList";
    public static final String SHOPPING_QUANTITY = "quantityProducts";

    /**
     *
     * @param url Where is the image
     * @return Image with Bitmap format
     */
    public static Bitmap downloadImage(String url) {
        try {
            InputStream isImagen;
            isImagen = (InputStream) new URL(url).getContent();
            Bitmap bBitMap = BitmapFactory.decodeStream(isImagen);
            return bBitMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
