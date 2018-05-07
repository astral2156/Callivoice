package com.example.callivoice;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerActivity;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.impl.util.PermissionUtils;
import android.Manifest;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button mRecordButton;
    private SpeechRecognizerClient client;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private EditText mUserText;
    private Button mSendBtn;
    private Button mGoToResultBtn; //activity_edit_result로 이동하는 버튼. 임시로 추가함 by 이성수
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        mRecordButton = (Button) findViewById(R.id.voiceRecordBtn);
        mUserText = (EditText) findViewById(R.id.userSpeechText);
        mSendBtn = (Button) findViewById(R.id.sendToDB);
        mGoToResultBtn = (Button) findViewById(R.id.GoToResult);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if(matches!=null) {
                    mUserText.setText(matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();


        //Kakao API 사용:
        /*
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("말씀해주세요~");
        mProgressDialog.setCancelable(false);

        SpeechRecognizerManager.getInstance().initializeLibrary(this);
        String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;
        String apiKey = "d496072a4fb23cfdfc0499425b618be1";
        SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().setAppKey(apiKey).setServiceType(serviceType);
        client = builder.build();
        client.setSpeechRecognizeListener(new SpeechRecognizeListener() {
            @Override
            public void onReady() {
                if(client==null) { return; }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.show();
                        }
                    });
                }
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                client.stopRecording();
            }

            @Override
            public void onPartialResult(String partialResult) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
                if(matches!=null) {
                    mUserSpeechTextView.setText (matches.get(0));
                }
            }

            @Override
            public void onAudioLevel(float audioLevel) {

            }

            @Override
            public void onFinished() {

            }
        });
        client.startRecording(true);
        */

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mText = mUserText.getText().toString();

                if(TextUtils.isEmpty(mText)) {
                    Toast.makeText(MainActivity.this, "입력한 텍스트가 없습니다", Toast.LENGTH_LONG).show();
                }
                else {
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                    currentUserDB.child("userText");

                    String userTextString = mUserText.getText().toString();

                    currentUserDB.child("userText").push().setValue(userTextString);
                    Intent i = new Intent(MainActivity.this, EmotionRecognitionActivity.class);
                    startActivity(i);
                }
            }
        });


        mRecordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        mUserText.setHint("오늘은 어떤 글을 쓰고 싶으신가요?");

                    break;
                    case MotionEvent.ACTION_DOWN:
                        mUserText.setText("");
                        mUserText.setHint("녹음 중..");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

                        break;
                }
                return false;
            }
        });

        mGoToResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mText = "안녕하세요";

                Intent i = new Intent(MainActivity.this, EditResultActivity.class);
                i.putExtra("CALLITEXT_KEY", "안녕하세요");
                startActivity(i);
            }
        });

    }

    /*
    public void onDestroy() {
        super.onDestroy();

        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }
*/

    public void onsettingBtnClicked(View v) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }

    }

}




