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

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity  implements  ListView.OnItemClickListener{

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

    private static final String PAYPAL_CLIENT_ID = "AaJJUNiBJezLdgVuY3egxJAvJU1JvlrMnO7iV8B5J_1E3af4mdC9JejXCprbabQPaLav4-A5WoHUmogC";
    private static final int PAYPAL_REQUEST_CODE = 7777;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);

//    String amount = "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        btnFinish = findViewById(R.id.btnFinishShopping);
        btnScan = findViewById(R.id.btnScanShopping);
        listView = findViewById(R.id.listViewShopping);

        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        btnScan.setOnClickListener(view -> scanClick());
        btnFinish.setOnClickListener(view -> finishClick());
        listView.setOnItemClickListener(this);
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
                .setPositiveButton("OK", (dialog, which) -> processPayment())
                .setNegativeButton("CANCEL", (dialog, which) -> {
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

        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null){
                    try {
                        Date c = Calendar.getInstance().getTime();

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c);
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        String idPayment = confirmation.toJSONObject().getJSONObject("response").getString("id");
                        Log.wtf("WTF",idPayment);
                        databaseReference2.push().setValue(new Shopping(MainActivity.idAuthUser,formattedDate,totalPrice+"",idList));


                        startActivity(new Intent(this,PaymentDetailsActivity.class)
                                .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("Amount",""+totalPrice)
                                .putExtra("Date", formattedDate));

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Purchase Canceled", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        } else if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show();
            } else {
                getProduct(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(totalPrice)),"MXN",
                "Purchase: ",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new AlertDialog.Builder(ShoppingActivity.this)
                .setTitle("Remover producto")
                .setMessage("Desea remover  " + productList.get(i).getName() + "?")
                .setPositiveButton("OK", (dialog, which) -> deleteProduct(i))
                .setNegativeButton("CANCEL", (dialog, which) -> {
                }).create().show();
    }

    private void deleteProduct(int i) {

        totalPrice -= Integer.parseInt(productList.get(i).getPrice());

        productList.remove(i);
        idList.remove(i);
        shoppingListAdapter.notifyDataSetChanged();
    }
}
