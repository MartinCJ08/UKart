package com.example.ukartapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ukartapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {
    TextView txtId,txtAmount,txtStatus, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);
        txtDate = findViewById(R.id.txtDate);

        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),
                    intent.getStringExtra("Amount"),
                    intent.getStringExtra("Date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount, String date) {
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText("Status: "+response.getString("state"));
            txtAmount.setText("Amount $"+paymentAmount);
            txtDate.setText(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
