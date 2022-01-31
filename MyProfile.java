package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        tv1=findViewById(R.id.tv1);

        read_data();

    }

    private void read_data() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Registration").child("-MujeHaaP6FkM6QxxK8w")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String post= "Name:"+snapshot.child("name").getValue().toString()+"\n"
                                +"Email:"+snapshot.child("email").getValue().toString();
                        tv1.setText(post);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
}}