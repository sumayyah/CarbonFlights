package edu.davis.carbonflights.transport.car;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import edu.davis.carbonflights.transport.hop.Hop;

/**
 * Created by Sumayyah on 9/19/13.
 */
public class CarHop extends Hop implements Parcelable{

    public String distance;
    public String frequency;

    private static final String
            SRC_K = "sName",    /* Source city */
            DST_K = "tName",    /* Target city */
            DUR_K = "duration", /* Estimated duration (in minutes) */
            DIS_K = "distance", /* Estimated distance between source and dest*/
            FRE_K = "frequency";    /* frequency of icon_bus service */

    public CarHop(Parcel in)
    {
        super(in);
        distance = in.readString();
        frequency = in.readString();
    }

    public CarHop(JSONObject json)
    {
        try {
            source  = json.getString(SRC_K);
            destination = json.getString(DST_K);
            duration = json.getDouble(DUR_K);
            distance = json.getString(DIS_K);
            frequency = json.getString(FRE_K);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public double getDurationVal()
    {
        return duration * 60.0d;
    }

    public String getDist(){
        return distance;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeDouble(Double.parseDouble(distance));
        out.writeString(frequency);
    }

    public static final Parcelable.Creator<CarHop> CREATOR = new Parcelable.Creator<CarHop>() {
        public CarHop createFromParcel(Parcel in) {
            return new CarHop(in);
        }
        public CarHop[] newArray(int size) {
            return new CarHop[size];
        }
    };
}
