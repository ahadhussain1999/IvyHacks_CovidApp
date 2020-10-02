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

public class StaySafeActivity extends AppCompatActivity {
    EditText emailId,password,Name,Number;
    Button signUpButton;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staysafe);

        firebaseAuth= FirebaseAuth.getInstance();
        Name=findViewById(R.id.editText3);
        Number=findViewById(R.id.editText4);
        emailId=findViewById(R.id.editText1);
        password=findViewById(R.id.editText2);
        signUpButton=findViewById(R.id.signupbutton);
        signIn=findViewById(R.id.signintext);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                String namer=Name.getText().toString();
                String number=Number.getText().toString();

                if(email.isEmpty() ||  pass.isEmpty() || namer.isEmpty()|| number.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter all your Details",Toast.LENGTH_SHORT);
                }
                else if(!(email.isEmpty() && pass.isEmpty() &&number.isEmpty()&&namer.isEmpty()))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(StaySafeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"SignUp Unsuccessful",Toast.LENGTH_SHORT);
                            }
//                            else
//                            {
//                                String user_id=firebaseAuth.getCurrentUser().getUid();
//                                String email = emailId.getText().toString();
//                                String pass = password.getText().toString();
//                                String namer=Name.getText().toString();
//                                String number=Number.getText().toString();
//                                userprofile userprofil=new userprofile(user_id,namer,email,number);
//                                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//                                DatabaseReference myref=firebaseDatabase.getReference("Users");
//                                myref.child(user_id).setValue(userprofil);
//                                startActivity(new Intent(SignUpActivity.this, MyAreaActivity.class));
//

                            }

//                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error!!!",Toast.LENGTH_SHORT);

                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaySafeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
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

