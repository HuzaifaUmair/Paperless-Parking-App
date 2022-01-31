package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyVehicle extends AppCompatActivity {

    Button add1;
    RecyclerView rv;
    myadapter adp;
    ArrayList<DataModel> list;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        add1=findViewById(R.id.add1);
        rv=findViewById(R.id.rv);
        manager=new LinearLayoutManager(this);
        list=new ArrayList<>();
        rv.setLayoutManager(manager);
        Fetectdata();


        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Vehicledetail.class));
                finish();
            }
        });

    }

    private void Fetectdata() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        ref.child("post")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            String name= snapshot1.child("name").getValue().toString();
                            String type=snapshot1.child("type").getValue().toString();
                            String number=snapshot1.child("number").getValue().toString();
                            String detail=snapshot1.child("detail").getValue().toString();
                            list.add(new DataModel(name,type,number,detail));
                        }
                        adp=new myadapter(MyVehicle.this,list);
                        rv.setAdapter(adp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(MyVehicle.this,error.toString(),Toast.LENGTH_SHORT);
                    }
                });

    }
}