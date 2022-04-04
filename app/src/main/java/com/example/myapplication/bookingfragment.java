package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class bookingfragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
   RecyclerView recview;
   bookingadapter adapter;
    public bookingfragment() {

    }


    public static bookingfragment newInstance(String param1, String param2)
    {
        bookingfragment fragment = new bookingfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_bookingfragment, container, false);
         recview=(RecyclerView)view.findViewById(R.id.bookview);
         LinearLayoutManager mlayout=new LinearLayoutManager(getActivity());
         mlayout.setReverseLayout(true);
         mlayout.setStackFromEnd(true);
         recview.setLayoutManager(mlayout);

        FirebaseRecyclerOptions<bookingmodel> options =
                new FirebaseRecyclerOptions.Builder<bookingmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("booking"), bookingmodel.class)
                        .build();
        adapter=new bookingadapter(options);
        recview.setAdapter(adapter);

         return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}