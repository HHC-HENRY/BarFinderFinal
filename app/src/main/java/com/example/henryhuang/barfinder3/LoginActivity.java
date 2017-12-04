package com.example.henryhuang.barfinder3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by Henry Huang on 2017/12/4.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);//無上面的menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        firebaseAuth = FirebaseAuth.getInstance();


        buttonLogin      = (Button) findViewById(R.id.buttonLogin);
        buttonRegister   = (Button) findViewById(R.id.buttonRegister);
        editTextEmail    = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

    }

    private void userLogin(){
        String email    = editTextEmail.getText().toString().trim();
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
        progressDialog.setMessage("Logging...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            //start MainpageActivity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin){
            userLogin();
        }
        if (view == buttonRegister){
            startActivity(new Intent(this, RegisterActivity.class));
        }

    }
}
