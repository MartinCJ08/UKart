package com.example.ukartapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukartapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShoppingDescriptionActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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
    }
}
