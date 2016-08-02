package edu.davis.carbonflights.results;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;

@SuppressWarnings("unchecked")
public class ResultsAdapter extends BaseExpandableListAdapter
{
    private LayoutInflater minflater;
    private Activity activity;
    public Result Results;

    public ResultsAdapter(Activity act) {
        activity = act;
        minflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Results = App.Results;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Results.groups().get(groupPosition).children().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = minflater.inflate(R.layout.cell_results, null);

        /* get info from hash */
        ResultChild resultChild = Results.groups().get(groupPosition).children().get(childPosition);

        /* get views */
        //TODO: change to CTTextView here and in XML
        TextView keyTV = (TextView) convertView.findViewById(R.id.textView1);
        TextView carbonTV = (TextView) convertView.findViewById(R.id.textView3);
        TextView kgTV = (TextView) convertView.findViewById(R.id.textView2);
        TextView timeTV = (TextView) convertView.findViewById(R.id.time);

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        /* set fonts */
        keyTV.setTypeface(App.league);
        carbonTV.setTypeface(App.league);
        timeTV.setTypeface(App.league);
        ((TextView)convertView.findViewById(R.id.textView2)).setTypeface(App.league);

        if(App.Network.done()) {

            Results.clearCarbonArray();
            Results.setMin();
            Results.setMax();

            double min = Results.getMin();
            double max = Results.getMax();

            Double diff = max-min;
            Double upperThird = 0.66*diff+min;
            Double lowerThird = 0.33*diff+min;

            if(resultChild.CO2() ==0.0 || (getGroupCount() == 1 && getChildrenCount(0) == 1))
            {
                /* if carbon is not yet calculated or there is only a single result*/
                carbonTV.setTextColor(Color.parseColor("#777777"));
            }
            else if (resultChild.CO2() > upperThird)//upper third red
                carbonTV.setTextColor(Color.parseColor("#FF989A") );
            else if (resultChild.CO2() > lowerThird)//middle third yellow
                carbonTV.setTextColor(Color.parseColor("#F1E4A1") );
            else if (resultChild.CO2() < lowerThird)//lower third green
                carbonTV.setTextColor(Color.parseColor("#9CE994") );
            else
                carbonTV.setTextColor(Color.parseColor("#aaaaaa") );
        }

        /* set text info */
        keyTV.setText(resultChild.key());

        progressBar.setVisibility(View.GONE);
        carbonTV.setVisibility(View.VISIBLE);
        kgTV.setVisibility(View.VISIBLE);
        carbonTV.setText(((Double)resultChild.CO2()).intValue()+"");

        timeTV.setText(resultChild.duration()+"");

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Results.groups().get(groupPosition).children().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return Results.groups().size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.group_results, null);
        }
        ((CheckedTextView) convertView).setTypeface(App.league);
        ((CheckedTextView) convertView).setText(Results.groups().get(groupPosition).key());
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
