package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBooking extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    TextView name,contact;
    ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapperbooking,new bookingfragment()).commit();
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.draweropen,R.string.drawerclose);

        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        View header=navigationView.getHeaderView(0);
        name=(TextView) header.findViewById(R.id.user_name);
        contact=(TextView) header.findViewById(R.id.user_contact);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users");
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
                    Toast.makeText(MyBooking.this, "Profile", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MyBooking.this,Profile.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.account)
                {
                    Toast.makeText(MyBooking.this, "Report", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MyBooking.this,Analysis.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.booking)
                {
                Toast.makeText(MyBooking.this,"My Bookings",Toast.LENGTH_SHORT).show();

                }

                if(item.getItemId()==R.id.exit)
                {
                    Toast.makeText(MyBooking.this, "Exit", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MyBooking.this,Login.class);
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
            super.onBackPressed();
        }
    }
}