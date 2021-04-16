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
                .setDesiredAccuracy(10)  // <-- optional
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
        } else if (event.equals(BackgroundGeolocation.EVENT_LOCATION)) {
            // A location event has been received.
        }
        // Important: Be sure to execute #finish when your task is complete.  
        // This signals the native code that your task is complete.
        finish();
    }

}
