package edu.davis.carbonflights.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;
import edu.davis.carbonflights.network.Network;
import edu.davis.carbonflights.results.ResultChild;
import edu.davis.carbonflights.results.ResultGroup;
import edu.davis.carbonflights.results.ResultsAdapter;
import edu.davis.carbonflights.transport.Transportation;
import edu.davis.carbonflights.utility.Utils;

/**
 *
 * Results Activity:
 *
 * Shows a list view of all available travel routes for given source and destination.
 * Each route appears in its own row, with duration of travel and carbon emissions in kilos.
 */

public class ResultsActivity extends Activity implements ExpandableListView.OnChildClickListener, Network.Listener
{
    private ResultsAdapter resultsAdapter;
    private ExpandableListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Utils.initActionBar(this, "Results", false);
        ((ImageView)findViewById(R.id.bg_image)).setImageResource(App.bg_id);

        resultsAdapter = new ResultsAdapter(this);

        resultsListView = (ExpandableListView)findViewById(R.id.listview_results);
        resultsListView.setAdapter(resultsAdapter);
        resultsListView.setOnChildClickListener(this);

        App.Network.setListener(this);

        for(int i=0; i < resultsAdapter.getGroupCount(); i++)
            resultsListView.expandGroup(i);

        if (App.Results.groups().size() == 0){
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }
        else
            findViewById(R.id.progressBar).setVisibility(View.GONE);

    }


    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l)
    {
        ResultChild resultChild = (ResultChild) resultsAdapter.getChild(groupPosition, childPosition);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putParcelableArrayListExtra(Transportation.Type + "", resultChild.transportationList);
        startActivity(intent);

        return false;
    }

    boolean expand = true;
    @Override
    public void onDataReceived()
    {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        resultsAdapter.notifyDataSetChanged();
        if (expand) {
            for(int i=0; i < resultsAdapter.getGroupCount(); i++)
                resultsListView.expandGroup(i);
            expand = false;
        }
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
            case R.id.carbonSort:
                sortResults("CO2");
                return true;
            case R.id.durationSort:
                sortResults("duration");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sortResults(String type){
        for(ResultGroup g: resultsAdapter.Results.groups()){
            g.sort(type);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    resultsAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onNoResults(){
        findViewById(R.id.message).setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed()
    {
        App.Network.clear();
        App.Results.clear();

        super.onBackPressed();
    }

}//End main
