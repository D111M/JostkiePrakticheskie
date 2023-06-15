package com.example.widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    FlashClass flashClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashClass = new FlashClass(this);
    }

    public void onClickFlashlight(View view) {
        if (flashClass.isFlash()) flashClass.LightOff();
        else flashClass.LightOn();

    }

}