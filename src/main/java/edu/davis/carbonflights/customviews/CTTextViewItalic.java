package edu.davis.carbonflights.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import edu.davis.carbonflights.App;

/**
 * Created by joey on 9/13/13.
 */
public class CTTextViewItalic extends TextView
{
    public CTTextViewItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    public CTTextViewItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CTTextViewItalic(Context context) {
        super(context);
        setFont();
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        //this is ugly but it avoids LeagueGothicItalic from getting cut off
        super.setText(text+" ", type);
    }

    public void setFont()
    {
        setTypeface(App.leagueItalic,1);
    }
}
