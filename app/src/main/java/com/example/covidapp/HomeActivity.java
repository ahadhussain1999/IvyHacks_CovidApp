package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    EditText emailId, password;
    Button signInButton;
    TextView signUp;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                    openHome();
                    break;
                case R.id.nav_myarea:
                    Toast.makeText(HomeActivity.this, "My Area", Toast.LENGTH_SHORT).show();
                    openMyArea();
                    break;
                case R.id.nav_staysafe:
                    Toast.makeText(HomeActivity.this, "Stay Safe", Toast.LENGTH_SHORT).show();
                    openStaySafe();
                    break;
                case R.id.nav_countries:
                    Toast.makeText(HomeActivity.this, "Countries", Toast.LENGTH_SHORT).show();
                    openCountries();
                    break;
                case R.id.nav_updates:
                    Toast.makeText(HomeActivity.this, "Updates", Toast.LENGTH_SHORT).show();
                    openUpdates();
                    break;
            }
            return false;
        }

    };


    private void openHome() {
        Intent intent = new Intent(this, MyAreaActivity.class);
        startActivity(intent);
    }

    private void openMyArea() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    private void openCountries() {
        Intent intent = new Intent(this, CountriesActivity.class);
        startActivity(intent);
    }

    private void openStaySafe() {
        Intent intent = new Intent(this, StaySafeActivity.class);
        startActivity(intent);
    }

    private void openUpdates() {
        Intent intent = new Intent(this, UpdatesActivity.class);
        startActivity(intent);
    }

}
