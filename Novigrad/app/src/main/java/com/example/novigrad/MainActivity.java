package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    Button logout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView fname,lname,email,phone,userType;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = (Button) findViewById(R.id.logOut);
        fname = (TextView) findViewById(R.id.showFirstName);
        lname = (TextView) findViewById(R.id.showLastName);
        email = (TextView) findViewById(R.id.showEmail);
        phone = (TextView)  findViewById(R.id.showPhone);
        userType = (TextView)  findViewById(R.id.showUser);
        fAuth = FirebaseAuth.getInstance();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fStore.collection("users").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (fAuth==null){
                    return;
                }
                if(fStore==null){
                    return;
                }

                if(documentSnapshot == null){
                    Log.i("Error; ", e.toString());
                }else {
                    phone.setText(   "Phone :      "+documentSnapshot.getString("phone"));
                    fname.setText(   "First Name : "+documentSnapshot.getString("firstName"));
                    lname.setText(   "Last Name :  "+documentSnapshot.getString("lastname"));
                    email.setText(   "Email :      "+documentSnapshot.getString("email"));
                    userType.setText("Type :       "+documentSnapshot.getString("type"));
                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Signing out the current user.");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }






}