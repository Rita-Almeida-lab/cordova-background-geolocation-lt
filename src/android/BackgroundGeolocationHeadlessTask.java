package com.transistorsoft.cordova.bggeo;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import com.transistorsoft.locationmanager.adapter.BackgroundGeolocation;
import com.transistorsoft.locationmanager.adapter.callback.TSLocationCallback;
import com.transistorsoft.locationmanager.event.ActivityChangeEvent;
import com.transistorsoft.locationmanager.event.GeofenceEvent;
import com.transistorsoft.locationmanager.event.GeofencesChangeEvent;
import com.transistorsoft.locationmanager.event.ConnectivityChangeEvent;
import com.transistorsoft.locationmanager.event.HeadlessEvent;
import com.transistorsoft.locationmanager.event.HeartbeatEvent;
import com.transistorsoft.locationmanager.event.MotionChangeEvent;
import com.transistorsoft.locationmanager.event.LocationProviderChangeEvent;
import com.transistorsoft.locationmanager.http.HttpResponse;
import com.transistorsoft.locationmanager.location.TSCurrentPositionRequest;
import com.transistorsoft.locationmanager.location.TSLocation;
import com.transistorsoft.locationmanager.logger.TSLog;

public class BackgroundGeolocationHeadlessTask  {

    @Subscribe
    public void onHeadlessTask(HeadlessEvent event) {

        String name = event.getName();
        TSLog.logger.debug("\uD83D\uDC80  event (CUSTOM IMPLEMENTATION): " + event.getName());
        TSLog.logger.debug("- event: " + event.getEvent());

        if (name.equals(BackgroundGeolocation.EVENT_HEARTBEAT)) {
            // [Optional] Build extras object.
            JSONObject extras = new JSONObject();
            try {
                extras.put("foo", "bar");
            } catch (JSONException e) {
                // Won't happen.
            }

            // Build request object.
            TSCurrentPositionRequest.Builder request = new TSCurrentPositionRequest.Builder(event.getContext())
                .setPersist(true)  // <-- optional
                .setMaximumAge(0)  // <-- optional
                .setExtras(extras) // <-- optional 
                .setTimeout(60)    // <-- optional
                .setSamples(3)     // <-- optional 
                //.setDesiredAccuracy(10)  // <-- optional
                .setCallback(new TSLocationCallback() {
                    @Override
                    public void onLocation(TSLocation tsLocation) {
                        // Location received callback.
                        TSLog.logger.debug("*** Location received: " + tsLocation.toString());      
                    }
                    @Override
                    public void onError(Integer error) {
                        // Location error callback.
                        TSLog.logger.error("*** getCurrentPosition ERROR: " + error.toString());
                    }
                });

            // Initiate the request.
            BackgroundGeolocation.getInstance(event.getContext()).getCurrentPosition(request.build());  

            HeartbeatEvent heartbeatEvent = event.getHeartbeatEvent();
        }
    }
}
