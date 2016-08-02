package edu.davis.carbonflights.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import edu.davis.carbonflights.App;
import edu.davis.carbonflights.R;
import edu.davis.carbonflights.api.CM1;
import edu.davis.carbonflights.api.Rome2Rio;
import edu.davis.carbonflights.customviews.CTTextView;
import edu.davis.carbonflights.network.HttpRequest;
import edu.davis.carbonflights.transport.bus.Bus;
import edu.davis.carbonflights.transport.car.Car;
import edu.davis.carbonflights.transport.flight.Flight;
import edu.davis.carbonflights.transport.train.Train;
import edu.davis.carbonflights.utility.Utils;

/**
 * Created by joey on 9/29/13.
 *
 * Estimates Activity:
 *
 * Takes in source and destination from Main Activity. Using these values, calculate
 * the carbon emissions of: an average flight between those two locations, an average
 * train ride, bus, and car. If these values are not available (if a route does not exist),
 * these values are left blank.
 *
 */
public class EstimatesActivity extends Activity implements View.OnClickListener,
        CM1.CM1Listener, Rome2Rio.R2RListener
{

    private CTTextView text_flight;
    private CTTextView text_train;
    private CTTextView text_bus;

    private CTTextView eqText_flight;
    private CTTextView eqText_train;
    private CTTextView eqText_bus;

    private CTTextView text_flightKG;
    private CTTextView text_trainKG;
    private CTTextView text_busKG;

    private CTTextView eq_flight;
    private CTTextView eq_train;
    private CTTextView eq_bus;

    private ProgressBar progress_flight;
    private ProgressBar progress_train;
    private ProgressBar progress_bus;

    private double gas_multiplier = 0.113;
    private double miles_driven_multiplier = 2.4;
    private double vehicle_multiplier = 0.0002;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimates);

        Utils.initActionBar(this, "Estimates", false);

        ((ImageView)findViewById(R.id.bg_image)).setImageResource(App.bg_id);

        text_flight = (CTTextView)findViewById(R.id.text_flight);
        text_train = (CTTextView)findViewById(R.id.text_train);
        text_bus = (CTTextView)findViewById(R.id.text_bus);

        eqText_flight=(CTTextView)findViewById(R.id.eqText_flight);
        eqText_train=(CTTextView)findViewById(R.id.eqText_train);
        eqText_bus=(CTTextView)findViewById(R.id.eqText_bus);

        text_flightKG = (CTTextView)findViewById(R.id.kg_flight);
        text_trainKG = (CTTextView)findViewById(R.id.kg_train);
        text_busKG = (CTTextView)findViewById(R.id.kg_bus);

        eq_flight = (CTTextView)findViewById(R.id.eq_flight);
        eq_train = (CTTextView)findViewById(R.id.eq_train);
        eq_bus = (CTTextView)findViewById(R.id.eq_bus);

        progress_flight = (ProgressBar)findViewById(R.id.progress_flight);
        progress_train = (ProgressBar)findViewById(R.id.progress_train);
        progress_bus = (ProgressBar)findViewById(R.id.progress_bus);

        findViewById(R.id.view_flight).setOnClickListener(this);
        findViewById(R.id.view_train).setOnClickListener(this);
        findViewById(R.id.view_bus).setOnClickListener(this);

        averages();
    }

    @Override
    public void onCM1Recieved(CM1 cm1, double carbon)
    {
        DecimalFormat dc = new DecimalFormat("#.##");
        Double finalCarbon = Double.valueOf(dc.format(carbon));
        Double gallonsOfGas = finalCarbon*gas_multiplier;
//        String finalGallons = dc.format(gallonsOfGas);
        String finalGallons = Integer.toString(gallonsOfGas.intValue());


        switch(cm1.type){
            case Flight.Type:
                text_flight.setText(finalCarbon.intValue()+"");
                eqText_flight.setText(finalGallons);

                progress_flight.setVisibility(View.GONE);

                text_flight.setVisibility(View.VISIBLE);
                text_flightKG.setVisibility(View.VISIBLE);
                eqText_flight.setVisibility(View.VISIBLE);
                eq_flight.setVisibility(View.VISIBLE);
                break;
            case Bus.Type:

                text_bus.setText(finalCarbon.intValue()+"");
                eqText_bus.setText(finalGallons);

                progress_bus.setVisibility(View.GONE);

                text_bus.setVisibility(View.VISIBLE);
                text_busKG.setVisibility(View.VISIBLE);
                eqText_bus.setVisibility(View.VISIBLE);
                eq_bus.setVisibility(View.VISIBLE);
                break;

            case Train.Type:

                text_train.setText(finalCarbon.intValue()+"");
                eqText_train.setText(finalGallons);

                progress_train.setVisibility(View.GONE);

                text_train.setVisibility(View.VISIBLE);
                text_trainKG.setVisibility(View.VISIBLE);
                eqText_train.setVisibility(View.VISIBLE);
                eq_train.setVisibility(View.VISIBLE);
                break;

            case Car.Type:
                break;
            default:
                Utils.log("Error in types");
        }
    }

    @Override
    public void onClick(View view)
    {
        int type = -1;
        switch (view.getId()) {
            case R.id.view_flight:
                type = Flight.Type;
                break;
            case R.id.view_bus:
                type = Bus.Type;
                break;
            case R.id.view_train:
                type = Train.Type;
                break;
        }

        startSearch(type, App.Results.source(), App.Results.destination());

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    /**
     * Starts searching in the background for routes from 'from' param to 'to' param.
     * Saves results in App.Results
     *
     * see Network.onR2RReceived() to see how it saves and starts carbon calculations
     * */
    public void startSearch(int transportType, String from, String to)
    {

        String flags="";
        App.Results.setTransportType(transportType);

        switch (transportType) {
            case Flight.Type:
                flags = Flight.flags;
                break;
            case Bus.Type:
                flags = Bus.flags;
                break;
            case Train.Type:
                flags = Train.flags;
                break;
            case Car.Type:
                flags = Car.flags;
        }

        HttpRequest.Parameters parameters = new HttpRequest.Parameters("oName", from)
                                                           .add("dName", to)
                                                           .add("flags", flags);
        HttpRequest request = new HttpRequest(HttpRequest.ROME2RIO_URL, parameters, new Rome2Rio(App.Network));
        App.Network.add(request);
        App.Network.dequeue();
    }

    @Override
    public void onR2RReceived(Rome2Rio rome2Rio, JSONArray routes, JSONArray airlines)
    {
        String distance="";
        HttpRequest.Parameters parameters;
        HttpRequest request = null;
        String vehicle="";
        double duration=0.0;

        try {

            /*If there are no results for bus and train*/
            if(routes.length()==0) {

                    progress_bus.setVisibility(View.GONE);
                    progress_train.setVisibility(View.GONE);
                    text_bus.setVisibility(View.VISIBLE);
                    text_bus.setText("No routes available");
                    text_train.setVisibility(View.VISIBLE);
                    text_train.setText("No routes available");

                return;

            }

            for (int i=0; i < routes.length(); i++){
                JSONObject route = (JSONObject)routes.get(i);
                distance = route.getString("distance");
                duration = Double.parseDouble(route.getString("duration"))*60;

                if(airlines.length()>0){
                    parameters = new HttpRequest.Parameters("distance_estimate", distance);
                    request = new HttpRequest(HttpRequest.CM1_FLIGHT_KEY, parameters, new CM1(this, Flight.Type));
                    request.execute();
                    return;
                }

                JSONArray segments = (JSONArray)route.getJSONArray("segments");

                for(int j=0;j<segments.length();j++){

                    JSONObject obj = (JSONObject)segments.get(j);
                    vehicle = obj.getString("vehicle");
                }
            }
        } catch(Exception e) {
            Utils.log("Error going through routes in R@R received");
            e.printStackTrace();
        }


        if(vehicle.equals("bus")) {
            parameters = new HttpRequest.Parameters("duration", Double.toString(duration));
            request = new HttpRequest(HttpRequest.CM1_BUS_KEY, parameters, new CM1(this, Bus.Type));
        }
        else if(vehicle.equals("car")){
            parameters = new HttpRequest.Parameters("distance", distance);
            request = new HttpRequest(HttpRequest.CM1_CAR_KEY, parameters, new CM1(this, Car.Type));

        }
        else if(vehicle.equals("BART")||vehicle.equals("train")||vehicle.equals("Metro")){
            parameters = new HttpRequest.Parameters("distance", distance);
            request = new HttpRequest(HttpRequest.CM1_TRAIN_KEY, parameters, new CM1(this, Train.Type));
        }
        else {
            Utils.log("Wrong vehicle type");
            return;
        }

        request.execute();
    }



    private void averages()
    {
        HttpRequest.Parameters parameters;
        HttpRequest request;

        String source = App.Results.source();
        String dest = App.Results.destination();

        //flight
            parameters = new HttpRequest.Parameters("oName", source)
                    .add("dName", dest)
                    .add("flags", Flight.flags);
            request = new HttpRequest(HttpRequest.ROME2RIO_URL, parameters, new Rome2Rio(this));
            request.execute();


        //train
        parameters = new HttpRequest.Parameters("oName", source)
                .add("dName", dest)
                .add("flags", "0x000FFF0F");
        request = new HttpRequest(HttpRequest.ROME2RIO_URL, parameters, new Rome2Rio(this));
        request.execute();

        //Bus
        parameters = new HttpRequest.Parameters("oName",source)
                .add("dName", dest)
                .add("flags", Bus.flags);
        request = new HttpRequest(HttpRequest.ROME2RIO_URL, parameters, new Rome2Rio(this));
        request.execute();

        //Car
        parameters = new HttpRequest.Parameters("oName",source)
                .add("dName", dest)
                .add("flags", Car.flags);
        request = new HttpRequest(HttpRequest.ROME2RIO_URL, parameters, new Rome2Rio(this));
        request.execute();
    }


} //End EstimatesActivity
