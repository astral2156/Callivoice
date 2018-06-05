package com.example.callivoice;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CalliVoice extends Application{

    public String UsersEmotion ="";

    public ArrayList<String> UsersText = new ArrayList<>();

    public ArrayList<String> UsersAnger = new ArrayList<>();

    public ArrayList<String> UsersLove = new ArrayList<>();;

    public ArrayList<String> UsersSurprise = new ArrayList<>();

    public ArrayList<String> UsersSad = new ArrayList<>();

    public ArrayList<String> UsersJoy = new ArrayList<>();

    public ArrayList<String> UsersFear = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
