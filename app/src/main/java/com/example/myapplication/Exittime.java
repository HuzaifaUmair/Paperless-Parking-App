package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Exittime extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    TextView name,contact;

    TimePicker txtextime;
    public String bookingkey="";

   public SimpleDateFormat sdf;
   public  Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exittime);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        txtextime=findViewById(R.id.txtexxtime);
        Bundle b=getIntent().getExtras();
        bookingkey=b.getString("key");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.setItemIconTintList(null);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.draweropen,R.string.drawerclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        View header=navigationView.getHeaderView(0);
        name=(TextView) header.findViewById(R.id.user_name);
        contact=(TextView) header.findViewById(R.id.user_contact);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users");
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue().toString().toUpperCase());
                contact.setText("Contact: "+snapshot.child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                if(item.getItemId()==R.id.profile)
                {
                    Toast.makeText(Exittime.this, "Profile", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Exittime.this,Profile.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.account)
                {
                    Toast.makeText(Exittime.this, "Report", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(Exittime.this,Analysis.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.booking)
                {

                    Toast.makeText(Exittime.this, "Booking", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Exittime.this,Booking.class);
                    startActivity(i);

                }

                if(item.getItemId()==R.id.exit)
                {
                    Toast.makeText(Exittime.this, "Exit", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Exittime.this,Login.class);
                    startActivity(i);
                    finish();
                }
                return true;
            }
        });

    }
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent i=new Intent(Exittime.this,Booking.class);
            startActivity(i);

        }
    }
    @SuppressLint("SimpleDateFormat")
    public void increase(View view) throws ParseException {

        String error="";

        String am_pm1="AM";
        int h1=txtextime.getCurrentHour();
        int m1=txtextime.getCurrentMinute();
        int original_h1=h1;
        int original_m1=m1;

        if (h1 > 12) {
            h1 -= 12;
            am_pm1 = "PM";
        } else if (h1 == 0) {
            h1 += 12;
            am_pm1 = "AM";
        } else if (h1 == 12)
            am_pm1 = "PM";
        else
            am_pm1 = "AM";
      calendar=Calendar.getInstance();
      sdf=new SimpleDateFormat("HH:mm");
      String current=sdf.format(calendar.getTime());
        Date current_time=sdf.parse(current);
        Date intime= sdf.parse(original_h1+":"+original_m1);

        if(intime.compareTo(current_time)<0)
        {
            error="yes";
            Toast.makeText(this,"Exit Time Should be greater then Current Time",Toast.LENGTH_SHORT).show();
        }
        if(error=="")
        {
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("booking").child(bookingkey);
            ref.child("exit_time").setValue(h1+":"+m1+":"+am_pm1);
            Toast.makeText(this,"Exit Time Increased Successfully",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Exittime.this,MyBooking.class);
            startActivity(i);
            finish();
        }
    }
}