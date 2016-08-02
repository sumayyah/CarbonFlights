package edu.davis.carbonflights.transport.bus;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.hop.Hop;
import edu.davis.carbonflights.utility.Utils;


/**
 * Created by joey on 8/14/13.
 */
public class BusHop extends Hop implements Parcelable
{
    public float frequency = 0.0f;
    public ArrayList<BusTransitLine> busTransitLines;

    private static final String
            SRC_K = "sName",    /* Source city */
            DST_K = "tName",    /* Target city */
            DUR_K = "duration", /* Estimated duration (in minutes) */
            FRE_K = "frequency",    /* frequency of icon_bus service */
            LNS_K = "lines";    /* Array of transit lines */

    public BusHop(Parcel in)
    {
        super(in);
        frequency = in.readFloat();
        busTransitLines = new ArrayList<BusTransitLine>();
        in.readTypedList(busTransitLines, BusTransitLine.CREATOR);
    }

    public BusHop(JSONObject json)
    {
        try {
            source  = json.getString(SRC_K);
            destination = json.getString(DST_K);
            duration = json.getDouble(DUR_K);
            frequency = ((Double)json.getDouble(FRE_K)).floatValue();
            busTransitLines = getBusTransitLines(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BusTransitLine> getBusTransitLines(JSONObject json)
    {

        ArrayList<BusTransitLine> busTransits = new ArrayList<BusTransitLine>();

        try
        {
            JSONArray jsonLines = json.getJSONArray(LNS_K);
            for (int i=0; i < jsonLines.length(); i++)
                busTransits.add( new BusTransitLine(jsonLines.getJSONObject(i)) );
        }
        catch (Exception e)
        { e.printStackTrace();}

        return busTransits;
    }

    public double getDurationVal()
    {
        Utils.log("Duration val " + duration);
        return duration;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeFloat(frequency);
        out.writeTypedList(busTransitLines);
    }

    public static final Parcelable.Creator<BusHop> CREATOR = new Parcelable.Creator<BusHop>() {
        public BusHop createFromParcel(Parcel in) {
            return new BusHop(in);
        }
        public BusHop[] newArray(int size) {
            return new BusHop[size];
        }
    };
}
