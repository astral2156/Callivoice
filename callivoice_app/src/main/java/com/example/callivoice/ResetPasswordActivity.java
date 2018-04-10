package com.example.callivoice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText mUserEmail;
    private Button mResetPasswordBtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mUserEmail = (EditText) findViewById(R.id.userEmail);
        mResetPasswordBtn = (Button) findViewById(R.id.resetPasswordBtn);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mUserEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "가입된 이메일을 입력하세요",Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "비밀번호 재설정하기 위해서 이메일을 보냈습니다", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ResetPasswordActivity.this, "재설정 이메일을 보내지 못했습니다 ㅠ", Toast.LENGTH_LONG).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
