package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class
Booking extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
   EditText txtcnumber;
   DatePicker txtdate;
   TimePicker txtetime,txtextime;
   public String slotkey="";
   private Calendar calendar;
   private SimpleDateFormat dateFormat;
   private String date;
   public String slotnumber="";
   public  DatabaseReference reference;
   public int h2,m2;
   String am_pm2;
    TextView name,contact;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        txtcnumber=findViewById(R.id.txtcnumber);
        txtdate=findViewById(R.id.txtdate);
        txtetime=findViewById(R.id.txtetime);
        txtextime=findViewById(R.id.txtextime);
        Bundle b=getIntent().getExtras();
        slotkey=b.getString("key");
         reference=FirebaseDatabase.getInstance().getReference().child("parkingslots").child(slotkey);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slotnumber=snapshot.child("number").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                    Toast.makeText(Booking.this, "Profile", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Booking.this,Profile.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.account)
                {
                    Toast.makeText(Booking.this, "Report", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Booking.this,Analysis.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.booking)
                {

                    Toast.makeText(Booking.this, "Booking", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Booking.this,Booking.class);
                    startActivity(i);

                }

                if(item.getItemId()==R.id.exit)
                {
                    Toast.makeText(Booking.this, "Exit", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Booking.this,Login.class);
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
            Intent i=new Intent(Booking.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void dobooking(View v) throws ParseException {

        String cnumber=txtcnumber.getText().toString().trim();
        String error="";
        if(cnumber.length()==0)
        {
            txtcnumber.setError("Enter Car Number Plate");
            error="yes";
        }

        int d=txtdate.getDayOfMonth();
        int y=txtdate.getYear();
        int m=txtdate.getMonth()+1;

        String my_date="";
        if(m<10 && d<10 ) {
            my_date = "0"+m + "/" +"0"+d + "/" + y;
        }
        else if(m<10 && d>=10) {
            my_date = "0"+m + "/" + d + "/" + y;
        }
        else
        {
            my_date = m + "/" + d + "/" + y;
        }


        String am_pm1="AM";
        int h1=txtetime.getCurrentHour();
        int m1=txtetime.getCurrentMinute();
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

        am_pm2="AM";
        h2 =txtextime.getCurrentHour();
        m2=txtextime.getCurrentMinute();
        int original_h2=h2;
        int original_m2=m2;

        if (h2 > 12) {
            h2 -= 12;
            am_pm2 = "PM";
        } else if (h2 == 0) {
            h2 += 12;
            am_pm2 = "AM";
        } else if (h2 == 12)
            am_pm2 = "PM";
        else
            am_pm2 = "AM";


        calendar=Calendar.getInstance();
       dateFormat=new  SimpleDateFormat("MM/dd/YYYY");
       date=dateFormat.format(calendar.getTime());

       if(!date.equals(my_date)) {
           error="yes";
           Toast.makeText(this,"chose current date", Toast.LENGTH_SHORT).show();
       }
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

       String current=sdf.format(calendar.getTime());
       Date current_time=sdf.parse(current);
        Date intime= sdf.parse(original_h1+":"+original_m1);

       if(intime.compareTo(current_time)<0)
       {
           error="yes";
           Toast.makeText(this,"Entrance Time Should be greater then Current Time",Toast.LENGTH_SHORT).show();
       }

       //Toast.makeText(this,"current:"+current,Toast.LENGTH_SHORT).show();

       Date outtime= sdf.parse(original_h2+":"+original_m2);

       if(outtime.compareTo(intime)<=0)
       {
           error="yes";
           Toast.makeText(this,"Exit Time Should be greater then entrance time",Toast.LENGTH_SHORT).show();
       }
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");

        String dayOfTheWeek = sdf1.format(calendar.getTime());
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid().toString();
        if(error.length()==0) {

                HashMap<String, String> map = new HashMap<>();
                map.put("car_number", cnumber);
                map.put("date", my_date);
                map.put("entrance_time", h1 + ":" + m1 + ":" + am_pm1);
                map.put("exit_time", h2 + ":" + m2 + ":" + am_pm2);
                map.put("day", dayOfTheWeek);
                map.put("original_en_time", original_h1+":"+original_m1);
                map.put("original_ex_time", original_h2+":"+original_m2);
                map.put("payment_status", "pending");
                map.put("slot_id", slotkey);
                map.put("user_id", uid);
            dateFormat=new  SimpleDateFormat("EEE MMM dd HH:mm zzz yyyy", Locale.ENGLISH);
            date=dateFormat.format(calendar.getTime());
            String stringdate=String.valueOf(date);
            String slotref=stringdate.concat('-'+"slot:"+slotnumber);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("booking").child(slotref).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                        if (currentData.getValue() == null) {
                            currentData.setValue(map);

                            reference.child("status").setValue("booked");

                            return Transaction.success(currentData);
                        } else {


                            return Transaction.abort();
                        }


                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                      if(committed==true)
                      {
                          Toast.makeText(Booking.this, "Slot Booked: "+slotnumber, Toast.LENGTH_SHORT).show();
                          reference.child("exit_time").setValue(h2 + ":" + m2 + ":" + am_pm2);
                          Intent i = new Intent(Booking.this, MainActivity.class);
                          startActivity(i);
                          finish();
                      }
                      else
                      {
                          Toast.makeText(Booking.this, "Sorry Slot just Booked", Toast.LENGTH_SHORT).show();
                          Intent i = new Intent(Booking.this, MainActivity.class);
                          startActivity(i);
                          finish();
                      }
                    }
                });



        }



        //Toast.makeText(this,"Extime:"+h2+":"+m2+":"+":"+am_pm2,Toast.LENGTH_SHORT).show();

    }

}