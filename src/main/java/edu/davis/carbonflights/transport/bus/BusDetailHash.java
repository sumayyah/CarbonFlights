package edu.davis.carbonflights.transport.bus;

import java.util.ArrayList;

/**
 * Created by Sumayyah on 9/25/13.
 */
public class BusDetailHash {

    private ArrayList<BusGroup> groups;

    public BusDetailHash(ArrayList<Bus> buses)
    {
        groups = new ArrayList<BusGroup>();

        Bus bus = buses.get(0);

        if (bus != null) {
            for (BusHop hop : (ArrayList<BusHop>)bus.hops)
                groups.add(new BusGroup(hop.getKey(), hop.busTransitLines));
        }
    }

    public ArrayList<BusGroup> groups()
    {
        return groups;
    }
}
