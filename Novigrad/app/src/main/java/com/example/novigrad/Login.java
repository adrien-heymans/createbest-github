package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    
    //elements of the design like buttons, textview,progressbar etc...

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    
    //the firebase instance used to identify the user 
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //retrieving the elements of the design, connecting buttons, texview,etc

        mEmail = findViewById(R.id.email2);
        mPassword =findViewById(R.id.password2);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.register2);
        mCreateBtn = findViewById(R.id.needAccount);

        //when the user doesn't have an account we send them to the register page 
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });



        //after entering email and password, we log them in
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the password or email has not been entered we display an error 
                if (mEmail==null){
                    mEmail.setError("Email is required.");
                    return;

                }
               
                if(mPassword==null){
                    mPassword.setError("Password is required.");
                    return;
                }

                //we retrieve the string of the email and password entered by the user
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;

                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }

                //if the password is too short
                if(password.length()<6){
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }

                //we set the visibility of the progressbar to visible so the user does'nt think that the app froze
                progressBar.setVisibility(View.VISIBLE);



                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the email and password are correct, we send the user to the welcome page
                        if(task.isSuccessful()){

                            System.out.println("here 7");
                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT) ;
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            //if the email and password are not correct, we "reset" the page
                            Toast.makeText(Login.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT);
                            progressBar.setVisibility(View.GONE);
                            mPassword.setError("Incorrect password or username.");

                        }
                    }
                });
            }
        });
    }
}