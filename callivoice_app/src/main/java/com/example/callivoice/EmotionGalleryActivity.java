package com.example.callivoice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmotionGalleryActivity extends AppCompatActivity {

    private DatabaseReference mImageDB;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private ArrayList<String> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_emotiongallery);
        Intent intent = getIntent();
        final String emotion = intent.getStringExtra("emotion");

        mImageDB = FirebaseDatabase.getInstance().getReference().child("Images");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mImageDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int imgCount = 0;
                if (emotion.equals("anger")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("anger").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            images.add(value);
                        }
                    }
                }
                adapter = new RecyclerAdapter(images, true);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
