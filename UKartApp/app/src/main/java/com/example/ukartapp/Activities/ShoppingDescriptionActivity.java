/**
 * Created by Miguel Balderrama 11/15/2019
 * ShoppingDescriptionActivity.java
 */

package com.example.ukartapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ukartapp.Models.Shopping;
import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShoppingDescriptionActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Shopping specyficShopping;

    private TextView txtDate, txtQuantity, txtPrice;
    private ListView listView;

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

        getShoppingInfo();
    }

    /**
     * Connect and get information from the DB
     */
    private void getShoppingInfo(){
        String id = bundle.getString("ID");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.SHOPPING_PATH).child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                specyficShopping = dataSnapshot.getValue(Shopping.class);

                txtDate.setText("Fecha: " + specyficShopping.getDate());
                txtPrice.setText("Precio: $" + specyficShopping.getPrice());
                txtQuantity.setText("Cantidad de productos: " + specyficShopping.getQuantityProducts());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
