package edu.davis.carbonflights.transport.flight;

import java.util.ArrayList;

/**
 * Created by joey on 9/24/13.
 */
public class FlightGroup
{
    private String key;
    private ArrayList<FlightChild> children;

    public FlightGroup(String k,  ArrayList<FlightHop> hops)
    {
        key = k;
        children = new ArrayList<FlightChild>();
        for (FlightHop flightHop : hops) {
            children.add(new FlightChild(flightHop, children.size()));
        }
    }

    public String key() {return key;}
    public ArrayList<FlightChild> children() {return children;}

}
