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
    public static RecyclerAdapter adapter;
    public static ArrayList<String> images = new ArrayList<>();
    public static String emotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_emotiongallery);
        Intent intent = getIntent();
        emotion = intent.getStringExtra("emotion");
        final boolean isResultActivity = intent.getBooleanExtra("isResultActivity", false);
        if (!isResultActivity) emotion = intent.getStringExtra("mEmotion");
        mImageDB = FirebaseDatabase.getInstance().getReference().child("Images");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        mImageDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int imgCount = 0;
                images.clear();
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
                if(emotion.equals("love")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("love").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            images.add(value);
                        }
                    }
                }
                if(emotion.equals("fear")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("fear").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            images.add(value);
                        }
                    }
                }
                if(emotion.equals("surprise")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("surprise").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            images.add(value);
                        }
                    }
                }
                if(emotion.equals("sadness")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("sadness").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            images.add(value);
                        }
                    }
                }
                if(emotion.equals("joy")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("joy").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            images.add(value);
                        }
                    }
                }
                adapter = new RecyclerAdapter(images, isResultActivity);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
