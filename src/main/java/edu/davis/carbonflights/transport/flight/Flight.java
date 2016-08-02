package edu.davis.carbonflights.transport.flight;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by Sumayyah on 7/10/13.
 *
 * flight contains information about a given airplane flight
 * from one place to another.
 *
 */

public class Flight extends Transportation //implements Parcelable {
{

    public static final int Type = 1;
    public static final String flags = "0x000FFFF0";

    public Flight()
    {
        hops =  new ArrayList<FlightHop>();
    }

    public Flight(Parcel in) 
    {
        super(in);
        hops =  new ArrayList<FlightHop>();
        in.readTypedList((ArrayList<FlightHop>)hops, FlightHop.CREATOR);
    }

    public static LinearLayout getView(Activity activity, ArrayList<Transportation> transportations)
    {
        ArrayList<Flight> flights =  new ArrayList<Flight>();
        for (Transportation transportation : transportations)
            flights.add((Flight)transportation);

        FlightDetailView flightDetailView =  new FlightDetailView(activity, flights);
        return flightDetailView;
    }

    @Override
    public String toString() {
        String hopstring = "";
        for (FlightHop hop : (ArrayList<FlightHop>)hops)
            hopstring += hop.toString() + " | ";
        return source + " " + destination + " - \n" + hopstring;
    }

    @Override
    public String getKey() {
        String key = "";
        int i=0;
        for (FlightHop flightHop : (ArrayList<FlightHop>) hops){
            //right arrow in utf8 \u2192
            if (i==0)
                key += flightHop.source /*+ " \u2192 "*/ + ((hops.size()==1)?" \u2192 "+flightHop.destination:"");

            else if(i==(hops.size())-1 && hops.size()>2)
                key+=" \u2192 "+ flightHop.destination;

            else
                key += " \u2192 "+flightHop.source +" \u2192 "+ flightHop.destination;
            i++;
        }
        return key;
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator<Flight> CREATOR = new Parcelable.Creator<Flight>() {
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };
}
