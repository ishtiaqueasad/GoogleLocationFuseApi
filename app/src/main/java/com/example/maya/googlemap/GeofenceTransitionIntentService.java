package com.example.maya.googlemap;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by maya on 4/26/2016.
 */
public class GeofenceTransitionIntentService extends IntentService {
    protected static final String TAG="TAG";
    List<Geofence> triggeringGeofences;
    public GeofenceTransitionIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        GeofencingEvent geofencingEvent= GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError())
        {
            String errorMessage= Integer.toString(geofencingEvent.getErrorCode());
            Log.e(TAG,errorMessage);
            return;
        }
        int geofenceTransition =geofencingEvent.getGeofenceTransition();
        if(geofenceTransition== Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition== Geofence.GEOFENCE_TRANSITION_EXIT)
        {
            triggeringGeofences= geofencingEvent.getTriggeringGeofences();
        }
        for(Geofence i: triggeringGeofences)
        {
            Log.e(TAG,i.getRequestId());
        }
    }
}
