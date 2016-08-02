package edu.davis.carbonflights.transport.bus;

import java.util.ArrayList;

/**
 * Created by Sumayyah on 9/25/13.
 */
public class BusGroup {

    private String key;
    private ArrayList<BusChild> children;

    public BusGroup(String k, ArrayList<BusTransitLine> transitLines)
    {
        key = k;
        children = new ArrayList<BusChild>();

        for (BusTransitLine transitLine : transitLines)
            children.add(new BusChild(transitLine));
    }

    public String key() {return key;}
    public ArrayList<BusChild> children() {return children;}
}
