package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {
private  EditText txtemail;
private  Button btn,btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        txtemail=findViewById(R.id.txtfemail);
        btn=findViewById(R.id.btnforget);
        btnlogin=findViewById(R.id.btnbacklogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Forgetpassword.this,Login.class);
                startActivity(i);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=txtemail.getText().toString().trim();
                if(email.isEmpty())
                {
                    txtemail.setError("Required");
                }
                else
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(Forgetpassword.this,"Check your Email", Toast.LENGTH_SHORT).show();
                             Intent i=new Intent(Forgetpassword.this,Login.class);
                             startActivity(i);
                             finish();
                         }
                         else
                         {
                             Toast.makeText(Forgetpassword.this,"Erroe: "+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                         }
                        }
                    });
                }
            }
        });
    }
    public void onBackPressed()
    {
        Intent i=new Intent(Forgetpassword.this,Login.class);
        startActivity(i);
        finish();
    }

}