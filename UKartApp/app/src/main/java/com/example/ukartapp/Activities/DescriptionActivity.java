/**
 * Created by Miguel Balderrama 11/02/2019
 * DescriptionActivity.java
 */

package com.example.ukartapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ukartapp.R;

public class DescriptionActivity extends AppCompatActivity {

    private ImageView imgDesc;
    private TextView txtDescName, txtDescPrice, txtDescInfo;
    private Button btnDescAdd, btnDescCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        imgDesc = findViewById(R.id.imgDesc);
        txtDescName = findViewById(R.id.txtDescName);
        txtDescPrice = findViewById(R.id.txtDescPrice);
        txtDescInfo = findViewById(R.id.txtDescInfo);
        btnDescAdd = findViewById(R.id.btnDescAdd);
        btnDescCancel = findViewById(R.id.btnDescCancel);

        btnDescAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDescCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
