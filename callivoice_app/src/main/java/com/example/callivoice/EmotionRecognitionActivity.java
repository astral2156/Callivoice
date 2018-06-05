package com.example.callivoice;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class EmotionRecognitionActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private TextView mUserTextView;
    private Button mNextBtn;
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
    private ArrayList<String> ALLlist = new ArrayList<>();
    private int size;
    private TextView mFoundWordsTextView;
    private String myText;
    private String emotion = "";

    //매핑을 시키기 위해 각 감정에 아이디를 final int로 준다:
    private final int nothing = 0;
    private final int love = 1;
    private final int anger = 2;
    private final int sadness = 3;
    private final int joy = 4;
    private final int fear = 5;
    private final int surprise = 6;

    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_recognition);

        //identifying Text and Image Views
        mUserTextView = (TextView) findViewById(R.id.userTextView);
        mFoundWordsTextView = (TextView) findViewById(R.id.wordList);
        mNextBtn = (Button) findViewById(R.id.goToImageEditBtn);
        //Creating firebase instance and reference. 파이어베이스와 연결 시킨다:
        mFirebaseDatabase = FirebaseDatabase.getInstance(); // 어플리케이션의 데이터베이스를 가져온다
        mDatabaseReference = mFirebaseDatabase.getReference(); //어플리케이션 데이터베이스 링크를 mDatabaseReference에 연결한다

        //로그인중 유저아이디와 사용자의 텍스트를 가져온다:
        //******************************************************************************************
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        final DatabaseReference currentUserDB = mDatabaseReference.child("Users").child(userID).child("userText");


        query = currentUserDB.orderByKey().limitToLast(1);      //Filter for lastly added userText
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

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmotionRecognitionActivity.this, EditResultActivity.class);
                i.putExtra("emotion", emotion);
                i.putExtra("text", myText);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final CalliVoice userdata = (CalliVoice)getApplication();

        userdata.UsersText.clear();
    }

    public void readDB () {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("emotions"); //데이터베이스에 있는 parent를 가리킨다 (예시: Callivoice(root)/emotions(parent))
        final CalliVoice userdata = (CalliVoice)getApplication();

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
                String tempText = myText;
                //감정인식 알고리즘:  (향상 시킬 예정)
                //사용자 입력한 텍스트를 감정사전과 비교해서 찾은 단어들을 각자 리스트에 저장한다
                int myIndex;

                String TextArr[] = myText.split(" ");
                for(int i=0;i<TextArr.length;i++)
                {
                    userdata.UsersText.add(TextArr[i]);
                }

                ArrayList<String> TempArr = new ArrayList<>();
                for(int k=0;k<TextArr.length;k++)
                {
                    boolean Isnothing = true;

                    for (int i = 0; i < size; i++) {
                        if (i < 0) i = 0;

                        if(!(i>=mAngerWords.size())) {
                            if (TextArr[k].contains(mAngerWords.get(i))) {

                                myIndex = TextArr[k].indexOf(mAngerWords.get(i));
                                TextArr[k] = TextArr[k].substring(0,myIndex) + TextArr[k].substring(myIndex + mAngerWords.get(i).length());

                                angryList.add(mAngerWords.get(i));
                                i--;
                            }

                            Isnothing = false;
                        }
                        if (!(i>=mJoyWords.size())) {
                            if (TextArr[k].contains(mJoyWords.get(i))) {

                                myIndex = TextArr[k].indexOf(mJoyWords.get(i));
                                TextArr[k] = TextArr[k].substring(0,myIndex) + TextArr[k].substring(myIndex + mJoyWords.get(i).length());

                                joyList.add(mJoyWords.get(i));
                                i--;
                            }

                            Isnothing = false;
                        }
                        if (!(i>=mSadWords.size())) {
                            if (TextArr[k].contains(mSadWords.get(i))) {

                                myIndex = TextArr[k].indexOf(mSadWords.get(i));
                                TextArr[k] = TextArr[k].substring(0,myIndex) + TextArr[k].substring(myIndex + mSadWords.get(i).length());

                                sadnessList.add(mSadWords.get(i));
                                i--;
                            }
                        }
                        if (!(i>=mFearWords.size())) {
                            if (TextArr[k].contains(mFearWords.get(i))) {

                                myIndex = TextArr[k].indexOf(mFearWords.get(i));
                                TextArr[k] = TextArr[k].substring(0,myIndex) + TextArr[k].substring(myIndex + mFearWords.get(i).length());

                                fearList.add(mFearWords.get(i));
                                i--;
                            }

                            Isnothing = false;
                        }
                        if (!(i>=mSurpriseWords.size())) {
                            if (TextArr[k].contains(mSurpriseWords.get(i))) {

                                myIndex = TextArr[k].indexOf(mSurpriseWords.get(i));
                                TextArr[k] = TextArr[k].substring(0,myIndex) + TextArr[k].substring(myIndex + mSurpriseWords.get(i).length());


                                surpriseList.add(mSurpriseWords.get(i));
                                i--;
                            }
                            Isnothing = false;
                        }
                        if (!(i>=mLoveWords.size())) {
                            if (TextArr[k].contains(mLoveWords.get(i))) {

                                myIndex = TextArr[k].indexOf(mLoveWords.get(i));
                                //I love not you

                            /*

                            Check for negative

                             */
                                TextArr[k] = TextArr[k].substring(0,myIndex) + TextArr[k].substring(myIndex + mLoveWords.get(i).length());

                                loveList.add(mLoveWords.get(i));
                                i--;
                            }

                            Isnothing = false;
                        }

                        //System.out.println("Current MyText (hey): " + myText);  //Debug

                    }

                    if(Isnothing == true)
                    {
                        TempArr.add(TextArr[k]);
                    }

                }

                PutInArr(userdata.UsersAnger, angryList);
                PutInArr(userdata.UsersFear, fearList);
                PutInArr(userdata.UsersJoy, joyList);
                PutInArr(userdata.UsersSad, sadnessList);
                PutInArr(userdata.UsersLove, loveList);
                PutInArr(userdata.UsersSurprise, surpriseList);

                //System.out.println("After count (hey): Love: " + loveList.size() + ", Anger: " + angryList.size() + "\n"); //Debug

                myText = tempText;

                //저장된 리스트의 사이즈를 비교해서 제일 큰 값을 찾으면 myEmotion에 아이디를 저장한다 (0-nothing, 1-love, 2-anger, 3-sadness, 4-joy, 5-fear, 6-surprise):
                int [] arr = {0, loveList.size(), angryList.size(), sadnessList.size(), joyList.size(), fearList.size(), surpriseList.size() };

                //Debug:
                /*
                System.out.println("Counters array (hey): ");
                for (int i = 0; i < arr.length; i++) {
                    System.out.println(" " + arr[i]);
                }
                */

                int myEmotion=nothing;
                int max=0;
                String emotion = "";
                for(int i=0; i<arr.length;i++) {
                    if(arr[i]!=0)
                    {
                        if(arr[i]>max) {
                            max = arr[i];
                            myEmotion = i;
                        }

                        else if(arr[i] == max)
                        {
                            String CurrentWord = ""; //현재 가장 많은 단어가 나온 emotion
                            String newWord = ""; //새로 등장한 단어

                            if(myEmotion == 2){
                                CurrentWord = angryList.get(angryList.size()-1);
                            }
                            else  if(myEmotion == 3){
                                CurrentWord = sadnessList.get(sadnessList.size()-1);
                            }
                            else  if(myEmotion == 4){
                                CurrentWord = joyList.get(joyList.size()-1);
                            }
                            else  if(myEmotion == 5){
                                CurrentWord = fearList.get(fearList.size()-1);
                            }
                            else  if(myEmotion == 6){
                                CurrentWord = surpriseList.get(surpriseList.size()-1);
                            }
                            else  if(myEmotion == 1){
                                CurrentWord = loveList.get(loveList.size()-1);
                            }

                            if(i == 2){
                                newWord = angryList.get(angryList.size()-1);
                            }
                            else  if(i == 3){
                                newWord = sadnessList.get(sadnessList.size()-1);
                            }
                            else  if(i == 4){
                                newWord = joyList.get(joyList.size()-1);
                            }
                            else  if(i == 5){
                                newWord = fearList.get(fearList.size()-1);
                            }
                            else  if(i == 6){
                                newWord = surpriseList.get(surpriseList.size()-1);
                            }
                            else  if(i == 1){
                                newWord = loveList.get(loveList.size()-1);
                            }
                            //new, Current 값 저장

                            int Currentindex = myText.lastIndexOf(CurrentWord);

                            int newIndex = myText.lastIndexOf(newWord);

                            if (newIndex>Currentindex){
                                myEmotion = i;
                            }

                        }

                    }

                }

                CarouselPicker carouselPicker = (CarouselPicker) findViewById(R.id.carousel);
                List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
                imageItems.add(new CarouselPicker.DrawableItem(R.drawable.image_recordshowemotion_small_love));  //0
                imageItems.add(new CarouselPicker.DrawableItem(R.drawable.image_recordshowemotion_small_anger)); //1
                imageItems.add(new CarouselPicker.DrawableItem(R.drawable.image_recordshowemotion_small_fear)); //2
                imageItems.add(new CarouselPicker.DrawableItem(R.drawable.image_recordshowemotion_small_joy)); //3
                imageItems.add(new CarouselPicker.DrawableItem(R.drawable.image_recordshowemotion_small_sadness)); //4
                imageItems.add(new CarouselPicker.DrawableItem(R.drawable.image_recordshowemotion_small_surprise)); //5

                CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(EmotionRecognitionActivity.this, imageItems, 0);
                carouselPicker.setAdapter(imageAdapter);

                if(myEmotion==nothing) {
                    emotion = "감정을 인식하지 못했습니다";
                    carouselPicker.setCurrentItem(0);
                }
                else if(myEmotion==anger) {
                    emotion="anger";
                    carouselPicker.setCurrentItem(1);
                }
                else if(myEmotion==sadness) {
                    emotion="sadness";
                    carouselPicker.setCurrentItem(4);
                }
                else if(myEmotion==joy) {
                    emotion="joy";
                    carouselPicker.setCurrentItem(3);
                }
                else if(myEmotion==fear) {
                    emotion="fear";
                    carouselPicker.setCurrentItem(2);
                }
                else if(myEmotion==surprise) {
                    emotion="surprise";
                    carouselPicker.setCurrentItem(5);
                }
                else if(myEmotion==love) {
                    emotion="love";
                    carouselPicker.setCurrentItem(0);
                }
                mFoundWordsTextView.setText("Your emotion is: " + emotion);
                userdata.UsersEmotion=emotion;
                System.out.println("your emotion" + userdata.UsersEmotion);

            }

            //감정 수정

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void PutInArr(ArrayList<String> userdata, ArrayList<String> Arr)
    {
        for(int i=0;i<Arr.size();i++)
        {
            userdata.add(Arr.get(i));
        }
    }
}


