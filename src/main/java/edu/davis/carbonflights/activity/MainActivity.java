package edu.davis.carbonflights.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;
import edu.davis.carbonflights.customviews.CTTextView;
import edu.davis.carbonflights.customviews.SlideoutMenu;
import edu.davis.carbonflights.utility.Utils;

/**
 * Created by Sumayyah on 7/4/13.
 *
 * Main Activity:
 *
 * Presents the main page of the app. Prompts user for travel source and destination
 * and takes user input.
 */

public class MainActivity extends Activity implements View.OnClickListener
{
    private EditText edit_from;
    private EditText edit_to;

    private TextView scrollText;
    private SlideoutMenu slideoutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Check if user is connected to the internet*/
               ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
               NetworkInfo net = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

               if(!net.isConnected()){
//                  ((CTTextView)findViewById(R.id.message)).setVisibility(View.VISIBLE);
                   showAlert("No Wifi connection available. Please enable wifi.");
               }

        Utils.initActionBar(this, "CarbonTrips", true);
        App.bg_id = App.bg_ids[Utils.randInt(0,App.bg_ids.length-1)];
        ((ImageView)findViewById(R.id.bg_image)).setImageResource(App.bg_id);

        //get width and height of window
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        slideoutMenu = (SlideoutMenu) findViewById(R.id.slideout_menu);
        slideoutMenu.height(metrics.heightPixels);
        slideoutMenu.width(metrics.widthPixels);
        scrollText = (TextView)(findViewById(R.id.scrollViewText));
        scrollText.setText("Our carbon calculations are based on the combined efforts of two API's:\n" +
                "\n" +
                "1) Rome2Rio\n" +
                "\n" +
                "\tThis is the travel search - it takes in your source and destination and gives back:\n" +
                "\n" +
                "\t\ta) All different kinds of transport in between the two locations\n" +
                "\t\tb) Distance between the two\n" +
                "\t\tc) All the different routes possible\n" +
                "\t\td) Duration of each route\n" +
                "\t\te) Many other travel parameters\n" +
                "\t\n" +
                "This data is returned in JSON format, which is parsed by the app. \n" +
                "\n" +
                "2) CM1\n" +
                "\n" +
                "\tThe app takes parameters from Rome2Rio's response and passes them to Brighter Planet's CM1 engine - the more parameters, the more accurate the engine is. It returns a value in kilograms of Carbon Dioxide emitted - actually, this value includes carbon dioxide as well as other harmful chemical vapors. ");


        edit_from = (EditText)findViewById(R.id.fromET);
        edit_to = (EditText)findViewById(R.id.toET);
        findViewById(R.id.button_search).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                System.exit(0);
                return true;
            case android.R.id.home:
                slideoutMenu.toggle();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (edit_from.getText() == null || edit_from.getText().toString().equals("")) {
            showAlert("Please enter 'from'");
            return;
        } else if (edit_to.getText() == null || edit_to.getText().toString().equals("")) {
            showAlert("Please enter 'to'");
            return;
        }
        App.Results.source(edit_from.getText().toString());
        App.Results.destination(edit_to.getText().toString());

        Intent intent = new Intent(this, EstimatesActivity.class);
        startActivity(intent);
    }


    private void showAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setNegativeButton("OK", null);
        builder.create().show();
    }

} //End main