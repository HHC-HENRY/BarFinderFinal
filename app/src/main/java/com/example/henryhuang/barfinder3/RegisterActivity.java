package com.example.henryhuang.barfinder3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by Henry Huang on 2017/12/4.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    Button buttonConfirm;
    Button buttonCancel;
    Button buttonClose;

    TextView text_title;
    EditText editTextEmail;
    EditText editTextPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        firebaseAuth = FirebaseAuth.getInstance();
/*
        // check whether user already log in
        if(firebaseAuth.getCurrentUser() != null){
            //directly go into user's mainpage
            finish();
            startActivity(new Intent(getApplicationContext(), MainpageActivity.class));
        }
*/

        progressDialog = new ProgressDialog(this);

        buttonConfirm    = (Button)findViewById(R.id.buttonConfirm);
        buttonCancel     = (Button)findViewById(R.id.buttonCancel);
        buttonClose      = (Button)findViewById(R.id.buttonClose);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail    = (EditText) findViewById(R.id.editTextEmail);
        text_title       = (TextView)findViewById(R.id.text_title);

        buttonConfirm.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonClose.setOnClickListener(this);


    }


    private void registerUser(){

        String email = editTextEmail.getText().toString().trim(); //trim() use to remove white space
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //no email key in
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            //stopping function to execution further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //no password key in
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            //stopping function to execution further
            return;
        }

        //if user passing validation
        //we will show a progress dialog
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user registration successful
                            //Start user profile activity here
                            //display toast
                            Toast.makeText(RegisterActivity.this, "Registration successfully", Toast.LENGTH_SHORT).show();
                            if(firebaseAuth.getCurrentUser() != null){
                                //directly go into user's mainpage
                                finish();
                                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "Register failed, Please try again", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }


    @Override
    public void onClick(View view) {
        if (view == buttonConfirm){
            registerUser();
        }
        if (view == buttonCancel){
            Toast.makeText(RegisterActivity.this,"Register Cancel!",Toast.LENGTH_SHORT).show();
            finish();
        }
        if (view == buttonClose) {
            Toast.makeText(RegisterActivity.this,"Register Close!",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}