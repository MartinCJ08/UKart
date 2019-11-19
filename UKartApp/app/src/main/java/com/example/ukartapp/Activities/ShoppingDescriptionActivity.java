/**
 * Created by Miguel Balderrama 11/15/2019
 * ShoppingDescriptionActivity.java
 */

package com.example.ukartapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukartapp.Adapters.ProductsShoppingAdapter;
import com.example.ukartapp.Models.Products;
import com.example.ukartapp.Models.Shopping;
import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDescriptionActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceInfo;
    private DatabaseReference databaseReferenceList;

    private Shopping specificShopping;
    private Products specificProduct;

    private TextView txtDate, txtQuantity, txtPrice;
    private ListView listView;

    private List<Products> productsList;
    private ProductsShoppingAdapter productsShoppingAdapter;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            getShoppingInfo();
        }
    };

    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            handler.post(runnable);
        }
    };

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_description);

        bundle = getIntent().getExtras();

        txtDate = findViewById(R.id.txtShopDescDate);
        txtPrice = findViewById(R.id.txtShopDescPrice);
        txtQuantity = findViewById(R.id.txtShopDescQuantity);
        listView = findViewById(R.id.listShopping);

        firebaseDatabase = FirebaseDatabase.getInstance();

        thread.start();
    }

    /**
     * Connect and get information from the DB
     */
    private void getShoppingInfo(){
        String id = bundle.getString("ID");

        databaseReferenceInfo = firebaseDatabase.getReference(Constants.SHOPPING_PATH).child(id);

        databaseReferenceInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                specificShopping = dataSnapshot.getValue(Shopping.class);

                txtDate.setText("Fecha: " + specificShopping.getDate());
                txtPrice.setText("Precio: $" + specificShopping.getPrice());
                txtQuantity.setText("Cantidad de productos: " + specificShopping.getQuantityProducts());

                displayListData(specificShopping.getProductsList());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Get and display all the information about each product
     * @param list
     */
    private void displayListData(List<String> list){

        databaseReferenceList = firebaseDatabase.getReference(Constants.PRODUCTS_PATH);
        productsList = new ArrayList<>();

        databaseReferenceList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                specificProduct = dataSnapshot.getValue(Products.class);
                specificProduct.setId(dataSnapshot.getKey());
                for(String id: list){
                    if(specificProduct.getId().equals(id)){
                        productsList.add(specificProduct);
                    }
                }
                productsShoppingAdapter = new ProductsShoppingAdapter(ShoppingDescriptionActivity.this, R.layout.list_products_shopping_item,productsList);
                listView.setAdapter(productsShoppingAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
