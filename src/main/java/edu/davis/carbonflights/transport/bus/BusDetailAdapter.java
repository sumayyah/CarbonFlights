package edu.davis.carbonflights.transport.bus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;
import edu.davis.carbonflights.customviews.CTTextViewItalic;

/**
 * Created by Sumayyah on 9/25/13.
 */
public class BusDetailAdapter extends BaseExpandableListAdapter implements Comparable<View> {

    private BusDetailHash busDetailHash;

    public LayoutInflater minflater;
    public Context context;

    public BusDetailAdapter(Context c, BusDetailHash b) {
        context = c;
        busDetailHash = b;
        minflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return busDetailHash.groups().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return busDetailHash.groups().get(groupPosition).children().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return busDetailHash.groups().get(groupPosition).children().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.group_bus_detail, null);
        }
        ((CheckedTextView) convertView).setTypeface(App.league);
        ((CheckedTextView) convertView).setText(busDetailHash.groups().get(groupPosition).key());
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) convertView = minflater.inflate(R.layout.cell_bus_detail, null);

        /* get info from hash */
        BusChild busChild = busDetailHash.groups().get(groupPosition).children().get(childPosition);
        int detailIndex = childPosition+1;

        /* get views */
        CTTextViewItalic text_title = (CTTextViewItalic) convertView.findViewById(R.id.text_title);
        CTTextViewItalic text_duration = (CTTextViewItalic) convertView.findViewById(R.id.text_duration);
        CTTextViewItalic text_frequency = (CTTextViewItalic) convertView.findViewById(R.id.text_frequency);

        /* set info */
        text_title.setText("Trip Detail " + detailIndex);
        text_duration.setText("duration: " + busChild.transitLine().getDuration());
        text_frequency.setText("frequency (times per week): " + busChild.transitLine().frequency);

        /* set color */
        if (busChild.highlight()) {
            //TODO: add these colors to colors.xml
            int yellow = Color.rgb(249, 245, 142);
            int gray = Color.argb(153, 191, 191, 191);
            text_title.setTextColor(yellow);
            convertView.setBackgroundColor(gray);
            convertView.findViewById(R.id.details).setVisibility(View.VISIBLE);
        } else {
            int blue = Color.rgb(92,165,204);
            int trans_white = Color.argb(102,255,255,255);
            text_title.setTextColor(blue);
            convertView.setBackgroundColor(trans_white);
            convertView.findViewById(R.id.details).setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int compareTo(View view) {
        return 0;
    }
}
