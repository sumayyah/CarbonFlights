package edu.davis.carbonflights.network;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;

import edu.davis.carbonflights.utility.Utils;
import edu.davis.carbonflights.transport.Position;


/**
 * Created by Sumayyah on 7/18/13.
 * A General purpose class that is used for REST services. First instantiate
 * an object of this class with a url and parameters. Then call execute
 * with a class that implements JSONReceived to be notified when its done
 * with the request and has received a json object or not.
 */
public class HttpRequest extends AsyncTask<JSONReceived, Void, JSONObject> {


    static String CM1_key = "822f0a463401760fb3c465e88bb6f538";
//    public static String ROME2RIO_URL = "http://evaluate.rome2rio.com/api/1.2/json/Search?key=9NxW18vX";
    public static String ROME2RIO_URL = "http://free.rome2rio.com/api/1.2/json/Search?key=RgvmnZCx";
    public static String CM1_FLIGHT_KEY = "http://impact.brighterplanet.com/flights.json?key="+CM1_key;
    public static String CM1_BUS_KEY = "http://impact.brighterplanet.com/bus_trips.json?key="+CM1_key;
    public static String CM1_TRAIN_KEY = "http://impact.brighterplanet.com/rail_trips.json?key="+CM1_key;
    public static String CM1_CAR_KEY = "http://impact.brighterplanet.com/automobile_trips.json?key="+CM1_key;
    private String URL = "";
    private Parameters parameters = null;
    public JSONReceived jsonHandler = null;
    private HttpGet request;

    public HttpRequest(String url, Parameters parameters, JSONReceived json){
        setURL(url);
        setParameters(parameters);
        setJsonHandler(json);

    }

    public void abort()
    {
        if (request != null)
            request.abort();
    }
    public void setURL(String url) {
        this.URL = url;
    }

    public void setParameters(Parameters params) {
        this.parameters = params;
    }

    public void setJsonHandler(JSONReceived json){
        this.jsonHandler = json;
    }

    /**
     * Returns a json object using a REST service with the given base url
     * and parameters.
     *
     * @param baseurl An absolute URL used for a RESTful API. Also contains
     *                the API key as the first parameter
     * @param parameters The parameters used for the REST API.
     * @return           The json object returned from the REST API using the
     *                   base URL and parameters.
     * @see Parameters
     */
    private JSONObject getJson(String baseurl, Parameters parameters) throws Exception{
        if (baseurl==null || parameters==null)
            return null;

        try {
            HttpClient client = new DefaultHttpClient();
            String uri_string = baseurl + parameters.getQueryString();

            request = new HttpGet();
            request.setURI(new URI(uri_string));
            HttpResponse response = client.execute(request);
            if(response.getStatusLine().getStatusCode() == 200) {
                /* Success */
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                JSONObject object = new JSONObject(data) ;

                return object;
            } //else error
        } catch (Exception e) {
            Utils.log("HttpRequest::getJson() exception");
            e.printStackTrace();
        }
        return null;
    }//getJson

    @Override
    protected JSONObject doInBackground(JSONReceived... jsonHandlers) {
        try {
            JSONObject jsonObj = getJson(URL, parameters);

            return jsonObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }//doInBackground

    @Override
    protected void onPostExecute(JSONObject jsonobj) {

        super.onPostExecute(jsonobj);
        if (jsonHandler != null){

            jsonHandler.JSONObjReceived(jsonobj);

        }
    }

    /**
     *  Wrapper class for hashmap with parameter labels and their associated values.
     *  Example:
     *      label = "origin_airport"
     *      value = "SFO
     *  */
    public static class Parameters {

        HashMap<String, String> paramsAndLabels = null;
        HashMap<String, Position> posParamsAndLabels = null;
        int type = 0;

        public Parameters(){
            paramsAndLabels = new HashMap<String, String>();
            posParamsAndLabels = new HashMap<String, Position>();
        }

        public Parameters (String paramLabel, String value) {
            paramsAndLabels = new HashMap<String, String>();
            add(paramLabel, value);
            type = 1;
        }

        public Parameters(String paramLabel, Position coordinates) {
            posParamsAndLabels = new HashMap<String, Position>();
            add(paramLabel, coordinates);
            type = 2;
        }

        public Parameters add(String paramLabel, String value) {
            paramsAndLabels.put(paramLabel, value);
            return this;
        }

        public Parameters add(String paramLabel, Position coordinates) {
            posParamsAndLabels.put(paramLabel, coordinates);
            return this;
        }

        /**
         * Returns a string that represents the parameters for a REST API.
         * @return A string of this format:
         *              &parameter1_label=value1&parameter2_label=value2... etc.
         * */
        String getQueryString() {
            String query = "";
            switch(type){

                case 1:
                    for (HashMap.Entry<String, String> entry : paramsAndLabels.entrySet()) {
                        String paramLabel = entry.getKey();
                        String value = entry.getValue();
                        try { /* we use the encoding to sanitize the user input */
                            query += "&"+paramLabel+"="+URLEncoder.encode(value, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (HashMap.Entry<String, Position> entry : posParamsAndLabels.entrySet()) {
                        String paramLabel = entry.getKey();
                        Position value = entry.getValue();
                        String cat = value.getLat()+"\u002C"+value.getLng();
                        try { /* we use the encoding to sanitize the user input */
                            query += "&"+paramLabel+"="+URLEncoder.encode(cat, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    query+="&flags=0x0000EFFF";
                    break;
                default:
                    Utils.log("Wrong type for getquerytype");
                    break;
            }
            return query;
        }//getQueryString
    }//Parameters

} //End main
