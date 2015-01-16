package com.example.jbulavincev.mobileappexperiments;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity {

    private final static String LOG_TAG="MapActivity";
    private static final String CUSTOMER_PREDICATE = "CUSTOMER";
    private static final String CUSTOMER_DIVIDER = "#";
    private static final String WORKER_CAPTION = "Worker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
       /* Bundle extras = getIntent().getExtras();
        String passedJsonValue;
      //  TextView textview = new TextView(this);
        if (extras != null) {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //  passedValue = extras.getInt("WOPosition");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            passedJsonValue = extras.getString("JSonString");
        }//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        else//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //  passedValue = 999999;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            passedJsonValue = "NO VALUE";
        }//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       // textview.setText("LOL2");*/
        GoogleMapsActivity gmapactivity = new GoogleMapsActivity();
        GoogleMap actualMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapViewMapActivity)).getMap();
        actualMap.getUiSettings().setZoomControlsEnabled(true);
        GeoLocationClass GeoLocation = new GeoLocationClass(MapActivity.this, gmapactivity, actualMap);
        FileRWClass filerwclass = new FileRWClass(MapActivity.this);
        String JSonArrayString = filerwclass.readFromFile();
        Log.d(LOG_TAG, "Read from file: "+JSonArrayString);
        GeoLocation.addCustomerMarkers(JSonArrayString);
         actualMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    String title = arg0.getTitle();
                    Log.d(LOG_TAG, "itemClick: title = " + title);
                    if (title.compareToIgnoreCase(WORKER_CAPTION) != 0) {
                        String[] titleparts = title.split(CUSTOMER_DIVIDER);
                        String titleidpart = titleparts[1].trim();
                        Log.d(LOG_TAG, "itemClick: workorder id = " + titleidpart);
                        Intent myIntent = new Intent(MapActivity.this, WorkorderDetails.class);
                        myIntent.putExtra("WOPosition", (Integer.parseInt(titleidpart) - 1));
                        MapActivity.this.startActivity(myIntent);
                    }
                    return true;
                }
            });
       // DBClass helper = new DBClass(MapActivity.this);
       // WorkorderData certainwo;
       // for (int i=0; i<helper.getRowsCount(); i++) {
       //     certainwo = helper.getCertainWOById(i + 1);
       //     Marker certainMarker = gmapactivity.addMarker(actualMap, Double.parseDouble(certainwo.latitude), Double.parseDouble(certainwo.longitude), "Customer number #"+(i+1));
       // }
       /* Marker workerMarker = actualMap.addMarker(new MarkerOptions()
                .position(new LatLng(GeoLocation.getLocation()[0], GeoLocation.getLocation()[1]))
                .title("Worker")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(workerMarker.getPosition())
                .bearing(0)
                .zoom(11)
                .build();
        actualMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));*/

    }

   /* @Override
    protected void onResume() {
        GoogleMapsActivity gmapactivity = new GoogleMapsActivity();
        GoogleMap actualMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapViewMapActivity)).getMap();
        GeoLocationClass GeoLocation = new GeoLocationClass(MapActivity.this,gmapactivity,actualMap );
        actualMap.clear();
        actualMap.getUiSettings().setZoomControlsEnabled(true);
        // DBClass helper = new DBClass(MapActivity.this);
        WorkorderData certainwo;
        // for (int i=0; i<helper.getRowsCount(); i++) {
        //     certainwo = helper.getCertainWOById(i + 1);
        //     Marker certainMarker = gmapactivity.addMarker(actualMap, Double.parseDouble(certainwo.latitude), Double.parseDouble(certainwo.longitude), "Customer number #"+(i+1));
        // }
        Marker workerMarker = actualMap.addMarker(new MarkerOptions()
                .position(new LatLng(GeoLocation.getLocation()[0], GeoLocation.getLocation()[1]))
                .title("Worker")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(workerMarker.getPosition())
                .bearing(0)
                .zoom(11)
                .build();
        actualMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
        super.onResume();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accounts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
