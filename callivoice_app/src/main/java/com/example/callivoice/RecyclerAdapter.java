package com.example.callivoice;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private ArrayList<String> images = new ArrayList<>();
    public static String imgUrl;

    public RecyclerAdapter(ArrayList<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_gallery_image_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
       final String image = images.get(position);
       Picasso.get().load(image).into(holder.emotionImage);

       holder.emotionImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Picasso.get().load(image).into(EditResultActivity.mPhotoEditorView.getSource());
               ((Activity)v.getContext()).finish();
           }
       });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView emotionImage;
        public ImageViewHolder(View itemView) {
            super(itemView);
            emotionImage = itemView.findViewById(R.id.emotionImage);
        }
    }
}
