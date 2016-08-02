package edu.davis.carbonflights.transport.flight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;
import edu.davis.carbonflights.customviews.CTTextViewItalic;
import edu.davis.carbonflights.transport.Airline;

/**
 * Created by joey on 9/13/13.
 */
public class FlightDetailView extends LinearLayout
        implements ExpandableListView.OnChildClickListener
{
    private ExpandableListView flightsListView;
    private FlightDetailAdapter flightDetailAdapter;
    private ViewGroup flightDetailLayout;

    private double gas_multiplier = 0.113;
    private double miles_driven_multiplier = 2.4;
    private double vehicle_multiplier = 0.0002;

    public FlightDetailView(Context context, ArrayList<Flight> flights)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        flightDetailAdapter = new FlightDetailAdapter(getContext(), new FlightDetailHash(flights, getHash(App.Results.airlines())));
        flightDetailLayout = (ViewGroup)inflater.inflate(R.layout.layout_flight_detail, null);
        ((CTTextViewItalic)flightDetailLayout.findViewById(R.id.text_co2)).setText(flights.get(0).CO2.intValue() + "");
        ((CTTextViewItalic)flightDetailLayout.findViewById(R.id.equivalent)).setText((flights.get(0).CO2.intValue()*gas_multiplier)+"");
//        ((CTTextViewItalic)flightDetailLayout.findViewById(R.id.text_equivalence)).setText(" \u2245 "+flights.get(0).equivalent().substring(0,4)+" cars driven per day");


        flightsListView = (ExpandableListView)flightDetailLayout.findViewById(R.id.detail_list_view);
        flightsListView.setAdapter(flightDetailAdapter);
        flightsListView.setOnChildClickListener(this);

        for(int i=0; i < flightDetailAdapter.getGroupCount(); i++)
            flightsListView.expandGroup(i);

        addView(flightDetailLayout);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l)
    {
        FlightChild child_clicked = (FlightChild) flightDetailAdapter.getChild(groupPos, childPos);
        for (int i=0; i < flightDetailAdapter.getGroupCount(); i++) {
            for (int j=0; j < flightDetailAdapter.getChildrenCount(i); j++) {
                FlightChild otherChild = (FlightChild) flightDetailAdapter.getChild(i,j);
                boolean higlight = (child_clicked.tag() == otherChild.tag())
                                   && !otherChild.highlight();
                ((FlightChild) flightDetailAdapter.getChild(i,j)).highlight(higlight);
            }
        }
        flightDetailAdapter.notifyDataSetChanged();
        return false;
    }

    private HashMap<String, Airline> getHash(ArrayList<Airline> airlines)
    {
        HashMap<String, Airline> airlineHashMap = new HashMap<String, Airline>();
        for (Airline airline : airlines)
            airlineHashMap.put(airline.code, airline);
        return airlineHashMap;
    }
}
