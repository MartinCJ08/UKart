package com.example.ukartapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ukartapp.Activities.ArActivity;
import com.example.ukartapp.Activities.MainActivity;
import com.example.ukartapp.Activities.ShoppingActivity;
import com.example.ukartapp.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {
//    EditText etCodigo;
//    Button btnLeerCodigo;
    private View view;
    private Intent inShopping;

    private Button btnStartShopping;


    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan, container, false);

        btnStartShopping = view.findViewById(R.id.btnStartShopping);

        // DEFINIMOS LOS COMPONENTES
//        etCodigo = vista.findViewById(R.id.etCodigo);
//        btnLeerCodigo = vista.findViewById(R.id.btnLeerCodigo);
//
//        btnLeerCodigo.setOnClickListener(v -> startScan());

        btnStartShopping.setOnClickListener(v -> {
            inShopping = new Intent(getActivity(), ShoppingActivity.class);
            startActivity(inShopping);
        });

        return view;
    }

//    public void startScan() {
//
//        IntentIntegrator intent = IntentIntegrator.forSupportFragment(ScanFragment.this);
//        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        intent.setPrompt("SCAN CODE");
//        intent.setCameraId(0);
//        intent.setBeepEnabled(false);
//        intent.setBarcodeImageEnabled(false);
//        intent.initiateScan();
//
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//
//        if(result != null) {
//            if(result.getContents() == null) {
//                Toast.makeText(getContext(), "Scan canceled", Toast.LENGTH_SHORT).show();
//            } else {
//                etCodigo.setText(result.getContents());
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
