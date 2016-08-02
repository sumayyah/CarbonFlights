package edu.davis.carbonflights.transport.hop;

import android.os.Parcel;
import android.os.Parcelable;

import edu.davis.carbonflights.utility.Utils;

/**
 * Created by joey on 8/14/13.
 */
public abstract class Hop implements HopInterface
{
    public String source;
    public String destination;
    public double duration;

    public Hop() { }

    public Hop(Parcel in)
    {
        source = in.readString();
        destination = in.readString();
        duration = in.readDouble();
    }

    public String source() { return source;}

    public String destination() {return destination;}

    public String getKey()
    {
        return source + " \u2192 " + destination;

    }
    public String getDuration()
    {
        int hours = (int)(duration/60);
        int minutes = (int)(duration - (hours*60));
        return  hours+"hrs "+minutes+"min";
    }
    public double getDurationVal(){
        return duration;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(source);
        out.writeString(destination);
        out.writeDouble(duration);
    }

    @Override
    public int describeContents(){
        return 0;
    }


    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator<Hop> CREATOR = new Parcelable.Creator<Hop>() {
        public Hop createFromParcel(Parcel in) {
            return null;
        }
        public Hop[] newArray(int size) {
            return new Hop[size];
        }
    };
}
