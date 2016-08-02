package edu.davis.carbonflights.results;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by joey on 7/29/13.
 */
public class ResultChild
{
    private String key;
    private double CO2=0.0;
    private Double carEqNum=0.0;

    public ArrayList<Transportation> transportationList;

    public ResultChild(Transportation transportation, String k)
    {
        key = k;
        CO2 = 0.0;
        carEqNum=0.0;
        transportationList = new ArrayList<Transportation>();
        transportationList.add(transportation);
    }

    //getters
    public String key() {return key;}
    public double CO2() {return CO2;}
    public String duration() {return transportationList.get(0).getDuration();}
    public double durationVal(){return transportationList.get(0).getDurationVal();}
    public String distance(){return transportationList.get(0).distance();}

    public void add(Transportation transportation)
    {
        transportationList.add(transportation);
    }


    public void update(double carbon)
    {
        CO2 += carbon;
        for (Transportation transportation : transportationList) {
            transportation.CO2 = CO2;
        }
    }

}
