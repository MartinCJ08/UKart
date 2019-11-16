package com.example.ukartapp.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ukartapp.Activities.MainActivity;
import com.example.ukartapp.Models.Usuario;
import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfFragment extends Fragment {
    private FirebaseAuth mAuth;

    private View viewGlobal;
    private Button btnAbout,btnChangedPwd,btnPricacy,btnSaveConf;
    private EditText txtNameConf,txtLastNameConf,txtEmailConf,txtCellconf;
    private String theNewPwd="",nom="",ape="",email="",cell="";
    private String oldPwd="",oldNom="",oldApe="",oldEmail="",oldCell="";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(Constants.USERS_PATH);
    private DatabaseReference reference = myRef.child(MainActivity.idAuthUser);

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

        btnAbout.setOnClickListener(view -> createAboutDialog());
        btnChangedPwd.setOnClickListener(view -> createChangedPwdDialog());
        btnPricacy.setOnClickListener(view -> createPrivacyDialog());
        btnSaveConf.setOnClickListener(view -> {
            nom = txtNameConf.getText().toString();
            ape = txtLastNameConf.getText().toString();
            email = txtEmailConf.getText().toString();
            cell = txtCellconf.getText().toString();
            changeConfiguration();
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oldNom = dataSnapshot.child(Constants.USER_NAME).getValue(String.class);
                oldApe = dataSnapshot.child(Constants.USER_LAST_NAME).getValue(String.class);
                oldEmail = dataSnapshot.child(Constants.USER_EMAIL).getValue(String.class);
                oldCell = dataSnapshot.child(Constants.USER_PHONE).getValue(String.class);
                oldPwd = dataSnapshot.child(Constants.USER_PASS).getValue(String.class);

                txtNameConf.setText(oldNom);
                txtLastNameConf.setText(oldApe);
                txtEmailConf.setText(oldEmail);
                txtCellconf.setText(oldCell);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return viewGlobal;
    }

    private void changeConfiguration() {
        // TODO: Agregar la información a la base de datos
        if(this.nom.equals("") || this.ape.equals("") || this.cell.equals("") || this.email.equals("")){
            Toast.makeText(getContext(),"Fill all the field", Toast.LENGTH_SHORT).show();
        } else {
            if(!this.nom.equals(this.oldNom) || !this.ape.equals(this.oldApe) || !this.cell.equals(this.oldCell)
             || !this.email.equals(this.oldEmail) ){
                modifiedUserDB(nom,ape,email,theNewPwd,cell);
            }
        }
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

        if(oldPwd.equals("") || newPwd.equals("")|| newPwdConfirm.equals("")){
            Toast.makeText(getContext(),"Please, fill all the data",Toast.LENGTH_SHORT).show();
        }else{
            if(this.oldPwd.equals(oldPwd)){
                if(newPwd.equals(newPwdConfirm)){
                    theNewPwd = newPwd;
                    Toast.makeText(getContext(),"Password will change correcltly",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"The new password don't match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(),"The old password don't match "+oldPwd+" ol"+this.oldPwd,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void modifiedUserDB(String nom, String ape, String email, String pass, String phone) {
        if( pass.equals("")){
            pass = this.oldPwd;
        }
        Usuario user = new Usuario(nom, ape, email, pass, phone);
        reference.setValue(user);
    }
}
