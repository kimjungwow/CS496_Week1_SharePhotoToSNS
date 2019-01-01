package com.cs496.myapplication;

import android.app.Activity;
import android.os.Bundle;

public class SaveActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        onBackPressed();
    }

}
