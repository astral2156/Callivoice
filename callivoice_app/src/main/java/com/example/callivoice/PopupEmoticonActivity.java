package com.example.callivoice;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopupEmoticonActivity extends Activity {

    private Button mChooseEmoticonBtn1;
    private Button mChooseEmoticonBtn2;
    private Button mChooseEmoticonBtn3;
    private Button mChooseEmoticonBtn4;
    private Button mChooseEmoticonBtn5;
    private Button mChooseEmoticonBtn6;
    private Button mChooseEmoticonBtn7;
    private Button mChooseEmoticonBtn8;

    private Button mCloseBtn;

    private int selectedEmoticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_emoticon_back);

        //mPhotoEditorView = findViewById(R.id.photoEditorView);

        DisplayMetrics dm = new DisplayMetrics();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        wlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        wlp.y = 100;
        window.setAttributes(wlp);
        window.setLayout((int)(width*.8),(int)(height*.9));

        Intent intent = getIntent();
        mChooseEmoticonBtn1 = findViewById(R.id.emBtn1);
        mChooseEmoticonBtn2 = findViewById(R.id.emBtn2);
        mChooseEmoticonBtn3 = findViewById(R.id.emBtn5);
        mChooseEmoticonBtn4 = findViewById(R.id.emBtn6);
        mChooseEmoticonBtn5 = findViewById(R.id.emBtn9);
        mChooseEmoticonBtn6 = findViewById(R.id.emBtn10);
        mChooseEmoticonBtn7 = findViewById(R.id.emBtn13);
        mChooseEmoticonBtn8 = findViewById(R.id.emBtn14);

        mCloseBtn = (Button) findViewById(R.id.closePopupBtn);

        mCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mChooseEmoticonBtn1.setOnClickListener(new View.OnClickListener() { //추후 더 수정
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}