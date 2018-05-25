package com.example.callivoice;

/**
 * Created by Lss on 2018-05-01.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PopupBackgroundActivity extends Activity {

    ImageButton[] mChooseBgBtn = new ImageButton[23];

    int bg = 1;
    int CLOSE = 0;

    private Button mCloseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_background);


        //font window size

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.9));




        final Intent intent = getIntent();


        mChooseBgBtn[0] = (ImageButton) findViewById(R.id.bgBtn1);
        mChooseBgBtn[1] = (ImageButton) findViewById(R.id.bgBtn2);
        mChooseBgBtn[2] = (ImageButton) findViewById(R.id.bgBtn3);
        mChooseBgBtn[3] = (ImageButton) findViewById(R.id.bgBtn4);
        mChooseBgBtn[4] = (ImageButton) findViewById(R.id.bgBtn5);
        mChooseBgBtn[5] = (ImageButton) findViewById(R.id.bgBtn6);
        mChooseBgBtn[6] = (ImageButton) findViewById(R.id.bgBtn7);
        mChooseBgBtn[7] = (ImageButton) findViewById(R.id.bgBtn8);
        mChooseBgBtn[8] = (ImageButton) findViewById(R.id.bgBtn9);
        mChooseBgBtn[9] = (ImageButton) findViewById(R.id.bgBtn10);
        mChooseBgBtn[10] = (ImageButton) findViewById(R.id.bgBtn11);
        mChooseBgBtn[11] = (ImageButton) findViewById(R.id.bgBtn12);
        mChooseBgBtn[12] = (ImageButton) findViewById(R.id.bgBtn13);
        mChooseBgBtn[13] = (ImageButton) findViewById(R.id.bgBtn14);
        mChooseBgBtn[14] = (ImageButton) findViewById(R.id.bgBtn15);
        mChooseBgBtn[15] = (ImageButton) findViewById(R.id.bgBtn16);
        mChooseBgBtn[16] = (ImageButton) findViewById(R.id.bgBtn17);
        mChooseBgBtn[17] = (ImageButton) findViewById(R.id.bgBtn18);
        mChooseBgBtn[18] = (ImageButton) findViewById(R.id.bgBtn19);
        mChooseBgBtn[19] = (ImageButton) findViewById(R.id.bgBtn20);
        mChooseBgBtn[20] = (ImageButton) findViewById(R.id.bgBtn21);
        mChooseBgBtn[21] = (ImageButton) findViewById(R.id.bgBtn22);
        mChooseBgBtn[22] = (ImageButton) findViewById(R.id.bgBtn23);


        mCloseBtn = (Button) findViewById(R.id.closePopupBtn);
/*
        for(int i=0; i<23; i++)
        {
            int now = i;
            mChooseBgBtn[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {                bg  = now; selectFont();          }
            });
        }
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
*/

    }

  /*  private void selectFont() {
        if(mfont == CLOSE) finish();
        else
        {
            Intent intent = new Intent();
            intent.putExtra("FONT_NUMBER", mfont);
            setResult(RESULT_OK, intent);
            finish();
        }
    }*/
}