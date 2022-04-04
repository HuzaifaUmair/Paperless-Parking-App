package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
EditText txtemail, txtpass;
Button btn;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtemail=findViewById(R.id.txtemail);
        txtpass=findViewById(R.id.txtpassword);
        btn=findViewById(R.id.btnlforget);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,Forgetpassword.class);
                startActivity(i);
            }
        });
    }
    public void movetoregister(View v)
    {
        Intent i=new Intent(Login.this,Register.class);
        startActivity(i);


    }
    public void movetmain(View v)
    {
        dialog=new ProgressDialog(Login.this);
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        dialog.show();

        String email=txtemail.getText().toString();
        String pass=txtpass.getText().toString();
        String valid="true";

        if(email.length()==0)
        {
            txtemail.setError("Email can not be empty");
            valid="false";
        }
        if(pass.length()==0)
        {
            txtpass.setError("Password can not be empty");
            valid="false";
        }

        if(valid=="true")
        {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                            dialog.dismiss();
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(Login.this, "please verify your email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(Login.this, "Failed" + "\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}