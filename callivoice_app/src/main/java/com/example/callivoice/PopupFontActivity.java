package com.example.callivoice;

/**
 * Created by Lss on 2018-05-01.
 */


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class PopupFontActivity extends Activity {

    private Button mChooseFontBtn1;
    private Button mChooseFontBtn2;
    private Button mChooseFontBtn3;
    private Button mChooseFontBtn4;
    private Button mChooseFontBtn5;
    private Button mChooseFontBtn6;

    private Button mCloseBtn;
    private PhotoEditorView mPhotoEditorView;
    private PhotoEditor mPhotoEditor;
    private int selectedFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_font);

        mPhotoEditorView = findViewById(R.id.photoEditorView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.8));

        Intent intent = getIntent();
        mChooseFontBtn1 = (Button) findViewById(R.id.fontBtn1);
        mChooseFontBtn2 = (Button) findViewById(R.id.fontBtn2);
        mChooseFontBtn3 = (Button) findViewById(R.id.fontBtn3);
        mChooseFontBtn4 = (Button) findViewById(R.id.fontBtn4);
        mChooseFontBtn5 = (Button) findViewById(R.id.fontBtn5);
        mChooseFontBtn6 = (Button) findViewById(R.id.fontBtn6);

        mCloseBtn = (Button) findViewById(R.id.closePopupBtn);

        mCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mChooseFontBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}