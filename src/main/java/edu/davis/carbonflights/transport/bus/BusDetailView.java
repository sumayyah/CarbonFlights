package edu.davis.carbonflights.transport.bus;

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
 * Created by Sumayyah on 9/25/13.
 */
public class BusDetailView extends LinearLayout implements ExpandableListView.OnChildClickListener{

    private BusDetailAdapter busDetailAdapter;
    private ExpandableListView busListView;

    private double gas_multiplier = 0.113;
    private double miles_driven_multiplier = 2.4;
    private double vehicle_multiplier = 0.0002;

    public BusDetailView(Context context, ArrayList<Bus> buses)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup busDetailLayout = (ViewGroup)inflater.inflate(R.layout.layout_bus_detail, null);

        busDetailAdapter = new BusDetailAdapter(getContext(), new BusDetailHash(buses));

        ((CTTextViewItalic)busDetailLayout.findViewById(R.id.text_co2)).setText(buses.get(0).CO2.intValue()+"");
        ((CTTextViewItalic)busDetailLayout.findViewById(R.id.equivalent)).setText((buses.get(0).CO2.intValue()*gas_multiplier)+"");
//        ((CTTextViewItalic)busDetailLayout.findViewById(R.id.text_equivalence)).setText(" \u2245 "+buses.get(0).equivalent().substring(0,4)+" cars driven per day");


        busListView = (ExpandableListView)busDetailLayout.findViewById(R.id.listview_bus);

        busListView.setAdapter(busDetailAdapter);
        busListView.setOnChildClickListener(this);

        for(int i=0; i < busDetailAdapter.getGroupCount(); i++)
            busListView.expandGroup(i);

        addView(busDetailLayout);
    }
    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        BusChild child_clicked = (BusChild) busDetailAdapter.getChild(groupPosition, childPosition);

        for (int i=0; i < busDetailAdapter.getGroupCount(); i++) {
            for (int j=0; j < busDetailAdapter.getChildrenCount(i); j++) {
                BusChild tc = ((BusChild)busDetailAdapter.getChild(i,j));
                if (tc != child_clicked)
                    tc.highlight(false);
            }
        }
        child_clicked.highlight( !child_clicked.highlight() ); //toggle

        busDetailAdapter.notifyDataSetChanged();
        return false;
    }
}
