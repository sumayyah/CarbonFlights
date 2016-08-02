package edu.davis.carbonflights.results;

import java.util.ArrayList;
import java.util.Collections;


import edu.davis.carbonflights.utility.Utils;
import edu.davis.carbonflights.transport.Airline;
import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by joey on 9/24/13.
 */
public class Result
{
    private Result()
    {
        groups = new ArrayList<ResultGroup>();
        airlines = new ArrayList<Airline>();
        min = 0;
        max = 0;
    }


    private static Result instance;
    public static Result getInstance()
    {
        if (instance == null)
            instance = new Result();
        return instance;
    }

    private ArrayList<ResultGroup> groups;
    private ArrayList<Airline> airlines;
    private ArrayList<Double> carbons = new ArrayList<Double>();
    private double min;
    private double max;
    private int transportType;
    private String source;
    private String destination;

    public double getMin() {return min;}
    public double getMax() {return max;}
    public String source() {return source;}
    public String destination() {return destination;}
    public int transportType() {return transportType;}
    public ArrayList<ResultGroup> groups() {return groups;}
    public ArrayList<Airline> airlines() {return airlines;}


    public void source(String s) {source = s;}
    public void destination(String d) {destination = d;}

    public void setMin() {
        populateCarbons();
        min = Collections.min(carbons);
    }
    public void setMax() {
        if(!carbons.isEmpty()){
            max = Collections.max(carbons);
        }
        else Utils.log("Carbons is empty - call setMin first");
    }
    public void setTransportType(int t) {transportType = t;}


    public void add(Transportation transportation, String groupKey, String childKey)
    {
        ResultGroup group = find(groupKey);

        if (group != null)
            group.add(transportation, childKey);
        else
            groups.add(new ResultGroup(transportation, groupKey, childKey));
    }

    public void add(Airline airline)
    {
        airlines.add(airline);
    }

    public void update(String groupKey, String childKey, double carbon)
    {
        ResultGroup group = find(groupKey);

        if (group == null) { /*This should never happen*/
            Utils.log("Result::update() - updating object failed for group:" + groupKey + " child:"+childKey);
            return;
        }

        group.update(childKey, carbon);
    }

    public ResultGroup find(String groupKey)
    {
        //TODO: make constant time startSearch instead of O(n)
        for (ResultGroup group : groups)
            if (group.key().equals(groupKey))
                return group;

        return null;
    }

    public void clear()
    {
        groups.clear();
        airlines.clear();
    }

    public void populateCarbons(){
        for(ResultGroup g: groups()){
            for(ResultChild c: g.children()){
                carbons.add(c.CO2());
            }
        }
    }

    public void clearCarbonArray(){
        if(!carbons.isEmpty()){
            carbons.clear();
        }
    }
}
