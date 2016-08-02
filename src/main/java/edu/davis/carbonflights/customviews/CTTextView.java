package edu.davis.carbonflights.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import edu.davis.carbonflights.App;

/**
 * Created by joey on 8/5/13.
 */
public class CTTextView extends TextView {

    public CTTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    public CTTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CTTextView(Context context) {
        super(context);
        setFont();
    }

    public void setFont()
    {
        setTypeface(App.league,1);
    }
}
