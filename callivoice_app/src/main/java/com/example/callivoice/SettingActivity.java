package com.example.callivoice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {

    private Button bgmOnOff;
    private Button appRead;
    private Button UserInfo;
    private Button LogoutBtn;
    private Button endSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button b = (Button) findViewById(R.id.appRead); // 세팅화면에서 앱 사용 방법 눌렀을 때 팝업화면

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,PopActivitySetting.class));

            }
        });
    }
    public void endSettingBtnClicked(View v){//뒤로가기 버튼
        Toast.makeText(getApplicationContext(),"뒤로가기", Toast.LENGTH_LONG).show();
        finish();
    }


    public void onClickUserInfo(View v){ //user 정보 열기 창
        Intent intent = new Intent(getApplicationContext(),SettinginfoActivity.class);
        startActivity(intent);
    }



}
