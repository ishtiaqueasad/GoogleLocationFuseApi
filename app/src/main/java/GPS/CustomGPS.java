package GPS;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by maya on 4/26/2016.
 */
public class CustomGPS implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Context context;
    private Observable<String> myObservable;
    private Subscriber<String> mySubscriber;

    public CustomGPS(Context contextClass, Subscriber<String> mysub) {
        this.context=contextClass;
        mySubscriber=mysub;

        //STEP 1: build the GOOGLE API Client
        buildGoogleApiClient();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }
    @Override
    public void onLocationChanged(final Location location)
    {

        myObservable=Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext(location.toString());
                    subscriber.onCompleted();
                }

            });
        myObservable.subscribe(mySubscriber);
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //STEP 2: AFTER ON CONNECT MAKE A LOCATION REQUEST
        locationRequest=LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);

        //Location location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }
    public synchronized void buildGoogleApiClient()
    {
        googleApiClient=new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

}
