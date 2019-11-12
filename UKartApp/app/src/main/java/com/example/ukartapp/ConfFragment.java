package com.example.ukartapp;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfFragment extends Fragment {
    View viewGlobal;
    Button btnAbout,btnChangedPwd,btnPricacy,btnSaveConf;
    EditText txtNameConf,txtLastNameConf,txtEmailConf,txtCellconf;
    String theNewPwd="",nom="",ape="",email="",cell="";

    public ConfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGlobal = inflater.inflate(R.layout.fragment_conf, container, false);
        btnAbout = viewGlobal.findViewById(R.id.btnAbout);
        btnChangedPwd = viewGlobal.findViewById(R.id.btnChangePwd);
        btnPricacy = viewGlobal.findViewById(R.id.btnPrivacy);
        btnSaveConf = viewGlobal.findViewById(R.id.btnSaveConf);
        txtNameConf = viewGlobal.findViewById(R.id.txtNameConf);
        txtLastNameConf = viewGlobal.findViewById(R.id.txtLastNameConf);
        txtEmailConf = viewGlobal.findViewById(R.id.txtEmailConf);
        txtCellconf = viewGlobal.findViewById(R.id.txtCellConf);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAboutDialog();
            }
        });
        btnChangedPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChangedPwdDialog();
            }
        });
        btnPricacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPrivacyDialog();
            }
        });
        btnSaveConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nom = txtNameConf.getText().toString();
                ape = txtLastNameConf.getText().toString();
                email = txtEmailConf.getText().toString();
                cell = txtCellconf.getText().toString();
                changeConfiguration();
            }
        });
        return viewGlobal;
    }

    private void changeConfiguration() {
        // TODO: Agregar la información a la base de datos
        Toast.makeText(getContext(),"Nom"+nom+" newPwd"+theNewPwd,Toast.LENGTH_SHORT).show();
    }

    public void createAboutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_about, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createChangedPwdDialog(){
        final EditText txtOldPwd, txtNewPwd, txtNewConfirm;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Changed password");
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_changed_pwd, null);
        txtOldPwd = customLayout.findViewById(R.id.txtOldPwd);
        txtNewPwd = customLayout.findViewById(R.id.txtNewPwd);
        txtNewConfirm = customLayout.findViewById(R.id.txtNewPwdConfirm);
        builder.setView(customLayout)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkPwd(txtOldPwd.getText().toString(),txtNewPwd.getText().toString(),txtNewConfirm.getText().toString());
                }
            })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createPrivacyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_privacy, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkPwd(String oldPwd, String newPwd, String newPwdConfirm) {
        //Confirmar que la antigua contraseña sea igual a la actual

        if(oldPwd.equals("") || newPwd.equals("")|| newPwdConfirm.equals("")){
            Toast.makeText(getContext(),"Please, fill all the data",Toast.LENGTH_SHORT).show();
        }else{
            if(newPwd.equals(newPwdConfirm)){
                theNewPwd = newPwd;
                Toast.makeText(getContext(),"Password will change correcltly",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"The new password don't match", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
