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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditResultActivity extends AppCompatActivity{

    private Button mBackBtn;
    private Button mFontBtn;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_result);

        TextView mCalliText = findViewById(R.id.calliTextView);

        Intent intent = getIntent();
        mBackBtn = (Button) findViewById(R.id.back);
        mFontBtn = (Button) findViewById(R.id.font);

        String callitext = intent.getStringExtra("CALLITEXT_KEY");
        mCalliText.setText(callitext);

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
                Intent popup_intent = new Intent(EditResultActivity.this, PopupFontActivity.class);
                startActivity(popup_intent);
            }
        });
    }

}