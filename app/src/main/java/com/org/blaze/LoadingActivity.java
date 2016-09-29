package com.org.blaze;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.faradaj.blurbehind.BlurBehind;

/**
 * Created by Big_Scal on 9/29/2016.
 */
public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        BlurBehind.getInstance()
                .withAlpha(50)
                .withFilterColor(Color.BLACK)
                .setBackground(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
    }
}
