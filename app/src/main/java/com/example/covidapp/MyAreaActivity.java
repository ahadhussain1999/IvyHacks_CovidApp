package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class MyAreaActivity extends AppCompatActivity {
Button logout,mapbutton;
FirebaseAuth firebaseAuth;
FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myarea);
        logout=findViewById(R.id.logoutbutton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyAreaActivity.this, HomeActivity.class));
                finish();
                return;
            }
        });
        mapbutton=findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAreaActivity.this, CountriesActivity.class));
                finish();
                return;

            }
        });
    }
}
