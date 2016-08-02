package edu.davis.carbonflights.transport;

/**
 * Created by Sumayyah on 9/13/13.
 */
public class Position {
    private float lat;
    private float lng;

    public Position(float lat, float lng){
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat(){
        return Float.toString(lat);
    }
    public String getLng(){
        return Float.toString(lng);
    }
}
