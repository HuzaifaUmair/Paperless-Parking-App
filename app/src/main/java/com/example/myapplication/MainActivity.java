package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
   private ActionBarDrawerToggle toggle;
   TextView name,contact;
   ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recfragment()).commit();

    drawerLayout=findViewById(R.id.drawer);
    navigationView=findViewById(R.id.nav_view);
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
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,Profile.class);
                startActivity(i);

            }
            if(item.getItemId()==R.id.account)
            {
                Toast.makeText(MainActivity.this, "Account", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,Analysis.class);
                startActivity(i);

            }
            if(item.getItemId()==R.id.booking)
            {

                Toast.makeText(MainActivity.this, "Booking", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,MyBooking.class);
                startActivity(i);

            }

            if(item.getItemId()==R.id.exit)
            {
                Toast.makeText(MainActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();
            }
            return true;
        }
    });
    /*
        viewFlipper=findViewById(R.id.flipper);
        int imagearr[]={R.drawable.slide1,R.drawable.slide4,R.drawable.slide2,R.drawable.slide4};

        for(int i=0;i<imagearr.length;i++)
        {
            showimg(imagearr[i]);
        }*/

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
    /*
    public void showimg(int img)
    {

        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(img);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }*/

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

    public void movetobooking(View v)
    {

    }

}