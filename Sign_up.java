package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity {

    EditText Fname,Uname,email,phone,p1,p2;
    Button Register;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
      Fname=findViewById(R.id.fname);
        Uname=findViewById(R.id.Uname);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        p1=findViewById(R.id.P1);
        p2=findViewById(R.id.P2);
        tv=findViewById(R.id.tv1);

        Register=findViewById(R.id.btn1);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Fname.getText().toString().isEmpty()){
                    Fname.setError("plz Enter First Name");
                }
                else if (Uname.getText().toString().isEmpty()){
                    Uname.setError("Plz Enter User Name");
                }
                else if (email.getText().toString().isEmpty()){
                    email.setError("Plz Enter Email");
                }
                else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())){
                    email.setError(("Invalid Email"));
                }
                else if (phone.getText().toString().isEmpty()){
                    phone.setError("plz enter phone no");
                }
                else if (p1.getText().toString().isEmpty()){
                    p1.setError("plz create your password");
                }
                else if (p2.getText().toString().isEmpty()){
                    p2.setError("Plz Retype your Password");
                }
                else tv.setText("All record are correct");
        }

    });
}}