package edu.davis.carbonflights.transport.bus;

/**
 * Created by Sumayyah on 9/25/13.
 */
public class BusChild {

    public BusChild(BusTransitLine t)
    {
        busTransitLine = t;
    }

    private boolean highlight;
    public boolean highlight() {return highlight;}
    public void highlight(boolean h) {highlight = h;}

    private BusTransitLine busTransitLine;
    public BusTransitLine transitLine() {return busTransitLine;}
    public void transitLine(BusTransitLine t) { busTransitLine = t;}
}
