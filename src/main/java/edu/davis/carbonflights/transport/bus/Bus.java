package edu.davis.carbonflights.transport.bus;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by joey on 8/14/13.
 */
public class Bus extends Transportation {

    public static final int Type = 2;

    public static final String flags = "0x000FF0FF";

    public Bus() {
        hops = new ArrayList<BusHop>();
    }

    public Bus(Parcel in) {
        super(in);
        hops = new ArrayList<BusHop>();
        in.readTypedList((ArrayList<BusHop>)hops, BusHop.CREATOR);
    }

    @Override
    public String getKey() {
         String key = "";
         int i=0;
         for (BusHop busHop : (ArrayList<BusHop>)hops){
             //right arrow in utf8 \u2192
             if (i==0)
                 key += busHop.source + ((hops.size()==1)?" \u2192 "+busHop.destination:"");
             else
                 key += " \u2192 "+ busHop.destination;
             i++;
         }
         return key;
    }

    public static LinearLayout getView(Activity activity, ArrayList<Transportation> transportations)
    {
        ArrayList<Bus> buses =  new ArrayList<Bus>();
        for (Transportation transportation : transportations)
            buses.add((Bus)transportation);


        BusDetailView busDetailView = new BusDetailView(activity, buses);

        return busDetailView;
    }
    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator<Bus> CREATOR = new Parcelable.Creator<Bus>() {
        public Bus createFromParcel(Parcel in) {
            return new Bus(in);
        }

        public Bus[] newArray(int size) {
            return new Bus[size];
        }
    };

}
