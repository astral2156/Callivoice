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
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private ArrayList<String> images = new ArrayList<>();
    private boolean showEmotionImages = false;
    private boolean showGalleryImages = false;

    public RecyclerAdapter(ArrayList<String> images, boolean showImages) {
        this.images = images;
        this.showEmotionImages = showImages;
        this.showGalleryImages = showImages;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;
        if(this.showEmotionImages) layout = R.layout.emotion_gallery_image_layout;
        if(this.showGalleryImages) layout = R.layout.single_item_for_gallery_layout;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
       final String image = images.get(position);
       if(this.showEmotionImages) {
           Picasso.get().load(image).into(holder.emotionImage);
           holder.emotionImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Picasso.get().load(image).into(EditResultActivity.mPhotoEditorView.getSource());
                   ((Activity) v.getContext()).finish();
               }
           });
       }
       if(this.showGalleryImages) {
           Picasso.get().load(image).into(holder.galleryImage);
       }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView emotionImage;
        ImageView galleryImage;
        public ImageViewHolder(View itemView) {
            super(itemView);
            emotionImage = itemView.findViewById(R.id.emotionImage);
            galleryImage = itemView.findViewById(R.id.galleryImage);
        }
    }
}
