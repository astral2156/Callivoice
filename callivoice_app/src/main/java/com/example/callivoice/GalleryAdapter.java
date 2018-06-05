package com.example.callivoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    private ArrayList<String> images = new ArrayList<>();
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String key;

    public GalleryAdapter(ArrayList<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_for_gallery_layout,parent,false);
        GalleryViewHolder galleryViewHolder = new GalleryViewHolder(view);
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, final int position) {
        final String image = images.get(position);
        Picasso.get().load(image).into(holder.galleryImage);
        holder.mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Picasso.get().load(image).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                            File myDir = new File(root+"/callivoice");

                            if(!myDir.exists()) {
                                myDir.mkdirs();
                            }

                            String name = new Date().toString() + ".jpg";
                            myDir = new File(myDir, name);
                            FileOutputStream out = new FileOutputStream(myDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            Toast.makeText(v.getContext(), "저장하였습니다", Toast.LENGTH_LONG).show();
                        }
                        catch(Exception e) {

                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        });

        holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserImageEditActivity.class);
                intent.putExtra("resultUrl", image);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ((Activity) v.getContext()).startActivity(intent);
            }
        });

        holder.mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String userID = mAuth.getCurrentUser().getUid();
                mStorage = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                mStorage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(v.getContext(), "삭제되었습니다", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "삭제하지 못했습니다", Toast.LENGTH_LONG).show();
                    }
                });

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("images");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot mSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot childSnapshot : mSnapshot.getChildren()) {
                                String value = childSnapshot.getValue().toString();
                                if (value.equals(image)) {
                                    key = mSnapshot.getKey();
                                    mDatabase.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            System.out.println("Image url was deleted from Firebase");
                                        }
                                    });
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                removeAt(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void removeAt (int position) {
        images.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, images.size());
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView galleryImage;
        Button mDeleteBtn;
        Button mEditBtn;
        Button mShareBtn;
        Button mDownloadBtn;
        public GalleryViewHolder(View itemView) {
            super(itemView);
            galleryImage = itemView.findViewById(R.id.galleryImage);
            mEditBtn = itemView.findViewById(R.id.mEditBtn);
            mShareBtn = itemView.findViewById(R.id.mShareBtn);
            mDownloadBtn = itemView.findViewById(R.id.mDownloadBtn);
            mDeleteBtn = itemView.findViewById(R.id.mDeleteBtn);

        }
    }

}
