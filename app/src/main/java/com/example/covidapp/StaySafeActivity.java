package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaySafeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    ExpandableListView main_expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staysafe);
        main_expandableListView = findViewById(R.id.main_expandable_list);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this, listGroup, listItem);
        main_expandableListView.setAdapter(adapter);
        initListData();
        firebaseAuth= FirebaseAuth.getInstance();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void initListData() {
        listGroup.add(getString(R.string.CDC1));
        listGroup.add(getString(R.string.CDC2));
        listGroup.add(getString(R.string.CDC3));
        listGroup.add(getString(R.string.CDC4));
        listGroup.add(getString(R.string.CDC5));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.CDC1);
        for (String item : array){
            list1.add(item);
        }
        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.CDC2);
        for (String item : array){
            list2.add(item);
        }
        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.CDC3);
        for (String item : array){
            list3.add(item);
        }
        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.CDC4);
        for (String item : array){
            list4.add(item);
        }
        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.CDC5);
        for (String item : array){
            list5.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        adapter.notifyDataSetChanged();
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
//                    openStaySafe();
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

