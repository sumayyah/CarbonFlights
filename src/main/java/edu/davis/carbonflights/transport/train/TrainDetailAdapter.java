package edu.davis.carbonflights.transport.train;

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
 * Created by joey on 9/24/13.
 */
public class TrainDetailAdapter extends BaseExpandableListAdapter
        implements Comparable<View>

{
    private TrainDetailHash trainDetailHash;

    public LayoutInflater minflater;
    public Context context;

    public TrainDetailAdapter(Context c, TrainDetailHash d) {
        context = c;
        trainDetailHash = d;
        minflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return trainDetailHash.groups().get(groupPosition).children().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = minflater.inflate(R.layout.cell_train_detail, null);

        /* get info from hash */
        TrainChild trainChild = trainDetailHash.groups().get(groupPosition).children().get(childPosition);

        /* get views */
        CTTextViewItalic text_title = (CTTextViewItalic) convertView.findViewById(R.id.text_title);
        CTTextViewItalic text_duration = (CTTextViewItalic) convertView.findViewById(R.id.text_duration);
        CTTextViewItalic text_frequncy = (CTTextViewItalic) convertView.findViewById(R.id.text_frequency);

        /* set info */
        text_title.setText(trainChild.transitLine().name);
        text_duration.setText("duration: " + trainChild.transitLine().getDuration());
        text_frequncy.setText("frequency (times perweek): " + trainChild.transitLine().frequency);

        /* set color */
        if (trainChild.highlight()) {
            //TODO: add these colors to colors.xml
            int yellow = Color.rgb(249,245,142);
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
    public int getChildrenCount(int groupPosition)
    {
        return trainDetailHash.groups().get(groupPosition).children().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return trainDetailHash.groups().size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.group_train_detail, null);
        }
        ((CheckedTextView) convertView).setTypeface(App.league);
        ((CheckedTextView) convertView).setText(trainDetailHash.groups().get(groupPosition).key());
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

    @Override
    public int compareTo(View view) {
        return 0;
    }
}
