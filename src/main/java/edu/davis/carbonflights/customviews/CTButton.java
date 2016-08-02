package edu.davis.carbonflights.customviews;

/**
 * Created by joey on 9/10/13.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import edu.davis.carbonflights.App;

public class CTButton extends Button
{
    public CTButton(Context context) {
        super( context );
        setFont();

    }

    public CTButton(Context context, AttributeSet attrs) {
        super( context, attrs );
        setFont();
    }

    public CTButton(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        setFont();
    }

    private void setFont() {
        setTypeface(App.league);
    }
}
