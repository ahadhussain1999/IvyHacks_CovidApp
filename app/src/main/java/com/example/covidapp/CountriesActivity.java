package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    Spinner spinner;
    String choose_country, twitter_account, twitter_username;
    int country_id = 0;
    TextView Country_Infected_Text, Country_Deceased_Text, Country_Recovered_Text, Country_Text, Country_Risk_Text, Leader_Response_Text;
    ArrayList<String> countryList = new ArrayList<>();
    ArrayList<Integer> infectedList = new ArrayList<>();
    ArrayList<String> deadList = new ArrayList<>();
    ArrayList<String> recoveredList = new ArrayList<>();
    private COVID19DataAPI covid19DataAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        Country_Infected_Text = findViewById(R.id.Country_Infected_Value);
        Country_Deceased_Text = findViewById(R.id.Country_Deceased_Value);
        Country_Recovered_Text = findViewById(R.id.Country_Recovered_Value);
        Country_Text = findViewById(R.id.Country_Text);
        Country_Risk_Text = findViewById(R.id.Country_Risk_Text);
        Leader_Response_Text = findViewById(R.id.Leader_Response_Text);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        spinner = findViewById(R.id.country_picker_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        covid19DataAPI = retrofit.create(COVID19DataAPI.class);


        Call<List<WorldDataSummary>> call = covid19DataAPI.getWorldDataSummaries();
        call.enqueue(new Callback<List<WorldDataSummary>>() {
            @Override
            public void onResponse(Call<List<WorldDataSummary>> call, Response<List<WorldDataSummary>> response) {
                if (!response.isSuccessful()){
                    Country_Text.setText("Code: " + response.code());
                    return;
                }

                List<WorldDataSummary> worldDataSummaries = response.body();
                for (WorldDataSummary worldDataSummary : worldDataSummaries){
                    String Countrycontent = worldDataSummary.getCountry().toString();
                    Integer Infectedcontent = worldDataSummary.getInfected();
                    String Deadcontent = worldDataSummary.getDeceased().toString();
                    String Recoveredcontent = worldDataSummary.getRecovered().toString();

                    countryList.add(Countrycontent);
                    infectedList.add(Infectedcontent);
                    deadList.add(Deadcontent);
                    recoveredList.add(Recoveredcontent);

                }


            }

            @Override
            public void onFailure(Call<List<WorldDataSummary>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choose_country = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(this, choose_country, Toast.LENGTH_SHORT).show();
        pickCountry();
        try {
            Country_Infected_Text.setText(infectedList.get(country_id).toString());
            Country_Deceased_Text.setText(deadList.get(country_id));
            Country_Recovered_Text.setText(recoveredList.get(country_id));

            if(choose_country != "Select Country"){
                Country_Risk_Text.setVisibility(View.VISIBLE);
                Leader_Response_Text.setVisibility(View.VISIBLE);

            }else{
                Country_Risk_Text.setVisibility(View.INVISIBLE);
                Leader_Response_Text.setVisibility(View.INVISIBLE);
            }

            if(infectedList.get(country_id) > 300000){
                Country_Risk_Text.setText("This Country Is At High Risk For Spreading COVID-19");
            } else if(infectedList.get(country_id) > 100000 & infectedList.get(country_id) < 300000){
                Country_Risk_Text.setText("This Country Is At Medium Risk For Spreading COVID-19");
            }else if(infectedList.get(country_id) > 1000 & infectedList.get(country_id) < 100000) {
                Country_Risk_Text.setText("This Country Is At Low Risk For Spreading COVID-19");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(twitter_account == "Null"){
                Leader_Response_Text.setText("Recent Posts from the Leader of this Country");
                //hide recyclerview
            }else if(twitter_account == "@realDonaldTrump"){
                Leader_Response_Text.setText("Recent Posts from the Leader of this Country");
            }else if(twitter_account == "@Queen_Europe"){
                Leader_Response_Text.setText("Recent Posts from the Leader of this Country");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void pickCountry() {
        switch(choose_country){

            case "Select Country":
//                country_id = 0;
                Country_Text.setText("");
                twitter_account = "Null";
                break;

            case "Algeria":
                country_id = 48;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Austria":
                country_id = 47;
                Country_Text.setText(choose_country);
                twitter_account = "@MFA_Austria";
                break;

            case "Azerbaijan":
                country_id = 46;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Bahrain":
                country_id = 45;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Belgium":
                country_id = 44;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Brazil":
                country_id = 43;
                Country_Text.setText(choose_country);
                twitter_account = "@BrazilGovNews";
                break;

            case "Bulgaria":
                country_id = 42;
                Country_Text.setText(choose_country);
                twitter_account = "@Bulgaria";
                break;

            case "Canada":
                Country_Text.setText(choose_country);
                twitter_account = "@JustinTrudeau";
                country_id = 41;
                break;


            case "China":
                Country_Text.setText(choose_country);
                twitter_account = "@china";
                country_id = 40;
                break;


            case "Croatia":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 39;
                break;


            case "Czech Republic":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 38;
                break;


            case "Denmark":
                Country_Text.setText(choose_country);
                twitter_account = "@DanishMFA";
                country_id = 37;
                break;


            case "Estonia":
                country_id = 36;
                Country_Text.setText(choose_country);
                twitter_account = "@MFAestonia";
                break;


            case "Finland":
                country_id = 35;
                Country_Text.setText(choose_country);
                twitter_account = "@FinlandinEU";
                break;


            case "France":
                country_id = 34;
                Country_Text.setText(choose_country);
                twitter_account = "@EmmanuelMacron";
                break;


            case "Germany":
                country_id = 33;
                Country_Text.setText(choose_country);
                twitter_account = "@Queen_Europe";
                break;


            case "Honduras":
                country_id = 32;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Hungary":
                country_id = 31;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "India":
                country_id = 30;
                Country_Text.setText(choose_country);
                twitter_account = "@PMOIndia";
                break;

            case "Iran":
                country_id = 29;
                Country_Text.setText(choose_country);
                twitter_account = "@IRIMFA_EN";
                break;

            case "Italy":
                country_id = 28;
                Country_Text.setText(choose_country);
                twitter_account = "@ItalyMFA";
                break;

            case "Japan":
                country_id = 27;
                Country_Text.setText(choose_country);
                twitter_account = "@japan";
                break;

            case "Kosovo":
                country_id = 26;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Lithuania":
                country_id = 25;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Luxembourg":
                country_id = 24;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Malaysia":
                country_id = 23;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Netherlands":
                country_id = 22;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Nigeria":
                country_id = 21;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Norway":
                country_id = 20;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Pakistan":
                country_id = 19;
                Country_Text.setText(choose_country);
                twitter_account = "@ImranKhanPTI";
                break;

            case "Palestine":
                country_id = 18;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Philippines":
                country_id = 17;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Poland":
                country_id = 16;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Portugal":
                country_id = 15;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Romania":
                country_id = 14;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Russia":
                country_id = 13;
                Country_Text.setText(choose_country);
                twitter_account = "@Russia";
                break;

            case "Saudi Arabia":
                country_id = 12;
                Country_Text.setText(choose_country);
                twitter_account = "@KSAmofaEN";
                break;

            case "Serbia":
                country_id = 11;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Singapore":
                country_id = 10;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Slovakia":
                country_id = 9;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Slovenia":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 8;
                break;

            case "South Korea":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 7;
                break;

            case "Spain":
                country_id = 6;
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                break;

            case "Sweden":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 5;
                break;

            case "Switzerland":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 4;
                break;

            case "Turkey":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 3;
                break;

            case "United Kingdom":
                Country_Text.setText(choose_country);
                twitter_account = "@RoyalFamily";
                country_id = 2;
                break;

            case "United States":
                Country_Text.setText(choose_country);
                twitter_account = "@realDonaldTrump\n";
                country_id = 1;
                break;

            case "Vietnam":
                Country_Text.setText(choose_country);
                twitter_account = "Null";
                country_id = 0;
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    openHome();
                    break;
                case R.id.nav_myarea:
                    openMyArea();
                    break;
                case R.id.nav_staysafe:
                    openStaySafe();
                    break;
                case R.id.nav_countries:
//                    openCountries();
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

