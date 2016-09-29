package com.org.blaze.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.org.blaze.R;

public class CustomFontEditText extends EditText {

    private String fontType;

    public CustomFontEditText(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.CustomFontEditText);
            fontType = a.getString(R.styleable.CustomFontEditText_fontTypeEditText);
            if (fontType != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontType);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
