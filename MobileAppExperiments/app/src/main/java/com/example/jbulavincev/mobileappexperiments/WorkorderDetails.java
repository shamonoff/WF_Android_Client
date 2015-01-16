package com.example.jbulavincev.mobileappexperiments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

class spinnerAdapter extends ArrayAdapter<String> {

    public spinnerAdapter(Context context, ArrayList<String> status) {
        super(context, 0, status);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // WorkorderData wodata = getItem(position);
        String status = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        // Lookup view for data population
        // Populate the data into the template view using the data object

        //if (!wodata.status.equals(null))

        // Return the completed view to render on screen
        return convertView;
    }
}

public class WorkorderDetails extends Activity {
       protected String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details);
        Bundle extras = getIntent().getExtras();
        Integer passedValue;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        String passedJsonValue;
        if (extras != null) {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            passedValue = extras.getInt("WOPosition");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
           // passedJsonValue = extras.getString("JSonString");
        }//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        else//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            passedValue = 999999;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
           // passedJsonValue = "NO VALUE";
        }//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Log.d("WODetails", "passed id value = " + passedValue);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Log.d("WODetails", "passed int value = " + passedValue);
       // Log.d("WODetails", "passed jsonstring value = " + passedJsonValue);
        //DBClass helper = new DBClass(WorkorderDetails.this); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        JSonParser parser = new JSonParser();
        FileRWClass filerwclass = new FileRWClass(WorkorderDetails.this);
        String JSonArrayString = filerwclass.readFromFile();
        WorkorderData certainwo;
        //if (passedValue != null)
                certainwo = parser.PasrseJSonStringToWO(parser.getCertainJsonArrayPrt(passedValue,JSonArrayString));
              //  else
        //certainwo = parser.PasrseJSonStringToWO(passedJsonValue); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        TextView woidtextview = (TextView) findViewById(R.id.textView3);//
        TextView workertextview = (TextView) findViewById(R.id.textView4);//
        TextView datetextview = (TextView) findViewById(R.id.dateView);//
        TextView enddateview = (TextView) findViewById(R.id.enddateView);//
        TextView addressview = (TextView) findViewById(R.id.addressView);//
        TextView latitudeview = (TextView) findViewById(R.id.latitudeView);//
        TextView stateview = (TextView) findViewById(R.id.stateView);//
        TextView typeview = (TextView) findViewById(R.id.typeView);//
        TextView startdateview = (TextView) findViewById(R.id.startdateView);//
        TextView uuidview = (TextView) findViewById(R.id.uuidView);//
        TextView statusview = (TextView) findViewById(R.id.statusViewDetails);//
        TextView customerview = (TextView) findViewById(R.id.customerView);//
        TextView longitudeview = (TextView) findViewById(R.id.longitudeView);//
        TextView crewview = (TextView) findViewById(R.id.crewView);//
        woidtextview.setText("WOID: " + certainwo.woid);
        Log.d("WODetails", "woidtextview");
        workertextview.setText("Worker: " + certainwo.worker);
        Log.d("WODetails", "workertextview");
        datetextview.setText("Date: " + certainwo.date);
        Log.d("WODetails", "datetextview");
        enddateview.setText("Enddate: " + certainwo.enddate);
        Log.d("WODetails", "enddateview");
       addressview.setText("Address: " + certainwo.address);
        Log.d("WODetails", "addressview");
        latitudeview.setText("Latitude: " + certainwo.latitude);
        Log.d("WODetails", "latitudeview");
        stateview.setText("State: " + certainwo.state);
        Log.d("WODetails", "stateview");
        typeview.setText("Type: " + certainwo.type);
        Log.d("WODetails", "typeview");
        startdateview.setText("Startdate: " + certainwo.startdate);
        Log.d("WODetails", "startdateview");
        uuidview.setText("Uuid: " + certainwo.uuid);
        Log.d("WODetails", "uuidview");
        statusview.setText("Status: " + certainwo.status);
        Log.d("WODetails", "statusview");
        longitudeview.setText("longitude: " + certainwo.longitude);
        Log.d("WODetails", "lnogitudeview");
        customerview.setText("Customer: " + certainwo.customer);
        Log.d("WODetails", "customerview");
        crewview.setText("Crew: " + certainwo.crew);
        Log.d("WODetails", "crew");
        Spinner statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        //Adapter spinneradapter = new yourAdapter(WorkorderDetails.this);
       ///? statusSpinner.setAdapter();
        String[] statuses = getResources().getStringArray(R.array.statuses_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, statuses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(dataAdapter);

        /*statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });*/
       // GeoLocation.getLocation();
        GoogleMapsActivity gmapactivity = new GoogleMapsActivity();
        GoogleMap actualMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
        GeoLocationClass GeoLocation = new GeoLocationClass(WorkorderDetails.this, gmapactivity, actualMap);
        actualMap.getUiSettings().setZoomControlsEnabled(true);
       // GoogleMap.InfoWindowAdapter infowindow = null;
        //actualMap.setInfoWindowAdapter(infowindow);
        Marker customerMarker = actualMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(certainwo.latitude), Double.parseDouble(certainwo.longitude)))
                .title("Customer"));
        //Marker customerMarker = gmapactivity.addMarker(actualMap, Double.parseDouble(certainwo.latitude), Double.parseDouble(certainwo.longitude), "Customer");
        //Marker workerMarker = gmapactivity.addMarker(actualMap, 55.777766, 37.520389999999966, "Worker");//FAKE WORKER LOCATION
     /*   Marker workerMarker = actualMap.addMarker(new MarkerOptions()
                .position(new LatLng(GeoLocation.getLocation()[0], GeoLocation.getLocation()[1]))
                .title("Worker")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/
        //Marker workerMarker = gmapactivity.addMarker(actualMap,  GeoLocation.getLocation()[0],  GeoLocation.getLocation()[1], "Worker");//FAKE WORKER LOCATION
       /* CameraPosition currentPlace = new CameraPosition.Builder()
                .target(customerMarker.getPosition())
                .bearing(0)
                .zoom(11)
                .build();
        actualMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));*/

        Button LocationButton = (Button) findViewById(R.id.testlocationbutton);
        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoLocationClass GeoLocation = new GeoLocationClass(WorkorderDetails.this);
                //GeoLocation.getLocation();
                Log.d("WODetails", "GEO Corrs = " + GeoLocation.getLocation()[0] + ";" + GeoLocation.getLocation()[1]);
            }
        });

        Button changeStatusButton = (Button) findViewById(R.id.changeStatusButton);
        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoLocationClass GeoLocation = new GeoLocationClass(WorkorderDetails.this);
                //GeoLocation.getLocation();
                Spinner statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
                Log.d("WODetails", "changeStatusButton pressed. new status = " + statusSpinner.getSelectedItem().toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workorder_details, menu);
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
