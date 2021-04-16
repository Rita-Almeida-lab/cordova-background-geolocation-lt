package com.transistorsoft.cordova.bggeo;

import android.content.Context;
import android.util.Log;
import org.json.JSONObject;
import com.transistorsoft.locationmanager.adapter.BackgroundGeolocation;

/**
 * BackgroundGeolocationHeadlessTask
 * This component allows you to receive events from the BackgroundGeolocation plugin 
 * in the native Android environment while your app has been *terminated*,
 */
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
        } else if (event.equals(BackgroundGeolocation.EVENT_LOCATION)) {
            // A location event has been received.
        }
        // Important: Be sure to execute #finish when your task is complete.  
        // This signals the native code that your task is complete.
        finish();
    }
	TSCurrentPositionRequest request = new TSCurrentPositionRequest.Builder(event.getContext())
                    .setPersist(true)       // <-- yes, persist to database
                    .setSamples(1)          // <-- fetch 1 location sample
                    .setCallback(new TSLocationCallback() {
                        @Override
                        public void onLocation(TSLocation tsLocation) {
                            Log.i(TAG, "[current position] success: " + tsLocation.toJson());
                        }
                        @Override
                        public void onError(Integer error) {
                            Log.i(TAG, "[current position] failure: " + error);
                        }
                    })
                    .build();

                BackgroundGeolocation bgGeo = BackgroundGeolocation.getInstance(event.getContext());
                bgGeo.getCurrentPosition(request);
}
