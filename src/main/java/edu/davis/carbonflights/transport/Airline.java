package edu.davis.carbonflights.transport;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by joey on 8/7/13.
 */
public class Airline implements Parcelable {

    public String code;     //	Airline code (IATA)
    public String name;     //	Display name
    public ArrayList<String> timesArray = new ArrayList<String>(); //Holds the times at which this airline offers flights

    public static String KEY = "airlines";

    public Airline(JSONObject json) {
        try {
            code = json.getString("code");
            name = json.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Airline(Parcel in) {
        code = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(code);
        out.writeString(name);
    }

    public static final Parcelable.Creator<Airline> CREATOR = new Parcelable.Creator<Airline>() {
        public Airline createFromParcel(Parcel in) {
            return new Airline(in);
        }

        public Airline[] newArray(int size) {
            return new Airline[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }
}
