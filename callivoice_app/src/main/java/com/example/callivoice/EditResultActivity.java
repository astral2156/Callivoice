package com.example.callivoice;

/**
 * Created by Lss on 2018-05-01.
 */


import android.app.AlertDialog;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditResultActivity extends AppCompatActivity{

    /*
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private Bundle bundle = new Bundle();
    */
    private ViewResultFragmentActivity resultFrag;
    private Bundle bundle;

    private Button mBackBtn;
    private Button mFontBtn;
    private Button mBgBtn;

    // 액티비티간 intent 이동을 위한 상수값
    private final int REQUEST_FONT = 100;
    private final int REQUEST_BACKGROUND = 200;

    private int font_now = 1;
    /* font number
    1 = thefaceshop
    2 = nanum
    3 = seoul
    4 = menbal
    5 = leesunsin
    6 = bingrae
     */

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_result);

        final Intent intent = getIntent();

        //메인에서 intent를 이용한 데이터 불러오기 테스트
        TextView mCalliText = findViewById(R.id.calliTextView);
        String callitext = intent.getStringExtra("CALLITEXT_KEY");
        mCalliText.setText(callitext);


        //결과물 창을 fragment로 나타내기 위한 코드
        resultFrag = new ViewResultFragmentActivity();
        bundle = new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.resultImage, resultFrag);
        fragmentTransaction.commit();
        bundle.putString("string", callitext);
        bundle.putInt("font", font_now);
        resultFrag.setArguments(bundle);

        mBackBtn = (Button) findViewById(R.id.back);
        mFontBtn = (Button) findViewById(R.id.font);
        mBgBtn = (Button) findViewById(R.id.background);

        //뒤로가기 버튼
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

        //폰트 버튼
        mFontBtn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                Intent intent_font = new Intent(EditResultActivity.this, PopupFontActivity.class);
                startActivityForResult(intent_font, REQUEST_FONT);

            }
        });

        //배경 버튼
        mBgBtn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                Intent intent_font = new Intent(EditResultActivity.this, PopupBackgroundActivity.class);
                startActivityForResult(intent_font, REQUEST_BACKGROUND);

            }
        });
    }

    //intent로 이동했던 액티비티에서 전달한 데이터 덧씌우기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_FONT) {
            font_now = data.getIntExtra("FONT_NUMBER",font_now);
            bundle.putInt("font", font_now);
            resultFrag.setArguments(bundle);
            resultFrag.changeFont();
        }

        else if (requestCode == REQUEST_BACKGROUND) {
            return;
        }
    }

}