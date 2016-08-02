package edu.davis.carbonflights.transport.train;

import java.util.ArrayList;

/**
 * Created by joey on 9/24/13.
 */
public class TrainGroup
{
    private String key;
    private ArrayList<TrainChild> children;

    public TrainGroup(String k, ArrayList<TransitLine> transitLines)
    {
        key = k;
        children = new ArrayList<TrainChild>();

        for (TransitLine transitLine : transitLines)
            children.add(new TrainChild(transitLine));
    }

    public String key() {return key;}
    public ArrayList<TrainChild> children() {return children;}
}
