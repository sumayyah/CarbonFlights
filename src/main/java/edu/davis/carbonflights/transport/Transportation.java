package edu.davis.carbonflights.transport;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.bus.Bus;
import edu.davis.carbonflights.transport.flight.Flight;
import edu.davis.carbonflights.transport.hop.HopInterface;
import edu.davis.carbonflights.transport.train.Train;

/**
 * Created by joey on 8/14/13.
 */
public abstract class Transportation implements Parcelable {

    public static final int Type = 0;
    public String source;//city name
    public String destination;//city name
    public Double CO2 = 0.0;
    public int distance = 0;
    public ArrayList<? extends HopInterface> hops;
    public String equivalent ="";

    public Transportation()
    {
        hops = new ArrayList<HopInterface>();
    }

    public Transportation(Parcel in)
    {
        source = in.readString();
        destination = in.readString();
        CO2 = in.readDouble();
        equivalent = in.readString();
        distance = in.readInt();
    }

    public static ViewGroup getLayout(Activity activity, ArrayList<Transportation> transportations)
    {
        if(transportations.get(0) instanceof Flight) {
            return Flight.getView(activity, transportations);
        } else if (transportations.get(0) instanceof Bus) {
            return Bus.getView(activity, transportations);
        } else if (transportations.get(0) instanceof Train) {
            return Train.getView(activity, transportations);
        }
        return null;
    }

    public String getKey()
    {
        String key = "";
        int i=0;
        for (HopInterface hop : hops) {
            if (i==0) key += hop.source() + ((hops.size()==1)?hop.destination():"");
            else key += " \u2192 "+ hop.destination();
            i++;
        }
        return key;
    }

    public String getDuration()
    {
        double duration = getDurationVal();
        int hours = (int)(duration/60);
        int minutes = (int)(duration - (hours*60));
        return hours+"hrs "+minutes+"min";
    }

    public double getDurationVal()
    {
        double duration = 0.0;
        for(HopInterface hop: hops) {
            duration += hop.getDurationVal();
        }
        return duration;
    }

    public String distance(){
        return Integer.toString(distance);
    }
    public String carEquivalent(){
        return equivalent;
    }

    @Override
    public void writeToParcel(Parcel out, int i)
    {
        out.writeString(source);
        out.writeString(destination);
        out.writeDouble(CO2);
        out.writeString(equivalent);
        out.writeInt(distance);
        out.writeTypedList(hops);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }


//    @SuppressWarnings("unchecked")
//    public static final Parcelable.Creator<Transportation> CREATOR = new Parcelable.Creator<Transportation>() {
//        public Transportation createFromParcel(Parcel in) {
//            return null;
//        }
//        public Transportation[] newArray(int size) {
//            return new Transportation[size];
//        }
//    };
}
