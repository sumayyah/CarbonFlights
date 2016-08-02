package edu.davis.carbonflights.transport.train;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by joey on 8/14/13.
 */
public class Train extends Transportation {

    public static final int Type = 3;
    public static final String flags = "0x000FFF0F";

    public Train() {
        hops = new ArrayList<TrainHop>();
    }

    public Train(Parcel in) {
        super(in);
        hops = new ArrayList<TrainHop>();
        in.readTypedList((ArrayList<TrainHop>)hops, TrainHop.CREATOR);
    }

    @Override
    public String getKey() {
        String key = "";
        int i=0;
        for (TrainHop trainHop : (ArrayList<TrainHop>)hops){
            //right arrow in utf8 \u2192
            if (i==0)
                key += trainHop.source + ((hops.size()==1)?" \u2192 "+trainHop.destination:"");
            else
                key += " \u2192 "+ trainHop.destination;
            i++;
        }
        return key;
    }

    @Override
    public String toString() {
        String hopstring = "";
        int i = 0;
        for (TrainHop hop : (ArrayList<TrainHop>)hops)
            hopstring += "hop:" + (i++) + " " + hop.toString() + "\n";
        return source + " " + destination + "\n- Hops -\n" + hopstring;
    }

    public static LinearLayout getView(Activity activity, ArrayList<Transportation> transportations)
    {
        ArrayList<Train> trains =  new ArrayList<Train>();
        for (Transportation transportation : transportations)
            trains.add((Train)transportation);

        TrainDetailView trainDetailView =  new TrainDetailView(activity, trains);

        return trainDetailView;
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator<Train> CREATOR = new Parcelable.Creator<Train>() {
        public Train createFromParcel(Parcel in) {
            return new Train(in);
        }

        public Train[] newArray(int size) {
            return new Train[size];
        }
    };


}
