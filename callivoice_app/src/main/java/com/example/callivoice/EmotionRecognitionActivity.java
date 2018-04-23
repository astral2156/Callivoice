package com.example.callivoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmotionRecognitionActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private TextView mUserTextView;
    private ArrayList<String> mAngerWords = new ArrayList<>();
    private ArrayList<String> mJoyWords = new ArrayList<>();
    private ArrayList<String> mFearWords = new ArrayList<>();
    private ArrayList<String> mSadWords = new ArrayList<>();
    private ArrayList<String> mLoveWords = new ArrayList<>();
    private ArrayList<String> mSurpriseWords = new ArrayList<>();
    private ArrayList<String> joyList = new ArrayList<>();
    private ArrayList<String> angryList = new ArrayList<>();
    private ArrayList<String> fearList = new ArrayList<>();
    private ArrayList<String> sadnessList = new ArrayList<>();
    private ArrayList<String> loveList = new ArrayList<>();
    private ArrayList<String> surpriseList = new ArrayList<>();
    private int size;
    private TextView mFoundWordsTextView;
    private String myText;

    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_recognition);


        //identifying TextViews
        mUserTextView = (TextView) findViewById(R.id.userTextView);
        mFoundWordsTextView = (TextView) findViewById(R.id.wordList);

        //Creating firebase instance and reference. 파이어베이스와 연결 시킨다:
        mFirebaseDatabase = FirebaseDatabase.getInstance(); // 어플리케이션의 데이터베이스를 가져온다
        mDatabaseReference = mFirebaseDatabase.getReference(); //어플리케이션 데이터베이스 링크를 mDatabaseReference에 연결한다

        //로그인중 유저아이디와 사용자의 텍스트를 가져온다:
        //******************************************************************************************
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        final DatabaseReference currentUserDB = mDatabaseReference.child("Users").child(userID).child("userText");

        query = currentUserDB.orderByKey().limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mUserTextView.setText(value);
                myText = value;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        // *****************************************************************************************

        //실시간 데이터베이스에 들어가는 단어들을 읽고 감정을 분석하는 함수:
        readDB();


    }


    public void readDB () {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("emotions"); //데이터베이스에 있는 parent를 가리킨다 (예시: Callivoice(root)/emotions(parent))
        //가리킨 Reference에 ValueListener (데이터베이스 읽기):
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //emotions/'감정이름'에 들어가서 단어 하나씩 위에 만들어진 동일한 어래이리스트에 저장시킨다
                for (DataSnapshot childSnapshot : dataSnapshot.child("anger").getChildren()) {
                    String value = childSnapshot.getValue(String.class);
                    mAngerWords.add(value);
                }
                for (DataSnapshot childSnapshot : dataSnapshot.child("joy").getChildren()) {
                    String value = childSnapshot.getValue(String.class);
                    mJoyWords.add(value);
                }
                for (DataSnapshot childSnapshot : dataSnapshot.child("love").getChildren()) {
                    String value = childSnapshot.getValue(String.class);
                    mLoveWords.add(value);
                }
                for (DataSnapshot childSnapshot : dataSnapshot.child("fear").getChildren()) {
                    String value = childSnapshot.getValue(String.class);
                    mFearWords.add(value);
                }
                for (DataSnapshot childSnapshot : dataSnapshot.child("sadness").getChildren()) {
                    String value = childSnapshot.getValue(String.class);
                    mSadWords.add(value);
                }
                for (DataSnapshot childSnapshot : dataSnapshot.child("surprise").getChildren()) {
                    String value = childSnapshot.getValue(String.class);
                    mSurpriseWords.add(value);
                }

                //size는 현재 데이터베이스에 있는 emotion중 제일 큰 단어 회수 갖고 있는 감정의 값을 얻는다
                size = Math.max(Math.max(Math.max(mSurpriseWords.size(), mFearWords.size()),Math.max(mAngerWords.size(), mJoyWords.size())), Math.max(mSadWords.size(), mSurpriseWords.size()));

                //감정인식 알고리즘:  (향상 시킬 예정)
                //사용자 입력한 텍스트를 감정사전과 비교해서 찾은 단어들을 각자 리스트에 저장한다
                for (int i = 0; i < size; i++) {
                    if(!(i>=mAngerWords.size())) {
                        if (myText.contains(mAngerWords.get(i))) {
                            angryList.add(mAngerWords.get(i));
                        }
                    }
                    if (!(i>=mJoyWords.size())) {
                        if (myText.contains(mJoyWords.get(i))) {
                            joyList.add(mJoyWords.get(i));
                        }
                    }
                    if (!(i>=mSadWords.size())) {
                        if (myText.contains(mSadWords.get(i))) {
                            sadnessList.add(mSadWords.get(i));
                        }
                    }
                    if (!(i>=mFearWords.size())) {
                        if (myText.contains(mFearWords.get(i))) {
                            fearList.add(mFearWords.get(i));
                        }
                    }
                    if (!(i>=mSurpriseWords.size())) {
                        if (myText.contains(mSurpriseWords.get(i))) {
                            surpriseList.add(mSurpriseWords.get(i));
                        }
                    }
                    if (!(i>=mLoveWords.size())) {
                        if (myText.contains(mLoveWords.get(i))) {
                            loveList.add(mLoveWords.get(i));
                        }
                    }

                }



                System.out.println("Angry words count: "+angryList.size());
                System.out.println("Sad words count: "+sadnessList.size());
                System.out.println("Happy words count: "+joyList.size());
                System.out.println("Fear words count: "+fearList.size());
                System.out.println("Surprise words count: "+surpriseList.size());
                System.out.println("Love words count: "+loveList.size());


                //저장된 리스트의 사이즈를 비교해서 제일 큰 값을 찾으면 cnt에 아이디를 저장한다 (0-anger, 1-sadness, 2-joy, 3-fear, 4-surprise, 5-love):
                int [] arr = {angryList.size(), sadnessList.size(), joyList.size(), fearList.size(), surpriseList.size(), loveList.size()};
                int cnt=0;
                int max=0;
                String emotion = "";
                for(int i=0; i<arr.length;i++) {
                    if(arr[i]>max) {
                        max = arr[i];
                        cnt = i;
                    }
                }

                //카운트가 '0'이면 anger, '1'이면 sadness 등등..
                if(cnt==0) {emotion="anger";}
                else if(cnt==1) {emotion="sadness";}
                else if(cnt==2) {emotion="joy";}
                else if(cnt==3) {emotion="fear";}
                else if(cnt==4) {emotion="surprise";}
                else if(cnt==5) {emotion="love";}
                
                mFoundWordsTextView.setText("Your emotion is: " + emotion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
