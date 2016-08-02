package edu.davis.carbonflights.utility;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import edu.davis.carbonflights.R;

/**
 * Created by joey on 6/27/13.
 */
public class Utils {
    public static void log(String message){
        Log.d("Utils", message);
    }
    public static void logAndShow(Context context, String message){
        Utils.log(message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static void initActionBar(Activity activity, String title, boolean showHome) {
        ActionBar actionBar = activity.getActionBar();
        // add the custom view to the action bar
        actionBar.setCustomView(R.layout.title);
        TextView titleTV = (TextView) actionBar.getCustomView().findViewById(R.id.title);

        titleTV.setTextColor(activity.getResources().getColor(R.color.CTBlue));
        titleTV.setText(title);
        if (showHome)
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM|ActionBar.DISPLAY_SHOW_HOME);
        else
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

                    actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);

    }

    public static String convertTime(String time)
    {
        try {
            String[] times = time.split(":");
            int hour = Integer.parseInt(times[0]);
            String minutes = times[1];//avoids emitting leading zeroes

            if (hour > 12) return (hour-12) + ":" + times[1] + "pm";
            else return hour + ":" + minutes + "am";
        } catch (Exception e) {
            return time;
        }
    }

    public static String time(Double duration){
        int hours = (int)(duration/60);
        int minutes = (int)(duration - (hours*60));
        return hours+"hrs "+minutes+"min";
    }
    public static int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
