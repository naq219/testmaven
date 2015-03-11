package com.telpoo.frame.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by naq on 27/02/2015.
 */
public class TvFont extends TextView {

    public TvFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TvFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TvFont(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "appfont.ttf");
        setTypeface(tf);
    }

}