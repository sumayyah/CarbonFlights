package edu.davis.carbonflights.transport.flight;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;
import edu.davis.carbonflights.utility.Utils;
import edu.davis.carbonflights.customviews.CTTextViewItalic;

//@SuppressWarnings("unchecked")
public class FlightDetailAdapter extends BaseExpandableListAdapter
                           implements Comparable<View>
{
    private FlightDetailHash flightDetailHash;

    public LayoutInflater minflater;
    public Context context;

    public FlightDetailAdapter(Context c, FlightDetailHash d) {
        context = c;
        flightDetailHash = d;
        minflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return flightDetailHash.groups().get(groupPosition).children().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = minflater.inflate(R.layout.cell_flight_detail, null);

        /* get info from hash */
        FlightChild child = flightDetailHash.groups().get(groupPosition).children().get(childPosition);

        /* get views */
        CTTextViewItalic text_depart = (CTTextViewItalic) convertView.findViewById(R.id.text_depart);
        CTTextViewItalic text_arrive = (CTTextViewItalic) convertView.findViewById(R.id.text_arrive);

        /* set info */
        text_depart.setText(Utils.convertTime(child.flighthop().depart));
        text_arrive.setText(Utils.convertTime(child.flighthop().arrive));

        /* set color */
        if (childPosition == 0) {
            //set header to gray
            int gray = Color.rgb(180,180,181);
            text_depart.setTextColor(gray);
            text_arrive.setTextColor(gray);
        } else {
            int blue = Color.rgb(92,165,204);
            text_depart.setTextColor(blue);
            text_arrive.setTextColor(blue);
        }

        if (childPosition != 0 && child.highlight()) {
            int yellow = Color.rgb(249,245,142);
            text_depart.setTextColor(yellow);
            text_arrive.setTextColor(yellow);
            int gray = Color.rgb(191,191,191);
            convertView.setBackgroundColor(gray);

            ((CTTextViewItalic)convertView.findViewById(R.id.text_airline)).setText(flightDetailHash.airline(child.flighthop().airline));
            ((CTTextViewItalic)convertView.findViewById(R.id.text_flight)).setText("flight "+child.flighthop().flightNum);
            convertView.findViewById(R.id.details).setVisibility(View.VISIBLE);
        } else {
            int trans_white = Color.argb(102,255,255,255);
            convertView.setBackgroundColor(trans_white);
            convertView.findViewById(R.id.details).setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return flightDetailHash.groups().get(groupPosition).children().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return flightDetailHash.groups().size();
//        return hash2d.getL1().size();
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
                             View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.group_flight_detail, null);
        }
        ((CheckedTextView) convertView).setTypeface(App.league);
        ((CheckedTextView) convertView).setText(flightDetailHash.groups().get(groupPosition).key());
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return childPosition != 0;//cant select header
    }

    @Override
    public int compareTo(View view) {
        return 0;
    }
}
