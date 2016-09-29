package com.org.blaze;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.org.blaze.adapter.PlaceAdapter;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search = (EditText) findViewById(R.id.search_edit);
        mRecyclerView = (RecyclerView) findViewById(R.id.place_recycleview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setData();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(" ");
        builder.setSpan(new ImageSpan(getApplicationContext(), R.drawable.search_icon),
                builder.length() - 1, builder.length(), 0);
        builder.append(" Search");
        search.setHint(builder);
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                search.setGravity(Gravity.LEFT);
                return false;
            }
        });

    }

    private void setData() {

        Resources res = getResources();
        String[] place_name = res.getStringArray(R.array.place_name);
        // int[] images = res.getIntArray(R.array.place_drwable);
        TypedArray images = res.obtainTypedArray(R.array.place_drwable);
        TypedArray place_icon = res.obtainTypedArray(R.array.place_icon);
        mAdapter = new PlaceAdapter(HomeActivity.this, place_name, images, place_icon);
        mRecyclerView.setAdapter(mAdapter);
    }

}
