package com.example.callivoice;

/**
 * Created by Lss on 2018-05-01.
 */


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;

public class EditResultActivity extends AppCompatActivity {

    private Button mBackBtn;
    private Button mFontBtn;
    private Button mEmotionGalleryBtn;
    private Button mSaveBtn;
    private Button mShareBtn;

    final Context context = this;
    private PhotoEditorView mPhotoEditorView;
    private PhotoEditor mPhotoEditor;
    private StorageReference mStorage;
    private DatabaseReference mImageDB;
    private ArrayList<String> mAngerImages = new ArrayList();
    private ArrayList<String> mLoveImages = new ArrayList();
    private ArrayList<String> mFearImages = new ArrayList();
    private ArrayList<String> mSurpriseImages = new ArrayList();
    private ArrayList<String> mSadnessImages = new ArrayList();
    private ArrayList<String> mJoyImages = new ArrayList();
    private final int nothing = 0;
    private final int love = 1;
    private final int anger = 2;
    private final int sadness = 3;
    private final int joy = 4;
    private final int fear = 5;
    private final int surprise = 6;
    private int selectedFont = R.font.basefont;
    private String myText;
    private String mFileName;
    private String mFilePath;
    private boolean saved;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_result);

        final Typeface mSetFont = ResourcesCompat.getFont(EditResultActivity.this, selectedFont);
        Intent intent = getIntent();
        final String emotion = intent.getStringExtra("emotion");
        myText = intent.getStringExtra("text");

        mPhotoEditorView = findViewById(R.id.photoEditorView);

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView).setPinchTextScalable(true).build();
        mStorage = FirebaseStorage.getInstance().getReference();
        mImageDB = FirebaseDatabase.getInstance().getReference().child("Images");
        TextView mCalliText = findViewById(R.id.calliTextView);

        mFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "callivoice";
        mFileName = mFilePath + "/image" + System.currentTimeMillis() + ".jpg";
        saved = false;



        mBackBtn = (Button) findViewById(R.id.back);
        mFontBtn = (Button) findViewById(R.id.font);
        mEmotionGalleryBtn = (Button) findViewById(R.id.emotionGalleryBtn);
        mSaveBtn = (Button) findViewById(R.id.save);
        mShareBtn = (Button) findViewById(R.id.share);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);




        String callitext = intent.getStringExtra("CALLITEXT_KEY");
        mCalliText.setText(callitext);

        mImageDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int imgCount = 0;
                int randomImage = 0;
                if (emotion.equals("anger")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("anger").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mAngerImages.add(value);
                        }
                    }
                    randomImage = (int) (Math.random() * imgCount + 1);
                    mPhotoEditor.addText(myText, Color.rgb(255, 255, 255));
                    Picasso.get().load(mAngerImages.get(randomImage - 1)).into(mPhotoEditorView.getSource());
                }
                if (emotion.equals("fear")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("fear").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mFearImages.add(value);
                        }
                    }
                    randomImage = (int) (Math.random() * imgCount + 1);
                    mPhotoEditor.addText(myText, Color.rgb(255, 255, 255));
                    Picasso.get().load(mFearImages.get(randomImage - 1)).into(mPhotoEditorView.getSource());
                }

                if (emotion.equals("love")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("love").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mLoveImages.add(value);
                        }
                    }
                    randomImage = (int) (Math.random() * imgCount + 1);
                    mPhotoEditor.addText(myText, Color.rgb(255, 255, 255));
                    Picasso.get().load(mLoveImages.get(randomImage - 1)).into(mPhotoEditorView.getSource());
                }

                if (emotion.equals("sadness")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("sadness").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mSadnessImages.add(value);
                        }
                    }
                    randomImage = (int) (Math.random() * imgCount + 1);
                    mPhotoEditor.addText(myText, Color.rgb(255, 255, 255));
                    Picasso.get().load(mSadnessImages.get(randomImage - 1)).into(mPhotoEditorView.getSource());
                }

                if (emotion.equals("surprise")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("surprise").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!childSnapshot.getKey().equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            int counter = 0;
                            mSurpriseImages.add(value);
                        }
                    }
                    randomImage = (int) (Math.random() * imgCount + 1);
                    mPhotoEditor.addText(myText, Color.rgb(255, 255, 255));
                    Picasso.get().load(mSurpriseImages.get(randomImage - 1)).into(mPhotoEditorView.getSource());
                }

                if (emotion.equals("joy")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("joy").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mJoyImages.add(value);
                        }
                    }
                    randomImage = (int) (Math.random() * imgCount + 1);
                    mPhotoEditor.addText(myText, Color.rgb(255, 255, 255));
                    Picasso.get().load(mJoyImages.get(randomImage - 1)).into(mPhotoEditorView.getSource());
                }
                System.out.println(imgCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(context);
                exitDialogBuilder.setTitle("종료하기");
                exitDialogBuilder.setMessage("저장하지 않고 편집을 종료하시겠습니까?").setCancelable(false)
                        .setPositiveButton("종료",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(EditResultActivity.this, EmotionRecognitionActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog exitDialog = exitDialogBuilder.create();

                exitDialog.show();

            }
        });

        mFontBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                displayPopupFont();
            }
        });

        mEmotionGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                displayPopupEG(emotion);
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                saveImage();
            }
        });

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(!saved) saveImage();

                Uri uri = Uri.fromFile(new File(mFileName));

                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);
                share_intent.setType("image/jpg");
                share_intent.putExtra(Intent.EXTRA_STREAM, uri);

                Intent chooser = Intent.createChooser(share_intent, "이미지 공유하기");
                startActivity(chooser);
            }
        });
    }

    public void displayPopupFont() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        width*=.6;
        height*=.8;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.activity_popup_font, (ViewGroup) findViewById(R.id.popup_element));
        final PopupWindow pw = new PopupWindow(layout, width, height,true);
        pw.showAtLocation(findViewById(R.id.RelativeLayout1), Gravity.CENTER, 0, 0);

        Button mChooseFontBtn1 = (Button) layout.findViewById(R.id.fontBtn1);
        Button mChooseFontBtn2 = (Button) layout.findViewById(R.id.fontBtn2);
        Button mChooseFontBtn3 = (Button) layout.findViewById(R.id.fontBtn3);
        Button mChooseFontBtn4 = (Button) layout.findViewById(R.id.fontBtn4);
        Button mChooseFontBtn5 = (Button) layout.findViewById(R.id.fontBtn5);
        Button mChooseFontBtn6 = (Button) layout.findViewById(R.id.fontBtn6);
        Button mCloseFontBtn = (Button) layout.findViewById(R.id.closePopupBtn);

        mChooseFontBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.thefaceshop;
                Typeface mTheFaceShopTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mTheFaceShopTf, myText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.nanumbrush;
                Typeface mNanumBrushTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mNanumBrushTf, myText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });

        mChooseFontBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.seoulhangang;
                Typeface mSeoulHangangTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mSeoulHangangTf, myText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.menbal;
                Typeface mMenbalTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mMenbalTf, myText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.leesunsin;
                Typeface mLeeSunSinTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mLeeSunSinTf, myText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.bingrae;
                Typeface mBingraeTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mBingraeTf, myText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });

        mCloseFontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }


    public void displayPopupEG(final String emotion) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        final String e = emotion;

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        width *= .9;
        height *= .9;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.activity_popup_emotiongallery, (ViewGroup) findViewById(R.id.popup_element));
        final PopupWindow pw = new PopupWindow(layout, width, height, true);
        pw.showAtLocation(findViewById(R.id.RelativeLayout1), Gravity.CENTER, 0, 0);


        FirebaseRecyclerAdapter<EmotionImage, ImageViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<EmotionImage, ImageViewHolder>(
                        EmotionImage.class,
                        R.layout.activity_popup_emotiongallery,
                        ImageViewHolder.class, mImageDB) {
                    @Override
                    protected void populateViewHolder(ImageViewHolder viewHolder, EmotionImage model, int position) {
                        viewHolder.setImage(getApplicationContext(), model.getImgUrl());

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);



    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setImage(Context ctx, String image) {
            ImageView mImage = (ImageView)mView.findViewById(R.id.emotionGallery_id);
            Picasso.get().load(image).into(mImage);
        }
    }

        /*
        ImageButton mChooseEmotionBtn1 = (ImageButton) layout.findViewById(R.id.emotionBtn1);
        ImageButton mChooseEmotionBtn2 = (ImageButton) layout.findViewById(R.id.emotionBtn2);
        ImageButton mChooseEmotionBtn3 = (ImageButton) layout.findViewById(R.id.emotionBtn3);
        ImageButton mChooseEmotionBtn4 = (ImageButton) layout.findViewById(R.id.emotionBtn4);
        Button mCloseEmotionBtn = (Button) layout.findViewById(R.id.closePopupBtn);

        if(e.equals("anger")) {
            Picasso.get().load(mAngerImages.get(0)).into(mChooseEmotionBtn1);
            Picasso.get().load(mAngerImages.get(1)).into(mChooseEmotionBtn2);
            Picasso.get().load(mAngerImages.get(2)).into(mChooseEmotionBtn3);
            Picasso.get().load(mAngerImages.get(3)).into(mChooseEmotionBtn4);
        }
        if(e.equals("fear")) {
            Picasso.get().load(mFearImages.get(0)).into(mChooseEmotionBtn1);
            Picasso.get().load(mFearImages.get(1)).into(mChooseEmotionBtn2);
            Picasso.get().load(mFearImages.get(2)).into(mChooseEmotionBtn3);
            Picasso.get().load(mFearImages.get(3)).into(mChooseEmotionBtn4);
        }

        if(e.equals("love")) {
            Picasso.get().load(mLoveImages.get(0)).into(mChooseEmotionBtn1);
            Picasso.get().load(mLoveImages.get(1)).into(mChooseEmotionBtn2);
            Picasso.get().load(mLoveImages.get(2)).into(mChooseEmotionBtn3);
            Picasso.get().load(mLoveImages.get(3)).into(mChooseEmotionBtn4);
        }

        if(e.equals("sadness")) {
            Picasso.get().load(mSadnessImages.get(0)).into(mChooseEmotionBtn1);
            Picasso.get().load(mSadnessImages.get(1)).into(mChooseEmotionBtn2);
            Picasso.get().load(mSadnessImages.get(2)).into(mChooseEmotionBtn3);
            Picasso.get().load(mSadnessImages.get(3)).into(mChooseEmotionBtn4);
        }

        if(e.equals("surprise")) {
            Picasso.get().load(mSurpriseImages.get(0)).into(mChooseEmotionBtn1);
            Picasso.get().load(mSurpriseImages.get(1)).into(mChooseEmotionBtn2);
            Picasso.get().load(mSurpriseImages.get(2)).into(mChooseEmotionBtn3);
        }

        if(e.equals("joy")) {
            Picasso.get().load(mJoyImages.get(0)).into(mChooseEmotionBtn1);
            Picasso.get().load(mJoyImages.get(1)).into(mChooseEmotionBtn2);
            Picasso.get().load(mJoyImages.get(2)).into(mChooseEmotionBtn3);
            Picasso.get().load(mJoyImages.get(3)).into(mChooseEmotionBtn4);
        }

        mChooseEmotionBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.equals("anger")) Picasso.get().load(mAngerImages.get(0)).into(mPhotoEditorView.getSource());
                if(e.equals("fear")) Picasso.get().load(mFearImages.get(0)).into(mPhotoEditorView.getSource());
                if(e.equals("love")) Picasso.get().load(mLoveImages.get(0)).into(mPhotoEditorView.getSource());
                if(e.equals("sadness")) Picasso.get().load(mSadnessImages.get(0)).into(mPhotoEditorView.getSource());
                if(e.equals("surprise")) Picasso.get().load(mSurpriseImages.get(0)).into(mPhotoEditorView.getSource());
                if(e.equals("joy")) Picasso.get().load(mJoyImages.get(0)).into(mPhotoEditorView.getSource());
                pw.dismiss();
            }
        });

        mChooseEmotionBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.equals("anger")) Picasso.get().load(mAngerImages.get(1)).into(mPhotoEditorView.getSource());
                if(e.equals("fear")) Picasso.get().load(mFearImages.get(1)).into(mPhotoEditorView.getSource());
                if(e.equals("love")) Picasso.get().load(mLoveImages.get(1)).into(mPhotoEditorView.getSource());
                if(e.equals("sadness")) Picasso.get().load(mSadnessImages.get(1)).into(mPhotoEditorView.getSource());
                if(e.equals("surprise")) Picasso.get().load(mSurpriseImages.get(1)).into(mPhotoEditorView.getSource());
                if(e.equals("joy")) Picasso.get().load(mJoyImages.get(1)).into(mPhotoEditorView.getSource());
                pw.dismiss();
            }
        });

        mChooseEmotionBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.equals("anger")) Picasso.get().load(mAngerImages.get(2)).into(mPhotoEditorView.getSource());
                if(e.equals("fear")) Picasso.get().load(mFearImages.get(2)).into(mPhotoEditorView.getSource());
                if(e.equals("love")) Picasso.get().load(mLoveImages.get(2)).into(mPhotoEditorView.getSource());
                if(e.equals("sadness")) Picasso.get().load(mSadnessImages.get(2)).into(mPhotoEditorView.getSource());
                if(e.equals("surprise")) Picasso.get().load(mSurpriseImages.get(2)).into(mPhotoEditorView.getSource());
                if(e.equals("joy")) Picasso.get().load(mJoyImages.get(2)).into(mPhotoEditorView.getSource());
                pw.dismiss();
            }
        });

        mChooseEmotionBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.equals("anger")) Picasso.get().load(mAngerImages.get(3)).into(mPhotoEditorView.getSource());
                if(e.equals("fear")) Picasso.get().load(mFearImages.get(3)).into(mPhotoEditorView.getSource());
                if(e.equals("love")) Picasso.get().load(mLoveImages.get(3)).into(mPhotoEditorView.getSource());
                if(e.equals("sadness")) Picasso.get().load(mSadnessImages.get(3)).into(mPhotoEditorView.getSource());
                if(e.equals("joy")) Picasso.get().load(mJoyImages.get(3)).into(mPhotoEditorView.getSource());
                pw.dismiss();
            }
        });
        mCloseEmotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }
*/
    public void saveImage()
    {
        if (ActivityCompat.checkSelfPermission(EditResultActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "저장 권한을 승인받아야 합니다", Toast.LENGTH_LONG).show();
            return;
        }


        File file = new File(mFilePath);
        if(!file.exists()) file.mkdir();

        mPhotoEditor.saveImage(mFileName, new PhotoEditor.OnSaveListener() {
            @Override
            public void onSuccess(@NonNull String imagePath) {
                saved = true;
                Log.e("PhotoEditor", "Image Saved Successfully");
                Toast.makeText(getApplicationContext(), "저장하였습니다", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("PhotoEditor", "Failed to save image");
                Toast.makeText(getApplicationContext(), "저장에 실패했습니다", Toast.LENGTH_LONG).show();
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
        Uri file_uri = Uri.fromFile(new File(mFileName));

        StorageReference mUserImageRef = mStorage.child("user_images/"+ userID +"/" +file_uri.getLastPathSegment());
        UploadTask uploadTask = mUserImageRef.putFile(file_uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Image upload successfully");
            }
        });
    }
}