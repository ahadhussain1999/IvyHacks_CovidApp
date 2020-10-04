package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView World_textView, Total_Infected_Text, New_Infected_Text;

    private COVID19DataAPI covid19DataAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView textView;
        World_textView = findViewById(R.id.World_View_Title);
        Total_Infected_Text = findViewById(R.id.Total_Infected_Value);
        New_Infected_Text = findViewById(R.id.New_Infected_Value);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        covid19DataAPI = retrofit.create(COVID19DataAPI.class);

        getSummary();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void getSummary() {
        Call<List<WorldDataSummary>> call = covid19DataAPI.getWorldDataSummaries();

        call.enqueue(new Callback<List<WorldDataSummary>>() {
            @Override
            public void onResponse(Call<List<WorldDataSummary>> call, Response<List<WorldDataSummary>> response) {


                List<WorldDataSummary> summaries = response.body();

                for(WorldDataSummary summary : summaries){
//                    int TconfirmedContent = summary.getInfected();
//                    int TdeathsContent = summary.getDeceased();
//                    int TrecoveredContent = summary.getRecovered();
//
//                    Toast.makeText(HomeActivity.this, summary.getInfected(), Toast.LENGTH_LONG).show();
//                    Total_Infected_Text.setText(TconfirmedContent);

                }

            }

            @Override
            public void onFailure(Call<List<WorldDataSummary>> call, Throwable t) {

            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
//                    openHome();
                    break;
                case R.id.nav_myarea:
                    openMyArea();
                    break;
                case R.id.nav_staysafe:
                    openStaySafe();
                    break;
                case R.id.nav_countries:
                    openCountries();
                    break;
                case R.id.nav_updates:
                    openUpdates();
                    break;
            }
            return true;
        }

    };


    private void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openMyArea() {
        Intent intent = new Intent(this, MyAreaActivity.class);
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