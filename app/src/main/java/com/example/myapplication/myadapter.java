package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("Range")
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        String key=getRef(position).getKey();
        holder.slotname.setText("#"+ model.getNumber());
        holder.slotstatus.setText("Status: "+model.getStatus());
        holder.exit_time.setVisibility(View.GONE);
        Calendar calendar= Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("MM/dd/YYYY");
        String date=dateFormat.format(calendar.getTime());
        if (model.getStatus().equals("free"))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#7884CC"));
        }
        if (model.getStatus().equals("booked"))
        {
            holder.cardView.setCardBackgroundColor(Color.RED);
            holder.exit_time.setVisibility(View.VISIBLE);
            holder.exit_time.setText("Expected: "+model.getExit_time());
        }
        if (model.getStatus().equals("occupied"))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F8C63C"));
            holder.exit_time.setVisibility(View.VISIBLE);
            if(model.getExit_time() !=""){
            holder.exit_time.setText("Expected: "+model.getExit_time());
            }
        }

        holder.btnslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(model.getStatus().equals("booked")) {
                        Toast.makeText(v.getContext(), "Sorry Slot is not free", Toast.LENGTH_SHORT).show();
                    }
                if(model.getStatus().equals("occupied")) {
                    Toast.makeText(v.getContext(), "Sorry Slot is not free", Toast.LENGTH_SHORT).show();
                }
                if(model.getStatus().equals("free")) {
                    Intent i=new Intent(v.getContext(),Booking.class);
                    i.putExtra("key",key);
                    v.getContext().startActivity(i);
                }
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
      return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder

    {
        CardView cardView;
       TextView slotname,slotstatus,exit_time;
       Button btnslot;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            slotname=itemView.findViewById(R.id.slot_name);
            slotstatus=itemView.findViewById(R.id.slot_status);
            exit_time=itemView.findViewById(R.id.exit);

            btnslot=itemView.findViewById(R.id.btn_slot);
            cardView=itemView.findViewById(R.id.single_card);
        }
    }
}
