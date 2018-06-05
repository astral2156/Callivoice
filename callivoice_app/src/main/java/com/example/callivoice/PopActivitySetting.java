package com.example.callivoice;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


public class PopActivitySetting extends Activity{

    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settingpop);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        DisplayMetrics dm = new DisplayMetrics();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        wlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        wlp.y = 100;
        window.setAttributes(wlp);
        window.setLayout((int)(width*.7),(int)(height*.757));
        //getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }
}
