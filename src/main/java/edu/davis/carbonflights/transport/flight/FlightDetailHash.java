package edu.davis.carbonflights.transport.flight;

import java.util.ArrayList;
import java.util.HashMap;

import edu.davis.carbonflights.transport.Airline;

/**
 * Created by joey on 9/18/13.
 */
public class FlightDetailHash
{
    private ArrayList<FlightGroup> groups;
    private HashMap<String, Airline> airlines;

    public FlightDetailHash(ArrayList<Flight> flights, HashMap<String, Airline> a)
    {
        groups = new ArrayList<FlightGroup>();
        airlines = a;

        Flight fflight = flights.get(0);
        int i = 0;

        for (FlightHop hop : (ArrayList<FlightHop>) fflight.hops) {
            String key = hop.getKey();
            ArrayList<FlightHop> tmp_hops = new ArrayList<FlightHop>();
            tmp_hops.add(FlightHop.header());

            for (Flight flight : flights)
                tmp_hops.add((FlightHop)flight.hops.get(i));

            groups.add(new FlightGroup(key, tmp_hops));
            i++;
        }

    }

    public ArrayList<FlightGroup> groups() { return groups; }

    public String airline(String a)
    {
        if (airlines.get(a).name == null) return a;
        else return airlines.get(a).name;
    }
}
