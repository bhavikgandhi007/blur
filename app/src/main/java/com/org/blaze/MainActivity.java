package com.org.blaze;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.OnWheelClickedListener;
import antistatic.spinnerwheel.OnWheelScrollListener;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;

public class MainActivity extends AppCompatActivity {

    LinearLayout linear_male, linear_female;
    int gender_select;
    TextView btn_done;
    private boolean timeScrolled = false;
    private static String TAG = MainActivity.class.getName();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear_male = (LinearLayout) findViewById(R.id.linear_male);
        linear_female = (LinearLayout) findViewById(R.id.linear_female);
        btn_done = (TextView) findViewById(R.id.btn_done);
        gender_select = 1;
        linear_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGender(0);
            }
        });
        linear_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGender(1);
            }
        });
        final AbstractWheel ampm = (AbstractWheel) findViewById(R.id.spinner_age);
        final NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this, 20, 40);
        numericWheelAdapter.setItemResource(R.layout.wheel_text_centered);
        numericWheelAdapter.setItemTextResource(R.id.text);
        ampm.setViewAdapter(numericWheelAdapter);

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

            public void onScrollingStarted(AbstractWheel wheel) {
            }

            public void onScrollingFinished(AbstractWheel wheel) {
                LinearLayout mItemsLayout = ampm.getItemsLayout();
                Log.i(TAG, "onScrollingFinished: " + mItemsLayout.getChildCount());
                for (int i = 0; i < mItemsLayout.getChildCount(); i++) {
                    if (i == (mItemsLayout.getChildCount() / 2) - 1) {
                        mItemsLayout.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.button_select));
                    } else {
                        mItemsLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }

            }
        };
        OnWheelChangedListener wheelListener2 = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    LinearLayout mItemsLayout = ampm.getItemsLayout();
                    int visibleitem = wheel.getVisibleItems();
                    selectSpin(visibleitem,mItemsLayout);
//                    Log.i(TAG, "onChanged: visible item" + visibleitem + "First item==>" + wheel.getFirstItem() + " \n" +
//                            "childItem" + mItemsLayout.getChildCount() + "old value" + oldValue + "new value" + newValue + "wheel Current Item" + wheel.getCurrentItem());
//                    if (newValue > oldValue) {
//                        for (int i = 1; i < mItemsLayout.getChildCount(); i++) {
//                            if (i == (visibleitem / 2) ) {
//                                mItemsLayout.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.button_select));
//                            } else {
//                                mItemsLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                            }
//                        }
//                    } else {
//                        for (int i = 1; i < mItemsLayout.getChildCount(); i++) {
//                            if (i == (mItemsLayout.getChildCount() / 2) - 1) {
//                                mItemsLayout.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.button_select));
//                            } else {
//                                mItemsLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                            }
//                        }
//                    }
                }
            }
        };
        ampm.addChangingListener(wheelListener2);
        ampm.addClickingListener(new OnWheelClickedListener() {
            @Override
            public void onItemClicked(AbstractWheel wheel, int itemIndex) {
                ampm.setCurrentItem(itemIndex);
                //wheel.setTextBackground(2);
                //ViewGroup viewGroup = new LinearLayout(MainActivity.this);
                // wheel.getViewAdapter().getItem(2, wheel.getRootView(),viewGroup).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    /*
    * Change Background according to user select
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void selectGender(int gender) {
        if (gender == 0) {
            linear_male.setBackground(getResources().getDrawable(R.drawable.button_select));
            linear_female.setBackground(getResources().getDrawable(R.drawable.button_unselect));
            gender_select = 0;
        } else {
            linear_male.setBackground(getResources().getDrawable(R.drawable.button_unselect));
            linear_female.setBackground(getResources().getDrawable(R.drawable.button_select));
            gender_select = 1;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void selectSpin(int visibleitem,LinearLayout mItemsLayout){
            for (int i = 1; i < mItemsLayout.getChildCount(); i++) {
                if (i == (visibleitem / 2) ) {
                    mItemsLayout.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.button_select));
                } else {
                    mItemsLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }

    }


}
