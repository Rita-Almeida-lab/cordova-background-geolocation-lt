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

public class BackgroundGeolocationHeadlessTask extends HeadlessTask implements HeadlessTask.Receiver {
    /**
     * @param context
     * @param event [location|motionchange|providerchange|activitychange|http|heartbeat|geofence|schedule|boot|terminate
     * @param params Same params signtature provived to Javascript events.
     */
    @Override
    public void onReceive(Context context, String event, JSONObject params) {
        Log.d("MyApp", "BackgroundGeolocationHeadlessTask: " + params.toString());

        // You can get a reference to the BackgroundGeolocation native API like this:
        BackgroundGeolocation bgGeo = BackgroundGeolocation.getInstance(context);

        // Create custom logic based upon the received event
        if (event.equals(BackgroundGeolocation.EVENT_HEARTBEAT)) {
            // A heartbeat event has been received.
			HeadlessTask.postNotification(context, event, params);
			alert("in background");
        } else if (event.equals(BackgroundGeolocation.EVENT_LOCATION)) {
            // A location event has been received.
        }
        // Important: Be sure to execute #finish when your task is complete.  
        // This signals the native code that your task is complete.
        finish();
    }
}
