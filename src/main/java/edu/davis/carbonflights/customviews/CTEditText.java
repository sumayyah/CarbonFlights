package edu.davis.carbonflights.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import edu.davis.carbonflights.App;

/**
 * Created by joey on 9/10/13.
 */
public class CTEditText extends EditText
{

    public CTEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    public CTEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CTEditText(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {
        setTypeface(App.leagueItalic);
    }

}