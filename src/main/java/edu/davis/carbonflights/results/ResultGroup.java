package edu.davis.carbonflights.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.davis.carbonflights.utility.Utils;
import edu.davis.carbonflights.transport.Transportation;

/**
 * Created by joey on 7/29/13.
 */
public class ResultGroup
{
    private String key;
    private ArrayList<ResultChild> children;// = new ArrayList<ResultChild>();

    public ResultGroup(Transportation transportation, String group, String child)
    {
        key = group;
        children = new ArrayList<ResultChild>();
        children.add(new ResultChild(transportation, child));

    }

    public String key() {return key;}
    public ArrayList<ResultChild> children() {return children;}


    public void add(Transportation transportation, String childKey)
    {
        ResultChild child = find(childKey);

        if (child != null)
            child.add(transportation);
        else
            children.add(new ResultChild(transportation, childKey));

    }


    public ResultChild find(String childkey)
    {
        //TODO: make constant time startSearch instead of O(n)
        for (ResultChild child : children())
            if (child.key().equals(childkey))
                return child;

        return null;
    }

    public void update(String childkey, double carbon)
    {
        ResultChild child = find(childkey);

        if (child == null) {
            //should NEVER happen!
            Utils.log("ResultGroup::update() - updating object failed for child:" + childkey);
            return;
        }

        child.update(carbon);
    }


    public void sort(final String type)
    {
        Collections.sort(children, new Comparator<ResultChild>() {
            @Override
            public int compare(ResultChild resultChild, ResultChild resultChild2) {
                if (type.equals("CO2")) {
                    if (resultChild.CO2() == resultChild2.CO2()) return 0;
                    else return resultChild.CO2() < resultChild2.CO2() ? -1:1;
                }
                else if(type.equals("duration")){
                    Double d1 = resultChild.durationVal();
                    Double d2 = resultChild2.durationVal();
                    return d1.compareTo(d2);
                } //else other types
                return 0;
            }
        });
    }

}
