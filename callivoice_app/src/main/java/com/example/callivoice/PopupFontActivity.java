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

public class PopupFontActivity extends Activity {

    private Button mChooseFontBtn1;
    private Button mChooseFontBtn2;
    private Button mChooseFontBtn3;
    private Button mChooseFontBtn4;
    private Button mChooseFontBtn5;
    private Button mChooseFontBtn6;

    private int FONT_THEFACESHOP = 1;
    private int FONT_NANUM = 2;
    private int FONT_SEOUL = 3;
    private int FONT_MENBAL = 4;
    private int FONT_LEESUNSIN = 5;
    private int FONT_BINGRAE = 6;
    private int CLOSE = 0;

    private int mfont = FONT_THEFACESHOP;

    private Button mCloseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_font);


        //font window size

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.8));




        final Intent intent = getIntent();


        mChooseFontBtn1 = (Button) findViewById(R.id.fontBtn1);
        mChooseFontBtn2 = (Button) findViewById(R.id.fontBtn2);
        mChooseFontBtn3 = (Button) findViewById(R.id.fontBtn3);
        mChooseFontBtn4 = (Button) findViewById(R.id.fontBtn4);
        mChooseFontBtn5 = (Button) findViewById(R.id.fontBtn5);
        mChooseFontBtn6 = (Button) findViewById(R.id.fontBtn6);

        mCloseBtn = (Button) findViewById(R.id.closePopupBtn);

        mChooseFontBtn1.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont  = FONT_THEFACESHOP; selectFont();          }
        });
        mChooseFontBtn2.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont  = FONT_NANUM; selectFont();          }
        });
        mChooseFontBtn3.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont  = FONT_SEOUL; selectFont();          }
        });
        mChooseFontBtn4.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont  = FONT_MENBAL; selectFont();          }
        });
        mChooseFontBtn5.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont  = FONT_LEESUNSIN; selectFont();          }
        });
        mChooseFontBtn6.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont  = FONT_BINGRAE; selectFont();          }
        });


        mCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {                mfont = CLOSE; selectFont();            }
        });


    }

    private void selectFont() {
        if(mfont == CLOSE) finish();
        else
        {
            Intent intent = new Intent();
            intent.putExtra("FONT_NUMBER", mfont);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}