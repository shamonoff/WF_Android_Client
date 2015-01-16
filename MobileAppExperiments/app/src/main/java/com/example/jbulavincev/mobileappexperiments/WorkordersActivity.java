package com.example.jbulavincev.mobileappexperiments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

//import com.example.jbulavincev.mobileappexperiments.trash.WorkorderAdd;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


class yourAdapter extends ArrayAdapter<WorkorderData> {

    public yourAdapter(Context context, ArrayList<WorkorderData> wodata) {
        super(context, 0, wodata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkorderData wodata = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        TextView woid = (TextView) convertView.findViewById(R.id.orderView);
        TextView worker = (TextView) convertView.findViewById(R.id.workerView);
        TextView date = (TextView) convertView.findViewById(R.id.dateView);
        TextView status  = (TextView) convertView.findViewById(R.id.statusViewDetails);
        date.setText(wodata.date);
        worker.setText("Customer:" + wodata.customer);
        woid.setText("OrderID:" + wodata.woid);
        status.setText(wodata.status);
        if ((wodata.status.compareToIgnoreCase("opened") == 0)) status.setTextColor(Color.GRAY);
            else if ((wodata.status.compareToIgnoreCase("in progress") == 0)) status.setTextColor(Color.GREEN);
        return convertView;
    }
}

public class WorkordersActivity extends Activity {

    private yourAdapter adapter;
    ArrayList<WorkorderData> arrayOfwodata;
    JSonParser parser = new JSonParser();
    String WSJSonResponse;
    final DBClass db = new DBClass(WorkordersActivity.this);
    private static final String EXTRA_VAR_NAME = "WOPosition";
    private static final String LOG_TAG = "WordordersActivity" ;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(LOG_TAG,"Start onActivityResult check...");
        if (requestCode == 1) {
            Log.d(LOG_TAG,"requestCode == 1");
            if(resultCode == RESULT_OK){
                Log.d(LOG_TAG,"resultCode == RESULT_OK");
                String result=data.getStringExtra("result");
                Log.d(LOG_TAG,"result == "+result);
                if (result.equals("WOCreateRequestDone"))
                {
                    Log.d(LOG_TAG,"notifyDataSetChanged...");
                   // adapter.add(db.getLastWOItem());
                    NotifyAdapter();
                 //   REFRESH WORKLIST TO GET NEW WO
                }
            }
            if (resultCode == RESULT_CANCELED) {
                Log.d(LOG_TAG,"resultCode == RESULT_CANCELED");
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordorders);
        // Construct the data source
        //db.reloadDatabase();
        //FAKE DATA
        // populateWOArray(arrayOfwodata);
        //populateWOArrayFromWS(arrayOfwodata);
        WSJSonResponse = null;
        Log.d(LOG_TAG, "Starting WS interaction");
        try { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            WSJSonResponse = new RetrieveArrayTask(WorkordersActivity.this).execute().get();
            if ((WSJSonResponse.compareToIgnoreCase("html error") == 0)|| (WSJSonResponse.compareToIgnoreCase("connection error") == 0)) {
                TextView tview = (TextView) findViewById(R.id.textView);
                if (WSJSonResponse.compareToIgnoreCase("html error") == 0)  tview.setText("Not enough privelegies. Relogin required.");
                    else tview.setText("Connection problems or server down. Relogin required.");
                Button reloginButton = (Button) findViewById(R.id.reloginButton);
                ImageButton refreshButton = (ImageButton) findViewById(R.id.refreshButton);
                //refreshButton.setVisibility(View.INVISIBLE);
                reloginButton.setVisibility(View.VISIBLE);
                reloginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //GeoLocationClass GeoLocation = new GeoLocationClass(WorkorderDetails.this);
                       // reloginButton.setVisibility(View.INVISIBLE);
                        //GeoLocation.getLocation();
                        Intent myIntent = new Intent(WorkordersActivity.this, LoginActivity.class);
                      //  String JSonString = parser.getCertainJsonArrayPrt(position, WSJSonResponse);
                       // myIntent.putExtra("WOPosition", position);
                        //myIntent.putExtra("JSonString", JSonString);
                        WorkordersActivity.this.startActivity(myIntent);
                        Log.d(LOG_TAG, "Relogin required");
                        finish();
                    }
                });
            }
            else {
                Log.d(LOG_TAG, "WSResponse = " + WSJSonResponse);
                FileRWClass filerwclass = new FileRWClass(WorkordersActivity.this);
                filerwclass.writeToFile(WSJSonResponse);
                Log.d(LOG_TAG, "WSJSonResponse written to file woarray.txt");
                //FAKE DATA END
                //POPULATE DB WITH FAKE DATA
                arrayOfwodata = parser.ParseJSonStringToWOArray(WSJSonResponse); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                Log.d(LOG_TAG, "arrayOfwodata = " + arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //db.populateDatabaseWithArray(arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //db.printAllWOData(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //POPULATE DB WITH FAKE DATA END
                //GET FAKE DATA FROM DB
                //ArrayList<WorkorderData> arrayOfwodataFromDb = db.getWOData();
                //GET FAKE DATA FROM DB END

// Create the adapter to convert the array to views
                adapter = new yourAdapter(this, arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //adapter.clear();
                //adapter.addAll(arrayOfwodata);
// Attach the adapter to a ListView
                ListView listView = (ListView) findViewById(R.id.listView); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                listView.setAdapter(adapter); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                NotifyAdapter();
                TextView tview = (TextView) findViewById(R.id.textView);
                tview.setText("Workorders");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Log.d(LOG_TAG, "itemClick: position = " + position + ", id = " + id);
                        Intent myIntent = new Intent(WorkordersActivity.this, WorkorderDetails.class);
                        String JSonString = parser.getCertainJsonArrayPrt(position, WSJSonResponse);
                        myIntent.putExtra("WOPosition", position);
                        //myIntent.putExtra("JSonString", JSonString);
                        WorkordersActivity.this.startActivity(myIntent);
                    }
                });
            }
            }
         catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /*ImageButton addWOButton = (ImageButton) findViewById(R.id.addWOButton);
        addWOButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "addWOButton click");
                Intent myIntent = new Intent(WordordersActivity.this, WorkorderAdd.class);
                //myIntent.putExtra("WOPosition", position);
                WordordersActivity.this.startActivityForResult(myIntent, 1);
            }
        });*/


       /* ImageButton localrefreshButton = (ImageButton) findViewById(R.id.localrefreshButton);
        localrefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "localrefreshButton click");
                //for (int i=)
               // arrayOfwodata
            }
        }); */

        ImageButton refreshButton = (ImageButton) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "refreshButton click");
                //db.reloadDatabase();
                JSonParser parser = new JSonParser();
                String WSJSonResponse = "NULL";
                Log.d(LOG_TAG, "Starting WS interaction");
             /*   try {
                    WSJSonResponse = new RetrieveArrayTask(WorkordersActivity.this).execute().get();
                    if (WSJSonResponse.compareToIgnoreCase("html error") == 0) {
                        TextView tview = (TextView) findViewById(R.id.textView);
                        tview.setText("CONNECTION ERROR NOT ENOUGH PRIVELEGIES");
                    }
                    else {
                        Log.d(LOG_TAG, "arrayOfwodata length before clearing: " + arrayOfwodata.size());
                        //arrayOfwodata.removeAll(arrayOfwodata);
                        adapter.clear();
                        arrayOfwodata = parser.ParseJSonStringToWOArray(WSJSonResponse);
                        adapter.addAll(arrayOfwodata);
                        Log.d(LOG_TAG, "arrayOfwodata = " + arrayOfwodata);
                        Log.d(LOG_TAG, "arrayOfwodata length after clearing: " + arrayOfwodata.size());
                        Log.d(LOG_TAG, "adapter count = " + adapter.getCount());
                        //for (int i=0; i<arrayOfwodata.size(); i++)
                        //   adapter.add(arrayOfwodata.get(i));
                        //db.populateDatabaseWithArray(arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        // db.printAllWOData(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        NotifyAdapter();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    TextView tview = (TextView) findViewById(R.id.textView);
                    tview.setText("NOT ENOUGH PRIVELEGIES");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    TextView tview = (TextView) findViewById(R.id.textView);
                    tview.setText("NOT ENOUGH PRIVELEGIES");
                }*/
                try { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    WSJSonResponse = new RetrieveArrayTask(WorkordersActivity.this).execute().get();
                    if ((WSJSonResponse.compareToIgnoreCase("html error") == 0)|| (WSJSonResponse.compareToIgnoreCase("connection error") == 0)) {
                        TextView tview = (TextView) findViewById(R.id.textView);
                        if (WSJSonResponse.compareToIgnoreCase("html error") == 0)  tview.setText("Not enough privelegies. Relogin required.");
                        else tview.setText("Connection problems or server down. Relogin required.");
                        Button reloginButton = (Button) findViewById(R.id.reloginButton);
                        ImageButton refreshButton = (ImageButton) findViewById(R.id.refreshButton);
                        //refreshButton.setVisibility(View.INVISIBLE);
                        reloginButton.setVisibility(View.VISIBLE);
                        reloginButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //GeoLocationClass GeoLocation = new GeoLocationClass(WorkorderDetails.this);
                                // reloginButton.setVisibility(View.INVISIBLE);
                                //GeoLocation.getLocation();
                                Intent myIntent = new Intent(WorkordersActivity.this, LoginActivity.class);
                                //  String JSonString = parser.getCertainJsonArrayPrt(position, WSJSonResponse);
                                // myIntent.putExtra("WOPosition", position);
                                //myIntent.putExtra("JSonString", JSonString);
                                WorkordersActivity.this.startActivity(myIntent);
                                Log.d(LOG_TAG, "Relogin required");
                                finish();
                            }
                        });
                    }
                    else {
                        Log.d(LOG_TAG, "WSResponse = " + WSJSonResponse);
                        FileRWClass filerwclass = new FileRWClass(WorkordersActivity.this);
                        filerwclass.writeToFile(WSJSonResponse);
                        Log.d(LOG_TAG, "WSJSonResponse written to file woarray.txt");
                        //FAKE DATA END
                        //POPULATE DB WITH FAKE DATA
                        arrayOfwodata = parser.ParseJSonStringToWOArray(WSJSonResponse); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        Log.d(LOG_TAG, "arrayOfwodata = " + arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //db.populateDatabaseWithArray(arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //db.printAllWOData(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //POPULATE DB WITH FAKE DATA END
                        //GET FAKE DATA FROM DB
                        //ArrayList<WorkorderData> arrayOfwodataFromDb = db.getWOData();
                        //GET FAKE DATA FROM DB END

// Create the adapter to convert the array to views
                        adapter = new yourAdapter(getBaseContext(), arrayOfwodata); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //adapter.clear();
                        //adapter.addAll(arrayOfwodata);
// Attach the adapter to a ListView
                        ListView listView = (ListView) findViewById(R.id.listView); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        listView.setAdapter(adapter); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        NotifyAdapter();
                        TextView tview = (TextView) findViewById(R.id.textView);
                        tview.setText("Workorders");
                        final String initiateResponse = WSJSonResponse;
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = " + id);
                                Intent myIntent = new Intent(WorkordersActivity.this, WorkorderDetails.class);
                                JSonParser parser = new JSonParser();
                                String JSonString = parser.getCertainJsonArrayPrt(position, initiateResponse);
                                myIntent.putExtra("WOPosition", position);
                                //myIntent.putExtra("JSonString", JSonString);
                                WorkordersActivity.this.startActivity(myIntent);
                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                //arrayOfwodata.clear(); //FAIL FAIL FAIL

                //   REFRESH WORKLIST TO GET NEW WO
            };
        });
    }
        // Add item to adapter


    private void NotifyAdapter ()
    {
        Runnable NotifyAdapter = new Runnable(){
            public void run(){
                //reload content
                adapter.notifyDataSetChanged();
            }

        };
        this.runOnUiThread(NotifyAdapter);
     }
// Or even append an entire new collection
// Fetching some data, data has now returned
// If data was JSON, convert to ArrayList of User objects.
      //  JSONArray jsonArray = ...;
      //  ArrayList<User> newUsers = User.fromJson(jsonArray)
      //  adapter.addAll(newUsers);
    //}

   /* protected void populateWOArray (ArrayList<WorkorderData> WOArray)
    {
        WorkorderData newData = new WorkorderData("10.10.2014", "Vasiliy Vasiliev", "C11213");
        WOArray.add(newData);
        WorkorderData newData2 = new WorkorderData("10.20.2014", "Vladimir J", "A133213");
        WOArray.add(newData2);
        WorkorderData newData3 = new WorkorderData("10.30.2014", "Sergey Ivanov", "D11213");
        WOArray.add(newData3);
        WorkorderData newData4 = new WorkorderData("10.30.2004", "Ivan Ivanov", "B11213");
        WOArray.add(newData4);
        newData4 = new WorkorderData("10.30.2004", "Ivan Ivanov", "sa11213");
        WOArray.add(newData4);
        newData4 = new WorkorderData("10.30.2004", "Ivan Ivanov", "m11213");
        WOArray.add(newData4);
        newData4 = new WorkorderData("10.30.2004", "Ivan Ivanov", "l11213");
        WOArray.add(newData4);
    }*/

    private static final String HOST_NAME = "http://ichigo.dyndns-work.com:7001/WorkforceManagementMobile/GetWorkOrders.jsp";
    private static final String URL = "http://ichigo.dyndns-work.com:7001/WorkforceManagementMobile/GetWorkOrders.jsp";
    private static final String USER_NAME = "operator";
    private static final String PASSWORD = "operator";
    protected void populateWOArrayFromWS (ArrayList<WorkorderData> WOArray)
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    AuthScope.ANY /*new AuthScope(HOST_NAME, 7001)*/,
                    new UsernamePasswordCredentials(USER_NAME, PASSWORD));

            HttpGet httpget = new HttpGet(URL);

            System.out.println("executing request" + httpget.getRequestLine());
            Log.d("JSON", "executing request: " + httpget.getRequestLine());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                Log.d("JSON", "Response content length: " + entity.getContentLength());
                Log.d("JSON", "EntityUtils.toString(entity): " + EntityUtils.toString(entity));
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("JSON", "Empty response or error");
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wordorders, menu);
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


