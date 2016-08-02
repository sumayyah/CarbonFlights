package edu.davis.carbonflights;

/**
 * Created by joey on 8/29/13.
 */

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import edu.davis.carbonflights.results.Result;

public class App extends Application
{
    public static Context Current;
    public static Typeface raleway;
    public static Typeface josefin;
    public static Typeface league;
    public static Typeface leagueItalic;
    public static edu.davis.carbonflights.network.Network Network;
    public static Result Results;
    public static int bg_ids[];//background
    public static int bg_id;
    @Override
    public void onCreate()
    {
        super.onCreate();
        Current = this;
        Network = edu.davis.carbonflights.network.Network.getInstance();
        Results = Result.getInstance();

        raleway = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Thin.ttf");
        josefin = Typeface.createFromAsset(getAssets(), "fonts/JosefinSans-Light.ttf");
        league = Typeface.createFromAsset(getAssets(), "fonts/LeagueGothic-Regulary.otf");
        leagueItalic = Typeface.createFromAsset(getAssets(), "fonts/LeagueGothic-Italic.otf");

        bg_ids = new int[11];
        bg_ids[0] = R.drawable.bg_bird;
        bg_ids[1] = R.drawable.bg_maldives;
        bg_ids[2] = R.drawable.bg_palms;
        bg_ids[3] = R.drawable.bg_tracks;
        bg_ids[4] = R.drawable.bg_wheat;
        bg_ids[5] = R.drawable.bg_boat;
        bg_ids[6] = R.drawable.bg_capital;
        bg_ids[7] = R.drawable.bg_farm;
        bg_ids[8] = R.drawable.bg_greenwheat;
        bg_ids[9] = R.drawable.bg_tree;
        bg_ids[10] = R.drawable.bg_water;
    }

    @Override
    public void onLowMemory()
    {
        //unused
    }


}
