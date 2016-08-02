package edu.davis.carbonflights.transport.train;

import java.util.ArrayList;

/**
 * Created by joey on 9/24/13.
 */
public class TrainDetailHash
{
    private ArrayList<TrainGroup> groups;

    public TrainDetailHash(ArrayList<Train> trains)
    {
        groups = new ArrayList<TrainGroup>();

        Train train = trains.get(0);

        if (train != null) {
            for (TrainHop hop : (ArrayList<TrainHop>)train.hops)
                groups.add(new TrainGroup(hop.getKey(), hop.transitLines));
        }
    }

    public ArrayList<TrainGroup> groups()
    {
        return groups;
    }
}
