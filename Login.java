package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email,p1;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        p1=findViewById(R.id.P1);
        auth=FirebaseAuth.getInstance();


    }
    public void  btn_login(View view)
    {
        loginUser();

    }

    private void loginUser() {
        String Email = email.getText().toString();
        String Password = p1.getText().toString();
        if (email.getText().toString().isEmpty()){
            email.setError("Plz Enter Email");
        }
        else if (p1.getText().toString().isEmpty()){
            p1.setError("Please enter password");
        }
        else{
        auth.signInWithEmailAndPassword(Email,Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(),"login Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"enter valid email or password",Toast.LENGTH_SHORT).show();
            }
        });

        }
    }


    public void Sign_up(View view) {
        startActivity(new Intent(getApplicationContext(),Sign_up.class));

    }
}