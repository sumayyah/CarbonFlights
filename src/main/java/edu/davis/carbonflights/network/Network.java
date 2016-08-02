package edu.davis.carbonflights.network;

import org.json.JSONArray;

import java.util.ArrayList;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.Parser;
import edu.davis.carbonflights.api.CM1;
import edu.davis.carbonflights.api.Rome2Rio;
import edu.davis.carbonflights.results.ResultChild;
import edu.davis.carbonflights.results.ResultGroup;
import edu.davis.carbonflights.transport.bus.Bus;
import edu.davis.carbonflights.transport.car.Car;
import edu.davis.carbonflights.transport.flight.Flight;
import edu.davis.carbonflights.transport.hop.HopInterface;
import edu.davis.carbonflights.transport.train.Train;
import edu.davis.carbonflights.utility.Utils;

/**
 * Created by Sumayyah on 7/18/13.
 */

public class Network implements Rome2Rio.R2RListener, CM1.CM1Listener
{

    ArrayList<HttpRequest> requests;

    private static Network instance = null;
    protected Network()
    {
        requests = new ArrayList<HttpRequest>();
        callsMade = 0;
    }

    public static Network getInstance()
    {
        if(instance == null)
            instance = new Network();
        return instance;
    }

    public void add(HttpRequest req)
    {
        requests.add(req);
    }

    public void dequeue()
    {
        if (!requests.isEmpty()) {
            HttpRequest request = requests.remove(0);
            request.execute();
        } else {
            Utils.log("Network::dequeue() - empty list");
        }
    }

    public void clear()
    {
        for (HttpRequest request : requests)
            request.abort();

        requests.clear();
        callsMade = 0;
        callsReceived = 0;
    }

    private int callsMade;
    private int callsReceived;
    public boolean done() {
        if (callsReceived == callsMade) return true;
        else return false;
    }

    @Override
    public void onR2RReceived(Rome2Rio rome2Rio, JSONArray routes, JSONArray airlines)
    {
        //parses response and saves to Results
        Parser.R2RParse(routes, airlines);
        if (listener != null)
            listener.onDataReceived();

        if(routes == null || routes.length() == 0) {
            listener.onNoResults();
        }

        //get Carbon Emissions
        for (ResultGroup group : App.Results.groups()) {
            for (ResultChild child : group.children()) {
                String trainDist = child.transportationList.get(0).distance();
                for (HopInterface hop : child.transportationList.get(0).hops) {
                    HttpRequest.Parameters parameters;
                    HttpRequest request;
                    switch (App.Results.transportType()) {
                        case Flight.Type:
                            parameters = new HttpRequest.Parameters("destination_airport", hop.destination()).add("origin_airport", hop.source());
                            request = new HttpRequest(HttpRequest.CM1_FLIGHT_KEY, parameters, new CM1(this, group.key(), child.key(),Flight.Type));
                            add(request);
                            callsMade++;
                            break;
                        case Bus.Type:
                            parameters = new HttpRequest.Parameters("duration", hop.getDurationVal()*60+"");
                            request = new HttpRequest(HttpRequest.CM1_BUS_KEY, parameters, new CM1(this, group.key(),child.key(), Bus.Type));
                            add(request);
                            callsMade++;
                            break;
                        case Train.Type:
                            parameters = new HttpRequest.Parameters("distance", trainDist);
                            request = new HttpRequest(HttpRequest.CM1_TRAIN_KEY, parameters, new CM1(this, group.key(),child.key(), Train.Type));
                            add(request);
                            callsMade++;
                            break;
                        case Car.Type:
                            parameters = new HttpRequest.Parameters("duration", hop.getDurationVal()+"");
                            request = new HttpRequest(HttpRequest.CM1_CAR_KEY, parameters, new CM1(this, group.key(),child.key(), Car.Type));
                            add(request);
                            callsMade++;
                    }
                }
            }
        }//end for group
        dequeue();
    }

    @Override
    public void onCM1Recieved(CM1 cm1, double carbon)
    {
        callsReceived++;
        if (done()) {
            callsMade = 0;
            callsReceived = 0;
        }

        App.Results.update(cm1.group(), cm1.child(), carbon);
        if (listener != null)
            listener.onDataReceived();

        dequeue();
    }


    public static interface Listener {
        public void onDataReceived();
        public void onNoResults();
    }

    private Listener listener;
    public void setListener(Listener l) {
        listener = l;
    }

}
