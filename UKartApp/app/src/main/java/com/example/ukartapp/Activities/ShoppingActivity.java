/**
 * Created by Miguel Balderrama 12/5/2019
 * ShoppingActivity.java
 */

package com.example.ukartapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ukartapp.Adapters.ProductsShoppingAdapter;
import com.example.ukartapp.Models.Products;
import com.example.ukartapp.Models.Shopping;
import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.PRODUCTS_PATH);
    private DatabaseReference databaseReference2 = firebaseDatabase.getReference(Constants.SHOPPING_PATH);

    private Toolbar myToolbar;

    private Button btnScan, btnFinish;
    private ListView listView;

    private ProductsShoppingAdapter shoppingListAdapter;

    private String id, name, description, price, qr, icon;

    private int totalPrice = 0;

    private List<String> idList = new ArrayList<>();
    private List<Products> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        btnFinish = findViewById(R.id.btnFinishShopping);
        btnScan = findViewById(R.id.btnScanShopping);
        listView = findViewById(R.id.listViewShopping);

        btnScan.setOnClickListener(view -> scanClick());
        btnFinish.setOnClickListener(view -> finishClick());
    }

    /**
     * Listener to scan a new product
     */
    private void scanClick(){
        IntentIntegrator intent =  new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("SCAN CODE");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    /**
     * Listener to confirm and finish the shopping
     */
    private void finishClick(){
        new AlertDialog.Builder(ShoppingActivity.this)
                .setTitle("Confirmar compra")
                .setMessage("Objetos adquiridos: " + idList.size() + "\nTotal: $" + totalPrice)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ShoppingActivity.this, "Compra realizada", Toast.LENGTH_SHORT).show();
                        Date c = Calendar.getInstance().getTime();

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c);
                        databaseReference2.push().setValue(new Shopping(MainActivity.idAuthUser,formattedDate,totalPrice+"",idList));
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }

        }).create().show();
    }


    /**
     * Return the scanned product and update the lists
     * @param myChild Product ID
     */
    private void getProduct(String myChild){
        databaseReference.child(myChild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id = dataSnapshot.getKey();
                name = dataSnapshot.child(Constants.PRODUCT_NAME).getValue(String.class);
                description = dataSnapshot.child(Constants.PRODUCT_DESC).getValue(String.class);
                price = dataSnapshot.child(Constants.PRODUCT_PRICE).getValue(String.class);
                qr = dataSnapshot.child(Constants.PRODUCT_QR).getValue(String.class);
                icon = dataSnapshot.child(Constants.PRODUCT_ICON).getValue(String.class);

                totalPrice += Integer.parseInt(price);

                idList.add(id);
                productList.add(new Products(id,name,description,price,qr,icon));

                shoppingListAdapter = new ProductsShoppingAdapter(ShoppingActivity.this, R.layout.list_products_shopping_item, productList);

                listView.setAdapter(shoppingListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show();
            } else {
                getProduct(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
