package edu.davis.carbonflights.api;

import org.json.JSONObject;

import edu.davis.carbonflights.network.JSONReceived;
import edu.davis.carbonflights.utility.Utils;

/**
 * Created by Sumayyah on 8/17/13.
 */

public class CM1 implements JSONReceived {

    /**
     * Callback function when CM1 sends a response for the
     * query you requested.
     * */

    private String group;
    private String child;
    public int type = -1;

    public  CM1Listener cm1Listener;
    public CM1(CM1Listener cm1, int t)
    {
        cm1Listener = cm1;
        type = t;
    }

    public CM1(CM1Listener cm1, String g, String c, int t)
    {
        cm1Listener = cm1;
        group = g;
        child = c;
        type = t;
    }

    public String group() {return group;}
    public String child() {return child;}

    @Override
    public void JSONObjReceived(JSONObject jsonobj) {
        try {
            JSONObject decisions = jsonobj.getJSONObject("decisions");
            JSONObject carbon = decisions.getJSONObject("carbon");
            JSONObject object = carbon.getJSONObject("object");
            double carbonValue = object.getDouble("value");

            JSONObject equivalents = jsonobj.getJSONObject("equivalents");
            String carEquivalent = equivalents.getString("cars_to_priuses_for_a_day");

            cm1Listener.onCM1Recieved(this, carbonValue);

        } catch(Exception e) {
            e.printStackTrace();
            Utils.log("CM1::JSONObjReceived()");
        }
    }

    public static interface CM1Listener {
        public void  onCM1Recieved(CM1 cm1, double carbon);
    }
}
