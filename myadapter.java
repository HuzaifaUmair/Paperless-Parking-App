package com.example.smartparking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.PersonViewHolder>  {

    private Context ctx;
    private ArrayList<DataModel>list;

    public myadapter(Context ctx, ArrayList<DataModel>list){

        this.ctx=ctx;
        this.list=list;

    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.card,parent,false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {

        DataModel model= list.get(position);
        holder.name.setText(model.getName());
        holder.type.setText(model.getType());
        holder.number.setText(model.getNumber());
        holder.detail.setText(model.getDetail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder{

        TextView name,type,number,detail;
       public PersonViewHolder(@NonNull View cardView){

           super(cardView);
           name=cardView.findViewById(R.id.Name);
           type=cardView.findViewById(R.id.Type);
           number=cardView.findViewById(R.id.Number);
           detail=cardView.findViewById(R.id.Detail);
       }

    }

}
