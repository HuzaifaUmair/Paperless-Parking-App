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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    TextView tname,temail,tphone;
    FirebaseUser user;
    FirebaseAuth auth;
    TextView name,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        tname=findViewById(R.id.pname);
        temail=findViewById(R.id.pemail);
        tphone=findViewById(R.id.pphone);
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
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users");
        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue().toString().toUpperCase());
                contact.setText("Contact: "+snapshot.child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        String id=user.getUid();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email=snapshot.child("email").getValue().toString();
                String name=snapshot.child("name").getValue().toString();

                String phone=snapshot.child("phone").getValue().toString();
                tname.setText(name);
                temail.setText(email);
                tphone.setText(phone);
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
                    Toast.makeText(Profile.this, "Profile", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId()==R.id.account)
                {
                    Toast.makeText(Profile.this, "Report", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Profile.this,Analysis.class);
                    startActivity(i);
                }
                if(item.getItemId()==R.id.booking)
                {

                    Toast.makeText(Profile.this, "Booking", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Profile.this,MyBooking.class);
                    startActivity(i);

                }

                if(item.getItemId()==R.id.exit)
                {
                    Toast.makeText(Profile.this, "Exit", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Profile.this,Login.class);
                    startActivity(i);
                    finish();
                }
                return true;
            }
        });







    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent i=new Intent(Profile.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}