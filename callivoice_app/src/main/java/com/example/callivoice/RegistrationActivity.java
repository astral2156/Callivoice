package com.example.callivoice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mUserEmail;
    private EditText mUserPassword;
    private Button mRegisterBtn;
    private FirebaseAuth firebaseAuth;
    private EditText mUserPasswordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUserName = (EditText) findViewById(R.id.userName);
        mUserEmail = (EditText) findViewById(R.id.userEmail);
        mUserPassword = (EditText) findViewById(R.id.userPassword);
        mRegisterBtn = (Button) findViewById(R.id.signupBtn);
        mUserPasswordRepeat = (EditText) findViewById(R.id.userPasswordRepeat);

        firebaseAuth = FirebaseAuth.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }

        });
    }

        public void startSignUp () {
            if (!mUserPassword.getText().toString().equals(mUserPasswordRepeat.getText().toString())) {
                Toast.makeText(RegistrationActivity.this, "비밀번호를 다시 입력해 주세요", Toast.LENGTH_LONG).show();
                return;
            } else {
                final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "잠시만 기다려 주세요...", "실행합니다...", true);
                firebaseAuth.createUserWithEmailAndPassword(mUserEmail.getText().toString(), mUserPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();


                        if (task.isSuccessful()) {

                            Toast.makeText(RegistrationActivity.this, "회원가입이 완료되었습니다", Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Log.e("Error", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
}
