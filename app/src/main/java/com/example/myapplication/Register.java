package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
EditText txtname,txtemail,txtphone,txtpassword,txtcpassword;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtname=findViewById(R.id.txtrname);
        txtemail=findViewById(R.id.txtremail);
        txtphone=findViewById(R.id.txtrphone);
        txtpassword=findViewById(R.id.txtrpassword);
        txtcpassword=findViewById(R.id.txtrcpassword);


    }
    public void register(View v)
    {
        dialog=new ProgressDialog(Register.this);
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        dialog.show();
        String name=txtname.getText().toString().trim();
        String email=txtemail.getText().toString().trim();
        String phone=txtphone.getText().toString().trim();
        String password=txtpassword.getText().toString().trim();
        String cpassword=txtcpassword.getText().toString().trim();
        String valid="true";
        if(name.length()==0)
        {
            txtname.setError("Name is required");
            valid="false";
        }
        if(email.length()==0)
        {
            txtemail.setError("Email is required");
            valid="false";
        }
        if(phone.length()==0)
        {
            txtphone.setError("Phone Number is required");
            valid="false";
        }
        if(password.length()==0)
        {
            txtpassword.setError("Password is required");
            valid="false";
        }
        else if(password.length()<=5)
        {
            txtpassword.setError("At least 6 chars required");
            valid="false";
        }
        if(cpassword.length()==0)
        {
            txtcpassword.setError("Password is required");
            valid="false";
        }
        else if(cpassword.length()<=5)
        {
            txtcpassword.setError("At least 6 chars required");
            valid="false";
        }
        if(password.equals(cpassword))
        {
        }
        else
        {
            txtcpassword.setError("Password not match");
            valid="false";
        }
        if(valid=="true")
        {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful())
                               {
                                   HashMap<String , String> map=new HashMap<>();
                                   map.put("name",name);
                                   map.put("email",email);
                                   map.put("phone",phone);
                                   map.put("password",password);
                                   String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                   DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("users").child(id);
                                   db.setValue(map);

                                   Toast.makeText(Register.this,"Successfully Registered: check your mail please verify your rmail.",Toast.LENGTH_SHORT).show();
                                   Intent i=new Intent(Register.this,Login.class);
                                   startActivity(i);
                                   finish();
                               }
                               else
                               {
                                   dialog.dismiss();
                                   Toast.makeText(Register.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                               }
                            }
                                                                  });

                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(Register.this, "Failed"+"\n"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        dialog.dismiss();

    }
    public void movetologin(View v)
    {
        Intent i=new Intent(Register.this,Login.class);
        startActivity(i);
        finish();
    }
    public void onBackPressed()
    {
            Intent i=new Intent(Register.this,Login.class);
            startActivity(i);
            finish();

    }
}