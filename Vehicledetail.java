package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Vehicledetail extends AppCompatActivity {
    EditText name,type,number,detail;
    Button addv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicledetail);
        name=findViewById(R.id.name);
        type=findViewById(R.id.type);
        number=findViewById(R.id.number);
        detail=findViewById(R.id.detail);
        addv=findViewById(R.id.addv);

        addv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> map = new HashMap<>();
                map.put("name", name.getText().toString());
                map.put("type", type.getText().toString());
                map.put("number", number.getText().toString());
                map.put("detail", detail.getText().toString());
                if (name.getText().toString().isEmpty()){
                    name.setError("Please enter vehicle name");
                }
                else
                if (type.getText().toString().isEmpty()){
                    type.setError("please enter type");
                }
                else
                if(number.getText().toString().isEmpty()){
                    number.setError("please enter vehicle number");
                }
                else
                if (detail.getText().toString().isEmpty()){
                    detail.setError("enter detail");
                }
                else{

                    FirebaseDatabase node = FirebaseDatabase.getInstance();
                    DatabaseReference ref= node.getReference().child("post");
                            ref.push().setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Detail submitted successfully", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            });

                }


            }});

    }
}