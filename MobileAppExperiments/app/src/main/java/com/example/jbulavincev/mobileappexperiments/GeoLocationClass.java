package com.example.jbulavincev.mobileappexperiments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by jbulavincev on 05.01.2015.
 */
public class GeoLocationClass {

   // private GoogleMapsActivity gmapactivity;
    private GoogleMap actualMap;
    private Marker workerMarker = null;
    private Context mContext;

    private static final String LOG_TAG = "GEOLocationClass" ;
    private static final String WORKER_CAPTION = "Worker";
    private static final String CUSTOMER_PREDICATE = "CUSTOMER";
    private static final String CUSTOMER_DIVIDER = "#";
    private static final String EXTRA_VAR_NAME = "WOPosition";
    private static final int ZOOM = 11;
    private static final int ACCURACY = 100; //meters
    private static final int LOCATION_UPDATE_TIME = 20000; //milliseconds

    public GeoLocationClass(Context mContext)
    {
        this.mContext = mContext;
    }


    public GeoLocationClass(Context mContext,GoogleMapsActivity gmapactivity, GoogleMap actualMap)
    {
        this.mContext = mContext;
        //this.gmapactivity = gmapactivity;
        this.actualMap = actualMap;
        workerMarker = actualMap.addMarker(new MarkerOptions()
                .position(new LatLng(getLocation()[0], getLocation()[1]))
                .title(WORKER_CAPTION)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(workerMarker.getPosition())
                .bearing(0)
                .zoom(ZOOM)
                .build();
        actualMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
        final Context initiateContext = this.mContext;

           /* actualMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    String title = arg0.getTitle();
                    Log.d(LOG_TAG, "itemClick: title = " + title);
                    if (title.compareToIgnoreCase(WORKER_CAPTION) != 0) {
                        String[] titleparts = title.split(CUSTOMER_DIVIDER);
                        String titleidpart = titleparts[1].trim();
                        Log.d(LOG_TAG, "itemClick: workorder id = " + titleidpart);
                        Intent myIntent = new Intent(initiateContext, WorkorderDetails.class);
                        myIntent.putExtra("WOPosition", (Integer.parseInt(titleidpart) - 1));
                        initiateContext.startActivity(myIntent);
                    }
                    return true;
                }
            });*/

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Log.d(LOG_TAG, "LatitudeUpdate: "+String.valueOf(location.getLatitude()));
            Log.d(LOG_TAG, "LongitudeUpdate: "+String.valueOf(location.getLongitude()));
            if (workerMarker != null )workerMarker.remove();
            workerMarker = actualMap.addMarker(new MarkerOptions()
                    .position(new LatLng(getLocation()[0], getLocation()[1]))
                    .title(WORKER_CAPTION)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            CameraPosition currentPlace = new CameraPosition.Builder()
                    .target(workerMarker.getPosition())
                    .bearing(0)
                    .zoom(ZOOM)
                    .build();
            actualMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
            Log.d(LOG_TAG, "Worker position updated");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(LOG_TAG, provider + " changed status to " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(LOG_TAG, provider + " changed status to enabled!");

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(LOG_TAG, provider + " changed status to disabled!");
        }
    }

    public Double[] getLocation()
    {
        Log.d(LOG_TAG, "getLocation method. ");
        Double[] Location = calculateLocationLatitude();
        return Location;
    }

    private Double[] calculateLocationLatitude()
    {
        LocationManager locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_TIME, ACCURACY, new MyLocationListener());
        Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(LOG_TAG, "Location: "+ location);
        return new Double[] {location.getLongitude(), location.getLatitude()};
    }

    public void addCustomerMarkers (String JSonString)
    {
        JSonParser parser = new JSonParser();
        ArrayList<WorkorderData> arrayOfwodata = parser.ParseJSonStringToWOArray(JSonString);
        for (int i=0; i<arrayOfwodata.size(); i++)
        {
            actualMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(arrayOfwodata.get(i).latitude), Double.parseDouble((arrayOfwodata.get(i).longitude))))
                    .title(CUSTOMER_PREDICATE + " " + CUSTOMER_DIVIDER + (i + 1))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }


}

