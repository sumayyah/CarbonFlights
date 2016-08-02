package edu.davis.carbonflights.transport.bus;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import edu.davis.carbonflights.utility.Utils;

/**
 * Created by Sumayyah on 9/25/13.
 */
public class BusTransitLine implements Parcelable {

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

    public BusTransitLine(JSONObject json){

        try {
            name = json.getString(NME_K);
            vehicle = json.getString(VHC_K);
            frequency = ((Double)json.getDouble(FQY_K)).floatValue();
            duration = ((Double)json.getDouble(DUR_K)).floatValue();
            try { code = json.getString(CDE_K); }
            catch (Exception e) { code = "";}

            try { agency = json.getString(AGY_K); }
            catch (Exception e) { agency = ""; }
            Utils.log("Bus details: "+vehicle+" "+frequency+" "+duration+" "+agency);
        }
        catch (Exception e) {
            Utils.log("BusTransitLine:Constructor error parsing json");
            e.printStackTrace();
        }
    }

    public BusTransitLine(Parcel in)
    {
        name = in.readString();
        vehicle = in.readString();
        code = in.readString();
        agency = in.readString();
        frequency = in.readFloat();
        duration = in.readFloat();

    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(name);
        out.writeString(vehicle);
        out.writeString(code);
        out.writeString(agency);
        out.writeFloat(frequency);
        out.writeFloat(duration);
    }


    @Override
    public String toString()
    {
        return
                "name:" + name +
                " vehicle:" + vehicle +
                " code:" + code +
                " agency:" + agency +
                " frequency:" + frequency +
                " duration:" + duration;
    }

    public String getDuration(){

        return Utils.time((double)duration);

    }


    public static final Parcelable.Creator<BusTransitLine> CREATOR = new Parcelable.Creator<BusTransitLine>() {
        public BusTransitLine createFromParcel(Parcel in) {
            return new BusTransitLine(in);
        }
        public BusTransitLine[] newArray(int size) {
            return new BusTransitLine[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


}
