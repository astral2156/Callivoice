package com.example.callivoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class PopupEmoticonActivity extends Activity{

    private Button mChooseEmojiBtn1;
    private Button mChooseEmojiBtn2;
    private Button mChooseEmojiBtn3;
    private PhotoEditorView mPhotoEditorView;
    private PhotoEditor mPhotoEdior;

    private Button mCloseBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_emoticon);

        mPhotoEditorView = findViewById(R.id.photoEditorView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.8));

        Intent intent = getIntent();
        mChooseEmojiBtn1 = (Button) findViewById(R.id.emojiBtn1);
        mChooseEmojiBtn2 = (Button) findViewById(R.id.emojiBtn2);
        mChooseEmojiBtn3 = (Button) findViewById(R.id.emojiBtn3);

        mCloseBtn1 =  (Button) findViewById(R.id.closePopupBtn1);

        mCloseBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
