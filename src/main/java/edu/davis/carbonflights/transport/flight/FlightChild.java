package edu.davis.carbonflights.transport.flight;

/**
 * Created by joey on 9/24/13.
 */
public class FlightChild
{
    public FlightChild(FlightHop f, int t)
    {
        flighthop = f;
        tag = t;
        highlight = false;
     }

    private FlightHop flighthop;
    public FlightHop flighthop(){return flighthop;}

    private boolean highlight;
    public boolean highlight() {return highlight;}
    public void highlight(boolean h) {highlight = h;}


    private int tag;
    public int tag() {return tag;}

}
