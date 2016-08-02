package edu.davis.carbonflights.transport.hop;

import android.os.Parcelable;

/**
 * Created by joey on 8/29/13.
 */
public interface HopInterface extends Parcelable
{
    public String getKey();
    public String getDuration();
    public double getDurationVal();
    public String source();
    public String destination();
}
