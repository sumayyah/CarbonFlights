package edu.davis.carbonflights.transport.train;

/**
 * Created by joey on 9/24/13.
 */
public class TrainChild
{
    public TrainChild(TransitLine t)
    {
        transitLine = t;
    }

    private boolean highlight;
    public boolean highlight() {return highlight;}
    public void highlight(boolean h) {highlight = h;}

    private TransitLine transitLine;
    public TransitLine transitLine() {return transitLine;}
    public void transitLine(TransitLine t) { transitLine = t;}

}
