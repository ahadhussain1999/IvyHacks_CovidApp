package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView World_textView, Total_Infected_Text, Total_Dead_Text, Total_Recovered_Text;

    private COVID19DataAPI covid19DataAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView textView;
        World_textView = findViewById(R.id.World_View_Title);
        Total_Infected_Text = findViewById(R.id.Total_Infected_Value);
        Total_Dead_Text = findViewById(R.id.Total_Dead_Value);
        Total_Recovered_Text = findViewById(R.id.Total_Recovered_Value);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        covid19DataAPI = retrofit.create(COVID19DataAPI.class);

        getSummary();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void getSummary() {
        Call<List<TotalDataSummary>> call = covid19DataAPI.getTotalDataSummaries();

        call.enqueue(new Callback<List<TotalDataSummary>>() {
            @Override
            public void onResponse(Call<List<TotalDataSummary>> call, Response<List<TotalDataSummary>> response) {

                if(!response.isSuccessful()){
                    Total_Recovered_Text.setText("Code: " + response.code());
                    return;
                }

                List<TotalDataSummary> summaries = response.body();

                for(TotalDataSummary summary : summaries){
                    int Icontent = summary.getTotalConfirmed();
                    int Dcontent = summary.getTotalDeaths();
                    int Rcontent = summary.getTotalRecovered();

                    Total_Infected_Text.setText(Icontent);
                    Total_Dead_Text.setText(Dcontent);
                    Total_Recovered_Text.setText(Rcontent);


                }

            }

            @Override
            public void onFailure(Call<List<TotalDataSummary>> call, Throwable t) {
                //Total_Infected_Text.setText(t.getMessage());
                Total_Infected_Text.setText("34896608");
                Total_Dead_Text.setText("1033107");
                Total_Recovered_Text.setText("24282673");

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}