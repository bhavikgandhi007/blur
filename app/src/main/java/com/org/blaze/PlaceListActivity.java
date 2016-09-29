package com.org.blaze;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faradaj.blurbehind.BlurBehind;
import com.faradaj.blurbehind.OnBlurCompleteListener;
import com.org.blaze.adapter.ShopAdapter;
import com.org.blaze.widget.DividerItemDecoration;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.adapters.ArrayWheelAdapter;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;

/**
 * Created by Big_Scal on 9/28/2016.
 */
public class PlaceListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private EditText search;
    private TextView txt_toolbar_tile, txt_filter_sort;
    private AbstractWheel spinner_time;
    Dialog dialogSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        txt_toolbar_tile = (TextView) findViewById(R.id.txt_toolbar_tile);
        setUpToolbar();
        txt_filter_sort = (TextView) findViewById(R.id.txt_filter_sort);
        mRecyclerView = (RecyclerView) findViewById(R.id.place_list_recycleview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(PlaceListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //add ItemDecoration
        mRecyclerView.addItemDecoration(new DividerItemDecoration(PlaceListActivity.this, 1));
        setData();
        spinner_time = (AbstractWheel) findViewById(R.id.spinner_time);
        ArrayWheelAdapter<String> ampmAdapter =
                new ArrayWheelAdapter<String>(this, new String[]{
                        "4AM", "5AM", "6AM", "7AM", "9AM", "10AM", "11AM", "12PM",
                        "1AM", "2AM", "3AM", "4AM", "5AM"
                });
        ampmAdapter.setItemResource(R.layout.wheel_time_centered);
        ampmAdapter.setItemTextResource(R.id.time_text);
        spinner_time.setViewAdapter(ampmAdapter);
        spinner_time.setCurrentItem(0);
        txt_filter_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ;
                showDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_place_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_wish) {
            return true;
        }
        return true;
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.place_toolbar);
        setSupportActionBar(toolbar);
        txt_toolbar_tile.setText(getIntent().getStringExtra("place_name"));
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData() {

        Resources res = getResources();
        String[] place_name = res.getStringArray(R.array.place_name_list);
        // int[] images = res.getIntArray(R.array.place_drwable);
        TypedArray images = res.obtainTypedArray(R.array.place_store_img);
        TypedArray place_icon = res.obtainTypedArray(R.array.place_icon);
        mAdapter = new ShopAdapter(PlaceListActivity.this, place_name, images);
        mRecyclerView.setAdapter(mAdapter);
    }

    /*
    * Show Filter Sort Dialog and its click Listner
     */
    private void showDialog() {

        dialogSort = new Dialog(new ContextThemeWrapper(PlaceListActivity.this, R.style.DialogSlideAnim));
        Window window = dialogSort.getWindow();
        dialogSort.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSort.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSort.getWindow().setGravity(Gravity.BOTTOM);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.98);
        dialogSort.getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSort.setContentView(R.layout.dialog_sort_by);
        final String[] selectFilter = new String[2];
        //Control Initialize
        final ImageView filter_age = (ImageView) dialogSort.findViewById(R.id.filter_age);
        final ImageView filter_busy = (ImageView) dialogSort.findViewById(R.id.filter_busy);
        final ImageView filter_not_busy = (ImageView) dialogSort.findViewById(R.id.filter_not_busy);
        final ImageView filter_males = (ImageView) dialogSort.findViewById(R.id.filter_males);
        final ImageView filter_females = (ImageView) dialogSort.findViewById(R.id.filter_females);
        final LinearLayout liner_age = (LinearLayout) dialogSort.findViewById(R.id.linear_age);
        final TextView btn_done = (TextView) dialogSort.findViewById(R.id.btn_done);
        liner_age.setVisibility(View.GONE); // by default spinner not visible

        // Click image
        filter_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_busy.setSelected(true);
                selectFilter[1] = selectFilter[0];
                selectFilter[0] = "busy";
                if (selectFilter[1] != null) {
                    clearOtherSelection(selectFilter[1]);
                }
            }
        });
        filter_not_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_not_busy.setSelected(true);
                selectFilter[1] = selectFilter[0];
                selectFilter[0] = "notbusy";
                if (selectFilter[1] != null) {
                    clearOtherSelection(selectFilter[1]);
                }
            }
        });
        filter_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_age.setSelected(true);
                selectFilter[1] = selectFilter[0];
                selectFilter[0] = "age";
                liner_age.setVisibility(View.VISIBLE);
                if (selectFilter[1] != null) {
                    clearOtherSelection(selectFilter[1]);
                }
            }
        });
        filter_males.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_males.setSelected(true);
                selectFilter[1] = selectFilter[0];
                selectFilter[0] = "males";
                if (selectFilter[1] != null) {
                    clearOtherSelection(selectFilter[1]);
                }
            }
        });
        filter_females.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_females.setSelected(true);
                selectFilter[1] = selectFilter[0];
                selectFilter[0] = "females";
                if (selectFilter[1] != null) {
                    clearOtherSelection(selectFilter[1]);
                }
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSort.dismiss();
                BlurBehind.getInstance().execute(PlaceListActivity.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(PlaceListActivity.this, LoadingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                    }
                });
            }
        });
        //Age Spinner Initialize
        final AbstractWheel spinner_age = (AbstractWheel) dialogSort.findViewById(R.id.dialog_spinner_age);
        final NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this, 22, 40);
        numericWheelAdapter.setItemResource(R.layout.wheel_text_centered);
        numericWheelAdapter.setItemTextResource(R.id.text);
        spinner_age.setViewAdapter(numericWheelAdapter);
        OnWheelChangedListener wheelListener2 = new OnWheelChangedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                LinearLayout mItemsLayout = spinner_age.getItemsLayout();
                int visibleitem = wheel.getVisibleItems();
                if (newValue > oldValue) {
                    for (int i = 0; i < mItemsLayout.getChildCount(); i++) {
                        FrameLayout select_frame = (FrameLayout) mItemsLayout.getChildAt(i);
                        TextView select_txt = (TextView) select_frame.getChildAt(0);
                        if (i == (visibleitem / 2) + 1) {
                            select_frame.setBackground(getResources().getDrawable(R.drawable.spinner_select_white));
                            select_txt.setTextColor(Color.BLACK);
                        } else {
                            select_frame.setBackgroundColor(Color.TRANSPARENT);
                            select_txt.setTextColor(Color.WHITE);
                        }
                    }
                }
            }
        };
        spinner_age.addChangingListener(wheelListener2);
        if (!dialogSort.isShowing())
            dialogSort.show();
    }

    private void clearOtherSelection(String unselect) {
        switch (unselect) {
            case "age":
                dialogSort.findViewById(R.id.filter_age).setSelected(false);
                break;
            case "males":
                dialogSort.findViewById(R.id.filter_males).setSelected(false);
                break;
            case "females":
                dialogSort.findViewById(R.id.filter_females).setSelected(false);
                break;
            case "busy":
                dialogSort.findViewById(R.id.filter_busy).setSelected(false);
                break;
            case "notbusy":
                dialogSort.findViewById(R.id.filter_not_busy).setSelected(false);
                break;
        }
    }

}
