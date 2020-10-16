package com.example.novigrad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    //instance variables needed, they are presents on the design

    EditText mFirstName,mLastName,mEmail,mPassword,mPhoneNumber;
    Button register;
    TextView mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    //initialising the drop down list where the user chose betweem user and employee
    String[] types = {"User","Employee"};
    Employee emp;
    Customer cust;

    //datastructure to store emplopyees and customers locally
    HashMap<String,Employee> employees = new HashMap<>();
    HashMap<String,Customer> customers = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //retrieving the elements of the design, connecting buttons, texview,etc
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password2);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        mLoginBtn = findViewById(R.id.mLogInBtn);

        //we need to initalize the dropdown list where the user can decide if they are creating an employee accoutn or a user account
        final Spinner dropdown = (Spinner) findViewById(R.id.dropDown1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);






        //Firebase variable , the first one is used for authentication, the second one to store the data of the user on the Clours Firestore

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //sending the user to the new activity when they don;t have an account
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });


        //if the user clicks on register we will create a new user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting all the Strings entered by the user in the form

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String fname = mFirstName.getText().toString();
                final String lname = mLastName.getText().toString();
                final String phone = mPhoneNumber.getText().toString();
                final String type = dropdown.getSelectedItem().toString();



                //checking if the entries are valid for emails and password

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;

                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }

                //the length of tha password needs to be at least 6
                if(password.length()<6){
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }

                //a simple progress bar that is turned on between the time the user clicks on register and the moment he is sent to the welcome page
                progressBar.setVisibility(View.VISIBLE);




                //firebase function taht will create a new user using its name and password (the one entered in the form)

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User created",Toast.LENGTH_SHORT);

                            //we add the data of the new user to the database
                            userID = fAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            System.out.println("here 4");
                            Map<String,Object> user = new HashMap<>();

                            //here we add the new user or employee to the firebase database
                            System.out.println("Creating new user");
                            user.put("firstName",fname);
                            user.put("lastname",lname);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("password",password);
                            user.put("type",type);

                            //we also need to add the employee or customer in their respective hashmap, it will be useful in the future

                            if (type=="Employee"){
                                employees.put(phone,new Employee(fname,lname,phone,email,password));
                            }
                            else {
                                customers.put(phone,new Customer(fname,lname,phone,email,password));
                            }

                            //the new entity has been added both locally and on the online database



                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override

                                //we are printing the succes in the consol
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess : user Profile is created for "+ userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                //we are printing the failure if applicable in the console
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure : "+e.toString());
                                }
                            });

                            //sending the user the the welcome page
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{

                            //if the task is not succesfull we print on the console that an error was found
                            System.out.println("error whe  creating user");
                            Toast.makeText(Register.this,"Error !" + task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT);

                            //we hide the progress bar
                            progressBar.setVisibility(View.GONE);


                        }




                    }
                });


            }
        });

//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Login.class));
//            }
//        });






    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+types[position] ,Toast.LENGTH_SHORT).show();
    }
}