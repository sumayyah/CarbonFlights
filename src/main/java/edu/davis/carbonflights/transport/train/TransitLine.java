package edu.davis.carbonflights.transport.train;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import edu.davis.carbonflights.utility.Utils;

/**
 * Created by joey on 9/23/13.
 */
public class TransitLine implements Parcelable
{
    String name;    // Line display name
    String vehicle; // Vehicle display name
    String code = "";    // Line code (optional)
    String agency = "";  // Agency code (optional)
    float frequency;// Estimated feequency (per week)
    float duration; // Estimated duration (in minutes)

    private static String
                    NME_K = "name",
                    VHC_K = "vehicle",
                    CDE_K = "code",
                    AGY_K = "agency",
                    FQY_K = "frequency",
                    DUR_K = "duration";


    public TransitLine(JSONObject json)
    {
        try {
            name = json.getString(NME_K);
            vehicle = json.getString(VHC_K);
            frequency = ((Double)json.getDouble(FQY_K)).floatValue();
            duration = ((Double)json.getDouble(DUR_K)).floatValue();
            code = json.getString(CDE_K); //optional
            agency = json.getString(AGY_K);//optional
        } catch (Exception e) {
            Utils.log("TransitLine:Constructor error parsing json");
            e.printStackTrace();
        }

    }

    public TransitLine(Parcel in)
    {
        name = in.readString();
        vehicle = in.readString();
        code = in.readString();
        agency = in.readString();
        frequency = in.readFloat();
        duration = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel out, int i)
    {
        out.writeString(name);
        out.writeString(vehicle);
        out.writeString(code);
        out.writeString(agency);
        out.writeFloat(frequency);
        out.writeFloat(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString()
    {
        return "name:" + name +
                " vehicle:" + vehicle +
                " code:" + code +
                " agency:" + agency +
                " frequncy:" + frequency +
                " duration:" + duration;
    }

    public String getDuration(){

        return Utils.time((double)duration);

    }

    public static final Parcelable.Creator<TransitLine> CREATOR = new Parcelable.Creator<TransitLine>() {
        public TransitLine createFromParcel(Parcel in) {
            return new TransitLine(in);
        }
        public TransitLine[] newArray(int size) {
            return new TransitLine[size];
        }
    };
}
