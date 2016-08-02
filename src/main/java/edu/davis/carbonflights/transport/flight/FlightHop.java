package edu.davis.carbonflights.transport.flight;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import edu.davis.carbonflights.transport.hop.Hop;

/**
 * Created by joey on 7/12/13.
 */
public class FlightHop extends Hop
{
    public String airline;
    public String flightNum;

    public String depart;    /* in minutes */
    public String arrive;    /* in minutes */

    /* Keys for JSONObject returned from rome2rio API */
    private static final String
            SRC_K = "sCode",    /* Source airport code (IATA) */
            DST_K = "tCode",    /* Target airport code (IATA) */
            AIR_K = "airline",  /* Airline code (IATA)*/
            FLN_K = "flight",   /* flight number */
            DEP_K = "sTime",    /* Departure time (24-hour local time - hh:mm) */
            ARV_K = "tTime",    /* Arrival time (24-hour local time - hh:mm) */
            DUR_K = "duration"; /* Estimated flight duration (in minutes) */

    public FlightHop() { }

    public FlightHop(Parcel in)
    {
        super(in);
        airline = in.readString();
        flightNum = in.readString();
        depart = in.readString();
        arrive = in.readString();
    }


    public FlightHop(JSONObject json) {
        try {
            source      = json.getString(SRC_K);
            destination = json.getString(DST_K);
            airline     = json.getString(AIR_K);
            flightNum   = json.getString(FLN_K);
            depart      = json.getString(DEP_K);
            arrive      = json.getString(ARV_K);
            duration    = json.getDouble(DUR_K);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return source + " > " + destination + " " + flightNum;
    }

    public static FlightHop header()
    {
        FlightHop header = new FlightHop();
        header.depart = "Departs";
        header.arrive = "Arrives";
        return header;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeString(airline);
        out.writeString(flightNum);
        out.writeString(depart);
        out.writeString(arrive);
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator<FlightHop> CREATOR = new Parcelable.Creator<FlightHop>() {
        public FlightHop createFromParcel(Parcel in) {
            return new FlightHop(in);
        }
        public FlightHop[] newArray(int size) {
            return new FlightHop[size];
        }
    };

}
