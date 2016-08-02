package edu.davis.carbonflights.transport.train;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.davis.carbonflights.R;
import edu.davis.carbonflights.customviews.CTTextViewItalic;

/**
 * Created by joey on 9/23/13.
 */
public class TrainDetailView extends LinearLayout implements ExpandableListView.OnChildClickListener {

    private TrainDetailAdapter trainDetailAdapter;
    private ExpandableListView trainListView;

    private double gas_multiplier = 0.113;
    private double miles_driven_multiplier = 2.4;
    private double vehicle_multiplier = 0.0002;

    public TrainDetailView(Context context, ArrayList<Train> trains)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup trainDetailLayout = (ViewGroup)inflater.inflate(R.layout.layout_train_detail, null);

        trainDetailAdapter = new TrainDetailAdapter(getContext(), new TrainDetailHash(trains));

        ((CTTextViewItalic)trainDetailLayout.findViewById(R.id.text_co2)).setText(trains.get(0).CO2.intValue()+"");
        ((CTTextViewItalic)trainDetailLayout.findViewById(R.id.equivalent)).setText((trains.get(0).CO2.intValue()*gas_multiplier)+"");

        trainListView = (ExpandableListView)trainDetailLayout.findViewById(R.id.listview_train);

        trainListView.setAdapter(trainDetailAdapter);
        trainListView.setOnChildClickListener(this);

        for(int i=0; i < trainDetailAdapter.getGroupCount(); i++)
            trainListView.expandGroup(i);

        addView(trainDetailLayout);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l)
    {
        TrainChild child_clicked = (TrainChild) trainDetailAdapter.getChild(groupPosition, childPosition);


        for (int i=0; i < trainDetailAdapter.getGroupCount(); i++) {
            for (int j=0; j < trainDetailAdapter.getChildrenCount(i); j++) {
                TrainChild tc = ((TrainChild)trainDetailAdapter.getChild(i,j));
                if (tc != child_clicked)
                    tc.highlight(false);
            }
        }//all children are now unhighglighted

        child_clicked.highlight( !child_clicked.highlight() ); //toggle

        trainDetailAdapter.notifyDataSetChanged();
        return false;
    }
}
