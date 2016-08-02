package edu.davis.carbonflights;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.davis.carbonflights.transport.Airline;
import edu.davis.carbonflights.transport.Transportation;
import edu.davis.carbonflights.transport.bus.Bus;
import edu.davis.carbonflights.transport.bus.BusHop;
import edu.davis.carbonflights.transport.car.Car;
import edu.davis.carbonflights.transport.car.CarHop;
import edu.davis.carbonflights.transport.flight.Flight;
import edu.davis.carbonflights.transport.flight.FlightHop;
import edu.davis.carbonflights.transport.hop.HopInterface;
import edu.davis.carbonflights.transport.train.Train;
import edu.davis.carbonflights.transport.train.TrainHop;
import edu.davis.carbonflights.utility.Utils;

/**
 * Created by joey on 9/25/13.
 */
public class Parser
{

    /**
     * Takes in json arrays and parses them into JAVA objects. Saves them in App.Results
     * @see edu.davis.carbonflights.results.Result
     *
     * */

    public static void R2RParse(JSONArray routes, JSONArray airlines)
    {
        try {
            for (int i=0; i < routes.length(); i++){
                JSONArray segments = ((JSONObject)routes.get(i)).getJSONArray("segments");

                for (int j=0; j < segments.length(); j++){
                    JSONObject segment = (JSONObject) segments.get(j);
                    String groupKey;
                    if (App.Results.transportType() == Flight.Type) {
                        groupKey = segment.get("sCode")+" \u2192 "+ segment.get("tCode");
                    } else {
                        groupKey = segment.get("sName")+" \u2192 "+ segment.get("tName");
                    }

                    JSONArray itineraries = segment.getJSONArray("itineraries");

                    for (int h=0; h < itineraries.length(); h++){
                        Transportation transportation;
                        switch (App.Results.transportType()) {
                            case Flight.Type:
                                transportation = new Flight();
                                transportation.source =  segment.getString("sCode");
                                transportation.destination = segment.getString("tCode");
                                transportation.distance = ((Double)(segment.getDouble("distance"))).intValue();
                                break;
                            case Bus.Type:
                                transportation = new Bus();
                                transportation.source =  segment.getString("sName");
                                transportation.destination = segment.getString("tName");
                                transportation.distance = ((Double)(segment.getDouble("distance"))).intValue();
                                break;
                            case Train.Type:
                                transportation = new Train();
                                transportation.source =  segment.getString(TrainHop.SRC_K);
                                transportation.destination = segment.getString(TrainHop.DST_K);
                                transportation.distance = ((Double)(segment.getDouble("distance"))).intValue();
                                break;
                            case Car.Type:
                                transportation = new Car();
                                transportation.source =  segment.getString("sName");
                                transportation.destination = segment.getString("tName");
                                transportation.distance = ((Double)(segment.getDouble("distance"))).intValue();
                                break;
                            default:
                                transportation = new Flight();//for compiler to stop complaining
                                break;
                        }

                        JSONArray legs = ((JSONObject)itineraries.get(h)).getJSONArray("legs");
                        for (int k=0; k < legs.length(); k++ ){
                            JSONArray hops = ((JSONObject)legs.get(k)).getJSONArray("hops");
                            for (int p=0; p < hops.length(); p++) {
                                JSONObject jsonHop = (JSONObject) hops.get(p);
                                HopInterface hop;
                                switch (App.Results.transportType()) {
                                    case Flight.Type:
                                        hop = new FlightHop(jsonHop);
                                        break;
                                    case Bus.Type:
                                        hop = new BusHop(jsonHop);
                                        break;
                                    case Train.Type:
                                        hop = new TrainHop(jsonHop);
                                        break;
                                    case Car.Type:
                                        hop = new CarHop(jsonHop);
                                        break;
                                    default:
                                        hop = new FlightHop(jsonHop);//for compiler to stop complaining
                                        break;
                                }
                                ((ArrayList<HopInterface>)transportation.hops).add(hop);
                            }
                        }
                        String childKey = transportation.getKey();
                        App.Results.add(transportation, groupKey, childKey);
                    }
                }
            }
            for (int i=0; i < airlines.length(); i++) {
                App.Results.add(new Airline((JSONObject) airlines.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
