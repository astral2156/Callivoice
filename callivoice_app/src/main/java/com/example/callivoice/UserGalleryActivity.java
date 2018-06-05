package com.example.callivoice;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserGalleryActivity extends AppCompatActivity {

    private DatabaseReference mUserImageDB;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GalleryAdapter galleryAdapter;
    private ArrayList<String> images = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String userID;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_gallery_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForGallery);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mUserImageDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("images");


        mUserImageDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images.clear();
                for(DataSnapshot mSnapshot:dataSnapshot.getChildren()) {
                    for (DataSnapshot childSnapshot : mSnapshot.getChildren()) {
                        String childKey = childSnapshot.getKey();
                        if (childKey.equals("result_img")) {
                            String imageUrl = childSnapshot.getValue(String.class);
                            System.out.println(imageUrl);
                            images.add(imageUrl);
                        }
                    }
                }
                    galleryAdapter = new GalleryAdapter(images, UserGalleryActivity.this);
                    recyclerView.setAdapter(galleryAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        images.clear();
    }


}
