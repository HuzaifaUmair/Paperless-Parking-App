package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity {

    EditText Uname,email,phone,P1;
    Button Register;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Uname=findViewById(R.id.Uname);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        P1=findViewById(R.id.P1);
        auth= FirebaseAuth.getInstance();



        Register=findViewById(R.id.btn1);

        Register.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {


                String name = Uname.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
                String Password = P1.getText().toString().trim();

                CreateUSer();


                 if (Uname.getText().toString().isEmpty()){
                    Uname.setError("Plz Enter User Name");
                }

                else if (phone.getText().toString().isEmpty()){
                    phone.setError("plz enter phone no");
                }
                else if (P1.getText().toString().isEmpty()){
                    P1.setError("plz create your password");
                }

                 else {



                     FirebaseDatabase node = FirebaseDatabase.getInstance();
                     DatabaseReference ref = node.getReference("Registration");

                     Register_holder obj = new Register_holder(name, Email, Phone, Password);

                     ref.push().setValue(obj);
                     Uname.setText("");
                     email.setText("");
                     phone.setText("");
                     P1.setText("");


                   Toast.makeText(getApplicationContext(), "Data submitted successfully", Toast.LENGTH_SHORT).show();



                 }
        }

            private void CreateUSer() {
                String Email = email.getText().toString();
                String Password = P1.getText().toString();
                if (email.getText().toString().isEmpty()){
                    email.setError("Plz Enter Email");
                }
                else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())){
                    email.setError(("Invalid Email"));
                }
                else if (P1.getText().toString().length()<8){
                    P1.setError("Password must be at least 8 character");
                }
                else{

                auth.createUserWithEmailAndPassword(Email,Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                startActivity(new Intent(getApplicationContext(),Login.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(),"Registrations error",Toast.LENGTH_SHORT).show();
                    }
                });

            }}


        });
}}