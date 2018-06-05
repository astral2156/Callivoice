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
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;

public class EditResultActivity extends AppCompatActivity{

    private Button mBackBtn;
    private Button mFontBtn;

    private Button mEmotionGalleryBtn;
    final Context context = this;
    private PhotoEditorView mPhotoEditorView;
    private PhotoEditor mPhotoEditor;
    private StorageReference mStorage;
    private DatabaseReference mImageDB;
    private ArrayList <String> mTextArr = new ArrayList<>();
    private ArrayList<String> mUsersText = new ArrayList<>();
    private ArrayList<String> mUsersAnger = new ArrayList<>();
    private ArrayList<String> mUsersLove = new ArrayList<>();;
    private ArrayList<String> mUsersSurprise = new ArrayList<>();
    private ArrayList<String> mUsersSad = new ArrayList<>();
    private ArrayList<String> mUsersJoy = new ArrayList<>();
    private ArrayList<String> mUsersFear = new ArrayList<>();
    private ArrayList<String> mAngerImages = new ArrayList();
    private ArrayList<String> mLoveImages = new ArrayList();
    private ArrayList<String> mFearImages = new ArrayList();
    private ArrayList<String> mSurpriseImages = new ArrayList();
    private ArrayList<String> mSadnessImages = new ArrayList();
    private ArrayList<String> mJoyImages = new ArrayList();
    private ArrayList<String> Wordmass = new ArrayList<>();
    private final int nothing = 0;
    private final int love = 1;
    private final int anger = 2;
    private final int sadness = 3;
    private final int joy = 4;
    private final int fear = 5;
    private final int surprise = 6;
    private int selectedFont = R.font.basefont;
    private String emotion;
    private String myText;
    private String Txt;
    private String resultTxt;
    private int inde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final CalliVoice userdata = (CalliVoice)getApplication();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_result);

        final Typeface mSetFont = ResourcesCompat.getFont(EditResultActivity.this, selectedFont);
        Intent intent = getIntent();
        //emotion = intent.getStringExtra("emotion");
        emotion = userdata.UsersEmotion;
        mUsersText = userdata.UsersText;
        mUsersAnger = userdata.UsersAnger;
        mUsersFear = userdata.UsersFear;
        mUsersJoy = userdata.UsersJoy;
        mUsersLove = userdata.UsersLove;
        mUsersSurprise = userdata.UsersSurprise;
        mUsersSad = userdata.UsersSad;

        System.out.println("emotion is "+ emotion);

        final TextView tv = (TextView)findViewById(R.id.tv); //임시로 보여주기

        //단어 조합

        resultTxt = "";
        myText = intent.getStringExtra("text");
        int textL = 0;
        int []WhereColorChange = new int[75];
        Arrays.fill(WhereColorChange,999);
        inde = 0;
        System.out.println("inde : "+inde);
        int color = Color.RED;
        for(int i=0;i<mUsersText.size();i++)
        {
            System.out.println(mUsersText);
            int FindWordIndex = 0;
            String word = mUsersText.get(i);

            if(emotion == "love")
            {
                String keyword = mUsersLove.get(FindWordIndex);

                if(word.contains(keyword))
                {
                    System.out.println("keyword is "+keyword);
                    WhereColorChange[inde*2] = textL+word.indexOf(keyword);
                    WhereColorChange[inde*2+1] = textL+word.indexOf(keyword)+keyword.length();
                    FindWordIndex ++;
                    inde +=1;
                }
            }

            if(emotion == "anger")
            {
                String keyword = mUsersAnger.get(FindWordIndex);

                if(word.contains(keyword))
                {
                    System.out.println("keyword is "+keyword);

                    WhereColorChange[inde*2] = textL+word.indexOf(keyword);
                    WhereColorChange[inde*2+1] = textL+word.indexOf(keyword)+keyword.length();

                    FindWordIndex ++;
                    inde +=1;
                }
            }

            if(emotion == "joy")
            {
                String keyword = mUsersJoy.get(FindWordIndex);

                if(word.contains(keyword))
                {
                    WhereColorChange[inde*2] = textL+word.indexOf(keyword);

                    WhereColorChange[inde*2+1] = textL+word.indexOf(keyword)+keyword.length();

                    FindWordIndex ++;
                    inde +=1;
                }
            }

            if(emotion == "sadness")
            {
                String keyword = mUsersSad.get(FindWordIndex);

                if(word.contains(keyword))
                {
                    System.out.println("keyword is "+keyword);

                    WhereColorChange[inde*2] = textL+word.indexOf(keyword);
                    WhereColorChange[inde*2+1] = textL+word.indexOf(keyword)+keyword.length();

                    FindWordIndex ++;
                    inde +=1;
                }
            }

            if(emotion == "surprise")
            {
                String keyword = mUsersSurprise.get(FindWordIndex);

                if(word.contains(keyword))
                {
                    System.out.println("keyword is "+keyword);

                    WhereColorChange[inde*2] = textL+word.indexOf(keyword);
                    WhereColorChange[inde*2+1] = textL+word.indexOf(keyword)+keyword.length();

                    FindWordIndex ++;
                    inde +=1;
                }
            }

            if(emotion == "fear")
            {
                String keyword = mUsersFear.get(FindWordIndex);

                if(word.contains(keyword))
                {
                    System.out.println("keyword is "+keyword);

                    WhereColorChange[inde*2] = textL+word.indexOf(keyword);
                    WhereColorChange[inde*2+1] = textL+word.indexOf(keyword)+keyword.length();

                    FindWordIndex ++;
                    inde +=1;
                }
            }

            textL =textL+mUsersText.get(i).length()+1;
            resultTxt = resultTxt+word+" ";
            System.out.println("resultTxt is "+ resultTxt);

            if(i == mUsersText.size()-1)
            {
                Wordmass.add(resultTxt);
                resultTxt="";
                System.out.println("Complete");
            }

            else if(resultTxt.length()+mUsersText.get(i+1).length() > 10)
            {
                System.out.println(resultTxt.length()+mUsersText.get(i+1).length());
                Wordmass.add(resultTxt);
                resultTxt="";
                System.out.println("Complete");
            }
        }



        //myText = intent.getStringExtra("text");

        System.out.println("text is "+myText);
        System.out.println("textmass is " + Wordmass);
        System.out.println("colorIndex is "+ WhereColorChange);

        SpannableStringBuilder Result = new SpannableStringBuilder(myText);

        ChangeColor(Result,WhereColorChange,color,inde);

        inde =0;

        tv.append(Result);

        mPhotoEditorView = findViewById(R.id.photoEditorView);

        mPhotoEditor = new PhotoEditor.Builder(this,mPhotoEditorView).setPinchTextScalable(true).build();
        mStorage = FirebaseStorage.getInstance().getReference();
        mImageDB = FirebaseDatabase.getInstance().getReference().child("Images");
        TextView mCalliText = findViewById(R.id.calliTextView);

        //tv.setText(Html.fromHtml(resultTxt));
        resultTxt ="";//결과값 초기화

        mBackBtn = (Button) findViewById(R.id.back);
        mFontBtn = (Button) findViewById(R.id.font);
        mEmotionGalleryBtn = (Button) findViewById(R.id.emotionGalleryBtn);


        String callitext = intent.getStringExtra("CALLITEXT_KEY");
        mCalliText.setText(callitext);

        mImageDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int imgCount=0;
                int randomImage = 0;
                if(emotion.equals("anger")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("anger").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mAngerImages.add(value);
                        }
                    }
                    randomImage =(int)(Math.random()*imgCount+1);
                    //mPhotoEditor.addText(myText, Color.rgb(255,255,255));
                    PutOnTxt(Wordmass);
                    Picasso.get().load(mAngerImages.get(randomImage-1)).into(mPhotoEditorView.getSource());
                }
                if(emotion.equals("fear")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("fear").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mFearImages.add(value);
                        }
                    }
                    randomImage =(int)(Math.random()*imgCount+1);
                    mPhotoEditor.addText(myText, Color.rgb(255,255,255));
                    Picasso.get().load(mFearImages.get(randomImage-1)).into(mPhotoEditorView.getSource());
                }

                if(emotion.equals("love")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("love").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mLoveImages.add(value);
                        }
                    }
                    randomImage =(int)(Math.random()*imgCount+1);
                    //mPhotoEditor.addText(myText, Color.rgb(255,255,255));
                    PutOnTxt(Wordmass);
                    Picasso.get().load(mLoveImages.get(randomImage-1)).into(mPhotoEditorView.getSource());
                }

                if(emotion.equals("sadness")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("sadness").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mSadnessImages.add(value);
                        }
                    }
                    randomImage =(int)(Math.random()*imgCount+1);
                    //mPhotoEditor.addText(myText, Color.rgb(255,255,255));
                    PutOnTxt(Wordmass);
                    Picasso.get().load(mSadnessImages.get(randomImage-1)).into(mPhotoEditorView.getSource());
                }

                if(emotion.equals("surprise")) {
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
                    randomImage =(int)(Math.random()*imgCount+1);
                    //mPhotoEditor.addText(myText, Color.rgb(255,255,255));
                    PutOnTxt(Wordmass);
                    Picasso.get().load(mSurpriseImages.get(randomImage-1)).into(mPhotoEditorView.getSource());
                }

                if(emotion.equals("joy")) {
                    for (DataSnapshot childSnapshot : dataSnapshot.child("joy").getChildren()) {
                        String key = childSnapshot.getKey();
                        if (key.equals("Counter")) {
                            imgCount = Integer.parseInt(childSnapshot.getValue().toString());
                        } else if (!key.equals("Counter")) {
                            String value = childSnapshot.getValue(String.class);
                            mJoyImages.add(value);
                        }
                    }
                    randomImage =(int)(Math.random()*imgCount+1);
                    //mPhotoEditor.addText(myText, Color.rgb(255,255,255));
                    PutOnTxt(Wordmass);
                    Picasso.get().load(mJoyImages.get(randomImage-1)).into(mPhotoEditorView.getSource());
                }
                System.out.println(imgCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

            public void onBackPressed(){

            }
        });


        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(context);
                exitDialogBuilder.setTitle("종료하기");
                exitDialogBuilder.setMessage("저장하지 않고 편집을 종료하시겠습니까?").setCancelable(false)
                        .setPositiveButton("종료",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        resultTxt="";
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

        mFontBtn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                displayPopup();
               /*Intent intent = new Intent(EditResultActivity.this, PopupFontActivity.class);
               startActivity(intent);*/
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Wordmass.clear();
    }

    public void displayPopup() {
        System.out.println("textmass is "+Wordmass);
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

        mChooseFontBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFont = R.font.thefaceshop;
                Typeface mTheFaceShopTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                //mPhotoEditor.addText(mTheFaceShopTf, myText, Color.rgb(255,255,255));
                PutOnTxt(mTheFaceShopTf,Wordmass);
                pw.dismiss();
            }
        });
        mChooseFontBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.nanumbrush;
                Typeface mNanumBrushTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                //mPhotoEditor.addText(mNanumBrushTf, myText, Color.rgb(255,255,255));
                PutOnTxt(mNanumBrushTf,Wordmass);
                pw.dismiss();
            }
        });

        mChooseFontBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.seoulhangang;
                Typeface mSeoulHangangTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                //mPhotoEditor.addText(mSeoulHangangTf, myText, Color.rgb(255,255,255));
                PutOnTxt(mSeoulHangangTf,Wordmass);
                pw.dismiss();
            }
        });
        mChooseFontBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.menbal;
                Typeface mMenbalTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                //mPhotoEditor.addText(mMenbalTf, myText, Color.rgb(255,255,255));
                PutOnTxt(mMenbalTf,Wordmass);
                pw.dismiss();
            }
        });
        mChooseFontBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.leesunsin;
                Typeface mLeeSunSinTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                //mPhotoEditor.addText(mLeeSunSinTf, myText, Color.rgb(255,255,255));
                PutOnTxt(mLeeSunSinTf,Wordmass);
                pw.dismiss();
            }
        });
        mChooseFontBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFont = R.font.bingrae;
                Typeface mBingraeTf = ResourcesCompat.getFont(EditResultActivity.this,selectedFont);
                mPhotoEditor.clearAllViews();
                //mPhotoEditor.addText(mBingraeTf, myText, Color.rgb(255,255,255));
                PutOnTxt(mBingraeTf,Wordmass);
                pw.dismiss();
            }
        });
    }

    public static void ChangeColor(SpannableStringBuilder Result, int [] array, int color, int index)
    {
        for(int i=0;i<index;i++)
        {
            Result.setSpan(new
                            ForegroundColorSpan(color),array[i*2],array[i*2+1],
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

    }

    public void PutOnTxt(ArrayList txt)
    {
        PutOnTxt(null, txt);
    }

    public void PutOnTxt(@Nullable Typeface textTypeface, ArrayList<String> txt)
    {
        String word = txt.get(0);

        for(int i =1;i<txt.size();i++)
        {
            word=word+System.lineSeparator()+txt.get(i);
        }
        if(textTypeface != null)
        {
                mPhotoEditor.addText(textTypeface, word,Color.rgb(255,255,255));
        }
        else
        {
                mPhotoEditor.addText(word,Color.rgb(255,255,255));
        }
    }

}