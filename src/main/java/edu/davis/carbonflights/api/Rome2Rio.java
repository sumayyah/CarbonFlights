package edu.davis.carbonflights.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.davis.carbonflights.network.JSONReceived;
import edu.davis.carbonflights.utility.Utils;


/**
 * Created by joey on 8/7/13.
 */
public class Rome2Rio implements JSONReceived
{

    private R2RListener r2RListener;
    public Rome2Rio(R2RListener r2RListener)
    {
        this.r2RListener = r2RListener;
    }

    /**
     * Callback function when Rome2Rio response is received over the netwok for the
     * query you requested.
     *
     * @param jsonobj A JSON object containing the response from
     *                the query you passed it. If it is null then
     *                there are no results.
     * @see   "http://www.rome2rio.com/documentation/startSearch/"
     * */
    @Override
    public void JSONObjReceived(JSONObject jsonobj)
    {
        if (jsonobj != null){
            try {
                JSONArray routes = jsonobj.getJSONArray("routes");
                JSONArray airlines = jsonobj.getJSONArray("airlines");

                r2RListener.onR2RReceived(this, routes, airlines);
            }
            catch (JSONException e) {
                //TODO: show internal error
                Utils.log("Rome2Rio - caught!");
                e.printStackTrace();
            }
            catch (Exception e) {
                Utils.log("Get distance failed");
                e.printStackTrace();
            }
        } else {
            Utils.log("R2R::JSONObjReceived() - Json object is null");

        }
    }//JSONObjReceived


    public static interface R2RListener {
        public void onR2RReceived(Rome2Rio rome2Rio, JSONArray routes, JSONArray airlines);
    }


}//End Rome2Rio



