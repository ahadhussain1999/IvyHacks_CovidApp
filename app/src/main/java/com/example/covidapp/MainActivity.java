package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
EditText emailId,password;
Button signInButton;
TextView signUp;
FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.editText1);
        password=findViewById(R.id.editText2);
        signInButton=findViewById(R.id.loginbutton);
        signUp=findViewById(R.id.registertext);
    authStateListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null)
            {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                finish();
                return;
            }
        }
    };



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                if (email.isEmpty())
                {
                    emailId.setError("Enter Email Address");
                    emailId.requestFocus();
                }
                else if (pass.isEmpty())
                {
                    password.setError("Enter your password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your Login details or signup",Toast.LENGTH_SHORT);
                }
                else if(!(email.isEmpty() && pass.isEmpty()))
                {
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Login Error, SignIn again",Toast.LENGTH_SHORT);
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error!!!",Toast.LENGTH_SHORT);

                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });}

        @Override
        protected void onStart(){
            super.onStart();
            firebaseAuth.addAuthStateListener(authStateListener);
        }
    @Override
    protected void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


}
