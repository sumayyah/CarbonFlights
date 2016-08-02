package edu.davis.carbonflights.transport.train;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.hop.Hop;

/**
 * Created by joey on 8/14/13.
 */
public class TrainHop extends Hop implements Parcelable
{
    public float frequency = 0.0f;
    public ArrayList<TransitLine> transitLines;

    public static final String
            SRC_K = "sName",    /* Source city */
            DST_K = "tName",    /* Target city */
            DUR_K = "duration", /* Estimated duration (in minutes) */
            FRE_K = "frequency",/* frequency of icon_train service */
            LNS_K = "lines";    /* Array of transit lines */


    public TrainHop(JSONObject json)
    {
        try {
            source  = json.getString(SRC_K);
            destination = json.getString(DST_K);
            duration = json.getDouble(DUR_K);
            frequency = ((Double)json.getDouble(FRE_K)).floatValue();
            transitLines = getTransitLines(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TransitLine>  getTransitLines(JSONObject json)
    {
        ArrayList<TransitLine> lines = new ArrayList<TransitLine>();
        try {
            JSONArray jsonLines = json.getJSONArray(LNS_K);
            for (int i=0; i < jsonLines.length(); i++)
                lines.add( new TransitLine(jsonLines.getJSONObject(i)) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }


    public TrainHop(Parcel in)
    {
        super(in);
        frequency = in.readFloat();
        transitLines = new ArrayList<TransitLine>();
        in.readTypedList(transitLines, TransitLine.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeFloat(frequency);
        out.writeTypedList(transitLines);
    }

    @Override
    public String toString() {

        String lineSting = "";
        int i = 0;

        for (TransitLine tline : transitLines)
            lineSting += "tline:" + (i++) + " " + tline.toString() + "\n";

        return  "source:" + source +
                " dest:" + destination +
                " freq:" + frequency +
                " dura:" + duration +
                "\n- Transit Lines -\n" + lineSting;
    }

    public static final Parcelable.Creator<TrainHop> CREATOR = new Parcelable.Creator<TrainHop>()
    {
        public TrainHop createFromParcel(Parcel in) {
            return new TrainHop(in);
        }
        public TrainHop[] newArray(int size) {
            return new TrainHop[size];
        }
    };

}
