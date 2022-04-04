package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class  bookingadapter extends FirebaseRecyclerAdapter<bookingmodel,bookingadapter.myviewholder>

{


    public bookingadapter(@NonNull FirebaseRecyclerOptions<bookingmodel> options) {
        super(options);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull bookingmodel model) {
        String key=getRef(position).getKey();

        String uid=model.getUser_id();
      String slot=model.getSlot_id();
        Calendar calendar= Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String date=dateFormat.format(calendar.getTime());
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String user=auth.getCurrentUser().getUid().toString();

        if(uid.equals(user)) {

                        holder.carnumber.setText("Car Numer: "+model.getCar_number());
                        holder.date.setText("Date: "+ model.getDate());
                        holder.etime.setText(model.getEntrance_time());
                        holder.extime.setText(model.getExit_time());
                        holder.payment.setText("Payment Status: "+model.getPayment_status());
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("parkingslots").child(slot);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(snapshot.exists())
                   {
                       holder.slotname.setText(snapshot.child("number").getValue().toString());
                   }
                   else
                   {
                       holder.slotname.setText("N/A");
                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
                        if(model.getPayment_status().equals("pending"))
                        {
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#F8C63C"));
                        }


                        if(model.getDate().equals(date) && model.getPayment_status().equals("pending"))
                        {
                            holder.button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i=new Intent(v.getContext(),Exittime.class);
                                    i.putExtra("key",key);
                                    v.getContext().startActivity(i);
                                }
                            });

                        }
                        else
                        {
                            holder.button.setVisibility(View.GONE);
                        }


                    }





        else
        {
            holder.cardView.setVisibility(View.GONE);
            CardView.LayoutParams params=new CardView.LayoutParams(
                    CardView.LayoutParams.WRAP_CONTENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            params.width=0;
            params.height=0;
            holder.cardView.setLayoutParams(params);
        }



    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singrowbooking,parent,false);
        return  new myviewholder(view);

    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView carnumber,date,etime,extime,payment,slotname;
        CardView cardView;
        Button button;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            carnumber=itemView.findViewById(R.id.car_number);
            slotname=itemView.findViewById(R.id.slot_name);
            date=itemView.findViewById(R.id.date);
            etime=itemView.findViewById(R.id.etime);
            extime=itemView.findViewById(R.id.extime);
            payment=itemView.findViewById(R.id.payment);
            cardView=itemView.findViewById(R.id.single_card);
            button=itemView.findViewById(R.id.btn_booking);
        }
    }
}
