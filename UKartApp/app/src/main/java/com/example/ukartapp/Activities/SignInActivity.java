package com.example.ukartapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukartapp.R;
import com.example.ukartapp.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView input_email,input_password;
    Intent inMain;
    Intent inSignup;
    private String key = "";
    private String sEmail = "";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(Constants.USERS_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_sign_in);


        mAuth = FirebaseAuth.getInstance();
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void onClick(View v){
        String sEmail = input_email.getText().toString();
        String sPass = input_password.getText().toString();

        if(sEmail.isEmpty() || sPass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Fill the field",Toast.LENGTH_SHORT).show();
        }else{
            signIn(sEmail,sPass);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.Theme_AppCompat_Light_Dialog_Alert);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                () -> progressDialog.dismiss(), 3000);
    }


    public void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Query query = reference.orderByChild(Constants.USER_EMAIL).equalTo(email);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                        key = datasnapshot.getKey();
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ID", key);
                                    inMain = new Intent(SignInActivity.this, MainActivity.class);
                                    inMain.putExtras(bundle);
                                    startActivity(inMain);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

//                            updateUI(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void goToSignUp(View v){
        inSignup = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(inSignup);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){}
    }
}
