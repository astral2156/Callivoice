package com.example.callivoice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;

public class UserImageEditActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDB;
    private DatabaseReference mImageDB;
    private StorageReference mStorage;
    private StorageReference mUserStorage;
    public static PhotoEditorView mPhotoEditorView;
    private PhotoEditor mPhotoEditor;
    private Button editFont;
    private Button editBackground;
    private Button addEmoji;
    private Button backBtn;
    private Button confirmEditBtn;
    private int selectedFont = R.font.basefont;
    private String userText;
    public static String singleImageUrl;
    public static String emotion;
    private String mKey;
    private String isCurrent;
    private String fromImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_image_edit);

        Intent intent = getIntent();
        fromImg = intent.getStringExtra("resultUrl");

        editFont = (Button) findViewById(R.id.changeFontBtn);
        editBackground = (Button) findViewById(R.id.emotionGalleryBtn);
        addEmoji = (Button) findViewById(R.id.emojiBtn);
        backBtn = (Button) findViewById(R.id.mBackBtn);
        confirmEditBtn = (Button) findViewById(R.id.confirmEditBtn);

        mPhotoEditorView = findViewById(R.id.photoEditorViewForEdit);
        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView).setPinchTextScalable(true).build();


        String name = new Date().toString() + ".jpg";
        mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getUid();
        mUserDB = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("images");
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl(fromImg);
        mUserStorage = FirebaseStorage.getInstance().getReference().child("user_images/" + currentUser+"/"+name);
        mUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot mSnapshot:dataSnapshot.getChildren()) {
                    for(DataSnapshot childSnapshot:mSnapshot.getChildren()) {
                        final String key = childSnapshot.getKey();

                        if(key.equals("result_img")){
                            String value = childSnapshot.getValue().toString();
                            isCurrent = value;
                            if(fromImg.equals(isCurrent)) {
                                String parentKey = childSnapshot.getRef().getParent().getKey();
                                System.out.println(parentKey);
                                singleImageUrl = dataSnapshot.child(parentKey).child("src_img").getValue().toString();
                                userText = dataSnapshot.child(parentKey).child("text").getValue().toString();
                                emotion = dataSnapshot.child(parentKey).child("emotion").getValue().toString();
                                Picasso.get().load(singleImageUrl).into(mPhotoEditorView.getSource());
                                mPhotoEditor.addText(userText, Color.rgb(255, 255, 255));
                                mKey = parentKey;
                            }
                            System.out.println("System>OUT " + isCurrent);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
            @Override
            public void onEditTextChangeListener(View rootView, String text, int colorCode) {
                mPhotoEditor.editText(rootView, text, colorCode);

            }

            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onRemoveViewListener(int numberOfAddedViews) {

            }

            @Override
            public void onStartViewChangeListener(ViewType viewType) {

            }

            @Override
            public void onStopViewChangeListener(ViewType viewType) {

            }
        });

        editFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFont();
            }
        });

        editBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isResultActivity = false;
                Intent intent = new Intent(UserImageEditActivity.this, EmotionGalleryActivity.class);
                intent.putExtra("mEmotion", emotion);
                intent.putExtra("isResultActivity", isResultActivity);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirmEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            mPhotoEditorView.setDrawingCacheEnabled(true);
                            mPhotoEditorView.buildDrawingCache();
                            Bitmap bitmap = mPhotoEditorView.getDrawingCache();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = mUserStorage.putBytes(data);
                            mStorage.delete();

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful()) throw task.getException();
                                    return mUserStorage.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        String url = downloadUri.toString();
                                        mUserDB.child(mKey).child("src_img").setValue(singleImageUrl);
                                        mUserDB.child(mKey).child("result_img").setValue(url);
                                        mUserDB.child(mKey).child("text").setValue(userText);
                                        fromImg = url;
                                    }
                                }
                            });

                Intent intent = new Intent(UserImageEditActivity.this, UserGalleryActivity.class);
                finish();
                startActivity(intent);
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
        pw.showAtLocation(findViewById(R.id.imageEditLayout), Gravity.CENTER, 0, 0);

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
                Typeface mTheFaceShopTf = ResourcesCompat.getFont(UserImageEditActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mTheFaceShopTf, userText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.nanumbrush;
                Typeface mNanumBrushTf = ResourcesCompat.getFont(UserImageEditActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mNanumBrushTf, userText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });

        mChooseFontBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.seoulhangang;
                Typeface mSeoulHangangTf = ResourcesCompat.getFont(UserImageEditActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mSeoulHangangTf, userText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.menbal;
                Typeface mMenbalTf = ResourcesCompat.getFont(UserImageEditActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mMenbalTf, userText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.leesunsin;
                Typeface mLeeSunSinTf = ResourcesCompat.getFont(UserImageEditActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mLeeSunSinTf, userText, Color.rgb(255,255,255));
                pw.dismiss();
            }
        });
        mChooseFontBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.bingrae;
                Typeface mBingraeTf = ResourcesCompat.getFont(UserImageEditActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                mPhotoEditor.addText(mBingraeTf, userText, Color.rgb(255,255,255));
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
}
