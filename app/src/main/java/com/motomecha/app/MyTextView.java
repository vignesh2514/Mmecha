package com.motomecha.app;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by vignesh2514 on 4/18/2017.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setType(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public MyTextView(Context context) {
        super(context);
        setType(context);
    }

    private void setType(Context context){
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/rama.ttf"));


    }
}