package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Analysis extends AppCompatActivity {
PieChart piechart;
Spinner spinner;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        piechart=(PieChart)findViewById(R.id.piechart);
        spinner=findViewById(R.id.dropdown);
        button=findViewById(R.id.show_grapgh);
        String[] items = new String[]{"Sunday", "Monday","Tuesday", "Wednesday","Thursday","Friday","Saturday",};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);



        //Toast.makeText(Analysis.this, "val1="+val1+" :val2="+val2+" :val3="+val3, Toast.LENGTH_SHORT).show();



    }
    public  void  show(View v)
    {
        String day=spinner.getSelectedItem().toString();
        Toast.makeText(this,day,Toast.LENGTH_SHORT).show();
        String str1="1am-12pm";
        String str2="1pm-8pm";
        String str3="9pm-12am";
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("timeframe").child(day);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String val1 = snapshot.child("time_frame_1").getValue().toString();
                    String val2 = snapshot.child("time_frame_2").getValue().toString();
                    String val3 = snapshot.child("time_frame_3").getValue().toString();
                    int v1 = Integer.parseInt(val1);
                    int v2 = Integer.parseInt(val2);
                    int v3 = Integer.parseInt(val3);
                    int total = v1 + v2 + v3;

                    float avg1 = (v1 * 100) / total;
                    float avg2 = (v2 * 100) / total;
                    float avg3 = (v3 * 100) / total;

                    ArrayList<PieEntry> records = new ArrayList<>();
                    records.add(new PieEntry(avg1, str1));
                    records.add(new PieEntry(avg2, str2));
                    records.add(new PieEntry(avg3, str3));


                    PieDataSet dataSet = new PieDataSet(records, "Parking Data Report");

                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(22f);

                    PieData pieData = new PieData(dataSet);

                    piechart.setData(pieData);
                    piechart.getDescription().setEnabled(true);
                    piechart.setCenterText("Parking Data Of "+day);
                    piechart.animate();
                    piechart.invalidate();
                }
                else
                {
                    Toast.makeText(Analysis.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void onBackPressed()
    {
        Intent i=new Intent(Analysis.this,MainActivity.class);
        startActivity(i);
    }
}