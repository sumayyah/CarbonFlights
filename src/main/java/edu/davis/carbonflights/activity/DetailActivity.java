package edu.davis.carbonflights.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.davis.carbonflights.R;
import edu.davis.carbonflights.transport.Transportation;
import edu.davis.carbonflights.utility.Utils;

/**
*
* Detail Activity
 *
 * Displays detailed information on chosen route: each travel segment, timings,
 * carbon emission metrics, and transport numbers (if available).
 *
*
* */

public class DetailActivity extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Utils.initActionBar(this, "DETAILS", false);

        ArrayList<Transportation> transportations = getIntent().getParcelableArrayListExtra(Transportation.Type+"");

        ViewGroup transportLayout = Transportation.getLayout(this, transportations);
        ((LinearLayout)findViewById(R.id.detailView)).addView(transportLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_sort_time:
//                //TODO: sort by times here
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }
}//End DetailActivity
