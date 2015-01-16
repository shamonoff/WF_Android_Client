package com.example.jbulavincev.mobileappexperiments;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jbulavincev on 07.01.2015.
 */
public class JSonParser {

    public JSonParser() {

    }


    private static final String TAG_WORKORDERS = "workorder";
    private static final String TAG_ENDDATE = "enddate";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_STATE = "state";
    private static final String TAG_TYPE = "type";
    private static final String TAG_STARTDATE = "startdate";
    private static final String TAG_UUID = "uuid";
    private static final String TAG_STATUS = "status";
    private static final String TAG_CUSTOMER = "customer";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_CREW = "crew";
    private static final String TAG_JSESSIONID = "JSESSIONID";
    private static final String TAG_RESULT = "RESULT";
    private static final String LOG_TAG = "JSonParser";
    private static final String NULL_VALUE = "JSonParser null value";

    private enum WOTAGS {TAG_ENDDATE, TAG_ADDRESS, TAG_LATITUDE, TAG_STATE, TAG_TYPE, TAG_STARTDATE, TAG_UUID,
        TAG_STATUS, TAG_CUSTOMER, TAG_LONGITUDE, TAG_CREW} //except TAG_WORKORDERS because it is the root one

    private enum CREDTAGS {TAG_JSESSIONID, TAG_RESULT}

    public String getCertainJsonArrayPrt (int i, String jsonStr)
    {
        JSONObject currentWO = null;
        if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                Log.d(LOG_TAG, "JSonString:" + jsonStr);
                Log.d(LOG_TAG, "JSonArrayLength:" + jsonArray.length());
                currentWO = jsonArray.getJSONObject(i);
                }
      catch (JSONException e) {
                e.printStackTrace();
            }

            finally {
                Log.d(LOG_TAG, "Certain jsonstring constructed: "+ currentWO.toString());
            }
        } else {
            Log.e(LOG_TAG, "Couldn't get any data from the url");
        }

        return currentWO.toString();
    }

    public WorkorderData PasrseJSonStringToWO (String jsonStr) {

        WorkorderData WOData = new WorkorderData();

        if (jsonStr != null) {
            try {
                JSONObject currentWO = new JSONObject(jsonStr);
                Log.d(LOG_TAG, "JSonString:" + jsonStr);
                    JSONObject currentWOData = currentWO.getJSONObject(TAG_WORKORDERS);
                    /*String latitudeNode = !currentWOData.getString(TAG_LATITUDE).isEmpty() ? currentWOData.getString(TAG_LATITUDE) : NULL_VALUE;
                    String enddateNode = !currentWOData.getString(TAG_ENDDATE).isEmpty() ? currentWOData.getString(TAG_ENDDATE) : NULL_VALUE;
                    String addressNode = !currentWOData.getString(TAG_ADDRESS).isEmpty() ? currentWOData.getString(TAG_ADDRESS) : NULL_VALUE;
                    String stateNode = !currentWOData.getString(TAG_STATE).isEmpty() ? currentWOData.getString(TAG_STATE) : NULL_VALUE;
                    String typeNode = !currentWOData.getString(TAG_TYPE).isEmpty() ? currentWOData.getString(TAG_TYPE) : NULL_VALUE;
                    String startdateNode = !currentWOData.getString(TAG_STARTDATE).isEmpty() ? currentWOData.getString(TAG_STARTDATE) : NULL_VALUE;
                    String uuidNode = !currentWOData.getString(TAG_UUID).isEmpty() ? currentWOData.getString(TAG_UUID) : NULL_VALUE;
                   // String statusNode = !currentWOData.getString(TAG_STATUS).isEmpty() ? currentWOData.getString(TAG_STATUS) : NULL_VALUE;
                    String customerNode = !currentWOData.getString(TAG_CUSTOMER).isEmpty() ? currentWOData.getString(TAG_CUSTOMER) : NULL_VALUE;
                    String longitudeNode = !currentWOData.getString(TAG_LONGITUDE).isEmpty() ? currentWOData.getString(TAG_LONGITUDE) : NULL_VALUE;
                    String crewNode = !currentWOData.getString(TAG_CREW).isEmpty() ? currentWOData.getString(TAG_CREW) : NULL_VALUE;*/
                String latitudeNode = "no value";
                if (!currentWOData.isNull( TAG_LATITUDE )) latitudeNode = !currentWOData.getString(TAG_LATITUDE).isEmpty() ? currentWOData.getString(TAG_LATITUDE) : NULL_VALUE;
                String enddateNode = "no value";
                if (!currentWOData.isNull( TAG_ENDDATE )) enddateNode = !currentWOData.getString(TAG_ENDDATE).isEmpty() ? currentWOData.getString(TAG_ENDDATE) : NULL_VALUE;
                String addressNode = "no value";
                if (!currentWOData.isNull( TAG_ADDRESS ))  addressNode = !currentWOData.getString(TAG_ADDRESS).isEmpty() ? currentWOData.getString(TAG_ADDRESS) : NULL_VALUE;
                String stateNode = "no value";
                if (!currentWOData.isNull( TAG_STATE ))  stateNode = !currentWOData.getString(TAG_STATE).isEmpty() ? currentWOData.getString(TAG_STATE) : NULL_VALUE;
                String typeNode = "no value";
                if (!currentWOData.isNull( TAG_TYPE ))  typeNode = !currentWOData.getString(TAG_TYPE).isEmpty() ? currentWOData.getString(TAG_TYPE) : NULL_VALUE;
                String startdateNode = "no value";
                if (!currentWOData.isNull( TAG_STARTDATE ))  startdateNode = !currentWOData.getString(TAG_STARTDATE).isEmpty() ? currentWOData.getString(TAG_STARTDATE) : NULL_VALUE;
                String uuidNode = "no value";
                if (!currentWOData.isNull( TAG_UUID ))  uuidNode = !currentWOData.getString(TAG_UUID).isEmpty() ? currentWOData.getString(TAG_UUID) : NULL_VALUE;
                String statusNode = "no value";
                if (!currentWOData.isNull( TAG_STATUS ))  statusNode = !currentWOData.getString(TAG_STATUS).isEmpty() ? currentWOData.getString(TAG_STATUS) : NULL_VALUE;
                String customerNode = "no value";
                if (!currentWOData.isNull( TAG_CUSTOMER ))  customerNode = !currentWOData.getString(TAG_CUSTOMER).isEmpty() ? currentWOData.getString(TAG_CUSTOMER) : NULL_VALUE;
                String longitudeNode = "no value";
                if (!currentWOData.isNull( TAG_LONGITUDE ))  longitudeNode = !currentWOData.getString(TAG_LONGITUDE).isEmpty() ? currentWOData.getString(TAG_LONGITUDE) : NULL_VALUE;
                String crewNode = "no value";
                if (!currentWOData.isNull( TAG_CREW ))  crewNode = !currentWOData.getString(TAG_CREW).isEmpty() ? currentWOData.getString(TAG_CREW) : NULL_VALUE;

                WOData.setEnddate(enddateNode);
                    WOData.setLatitude(latitudeNode);
                    WOData.setAddress(addressNode);
                    WOData.setState(stateNode);
                    WOData.setType(typeNode);
                    WOData.setStartdate(startdateNode);
                    WOData.setUuid(uuidNode);
                  //  WOData.setStatus(statusNode);
                    WOData.setCustomer(customerNode);
                    WOData.setLongitude(longitudeNode);
                    WOData.setCrew(crewNode);
                    WOData.setWOId(uuidNode);
                    WOData.setWorker(crewNode);
                    WOData.setDate(startdateNode);
                } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                Log.d(LOG_TAG, "WOData constructed.");
            }
        } else {
            Log.e(LOG_TAG, "Couldn't get any data from the url");
        }

        return WOData;

    }

    public String PasrseJSonStringToJsessionId (String jsonStr) {

        String jsessionid = null;

        if (jsonStr != null) {
            try {
                JSONObject currentjsonstring = new JSONObject(jsonStr);
                Log.d(LOG_TAG, "JSonString:" + jsonStr);
                jsessionid = !currentjsonstring.getString(TAG_JSESSIONID).isEmpty() ? currentjsonstring.getString(TAG_JSESSIONID) : NULL_VALUE;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                Log.d(LOG_TAG, "String jsessionid constructed: "+jsessionid);
            }
        } else {
            Log.e(LOG_TAG, "Couldn't get any data from the url");
        }

        return jsessionid;

    }

    public String PasrseJSonStringToResultCode (String jsonStr) {

        String result = null;

        if (jsonStr != null) {
            try {
                JSONObject currentjsonstring = new JSONObject(jsonStr);
                Log.d(LOG_TAG, "JSonString:" + jsonStr);
                result = !currentjsonstring.getString(TAG_RESULT).isEmpty() ? currentjsonstring.getString(TAG_RESULT) : NULL_VALUE;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                Log.d(LOG_TAG, "String result constructed: "+result);
            }
        } else {
            Log.e(LOG_TAG, "Couldn't get any data from the url");
        }

        return result;

    }


    public ArrayList<WorkorderData> ParseJSonStringToWOArray(String jsonStr) {

        ArrayList<WorkorderData> WOArray = new ArrayList<WorkorderData>();

        if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                Log.d(LOG_TAG, "JSonString:" + jsonStr);
                Log.d(LOG_TAG, "JSonArrayLength:" + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    WorkorderData WOData = new WorkorderData();
                    JSONObject currentWO = jsonArray.getJSONObject(i);
                    Log.d(LOG_TAG, "Looking for "+i+" JSON object:" + currentWO);
                        JSONObject currentWOData = currentWO.getJSONObject(TAG_WORKORDERS);
                        Log.d(LOG_TAG, "Looking for "+i+" JSON object parts:" + currentWOData);
                    String latitudeNode = "no value";
                        if (!currentWOData.isNull( TAG_LATITUDE )) latitudeNode = !currentWOData.getString(TAG_LATITUDE).isEmpty() ? currentWOData.getString(TAG_LATITUDE) : NULL_VALUE;
                    String enddateNode = "no value";
                    if (!currentWOData.isNull( TAG_ENDDATE )) enddateNode = !currentWOData.getString(TAG_ENDDATE).isEmpty() ? currentWOData.getString(TAG_ENDDATE) : NULL_VALUE;
                    String addressNode = "no value";
                    if (!currentWOData.isNull( TAG_ADDRESS ))  addressNode = !currentWOData.getString(TAG_ADDRESS).isEmpty() ? currentWOData.getString(TAG_ADDRESS) : NULL_VALUE;
                    String stateNode = "no value";
                    if (!currentWOData.isNull( TAG_STATE ))  stateNode = !currentWOData.getString(TAG_STATE).isEmpty() ? currentWOData.getString(TAG_STATE) : NULL_VALUE;
                    String typeNode = "no value";
                    if (!currentWOData.isNull( TAG_TYPE ))  typeNode = !currentWOData.getString(TAG_TYPE).isEmpty() ? currentWOData.getString(TAG_TYPE) : NULL_VALUE;
                    String startdateNode = "no value";
                    if (!currentWOData.isNull( TAG_STARTDATE ))  startdateNode = !currentWOData.getString(TAG_STARTDATE).isEmpty() ? currentWOData.getString(TAG_STARTDATE) : NULL_VALUE;
                    String uuidNode = "no value";
                    if (!currentWOData.isNull( TAG_UUID ))  uuidNode = !currentWOData.getString(TAG_UUID).isEmpty() ? currentWOData.getString(TAG_UUID) : NULL_VALUE;
                    String statusNode = "no value";
                    if (!currentWOData.isNull( TAG_STATUS ))  statusNode = !currentWOData.getString(TAG_STATUS).isEmpty() ? currentWOData.getString(TAG_STATUS) : NULL_VALUE;
                    String customerNode = "no value";
                    if (!currentWOData.isNull( TAG_CUSTOMER ))  customerNode = !currentWOData.getString(TAG_CUSTOMER).isEmpty() ? currentWOData.getString(TAG_CUSTOMER) : NULL_VALUE;
                    String longitudeNode = "no value";
                    if (!currentWOData.isNull( TAG_LONGITUDE ))  longitudeNode = !currentWOData.getString(TAG_LONGITUDE).isEmpty() ? currentWOData.getString(TAG_LONGITUDE) : NULL_VALUE;
                    String crewNode = "no value";
                    if (!currentWOData.isNull( TAG_CREW ))  crewNode = !currentWOData.getString(TAG_CREW).isEmpty() ? currentWOData.getString(TAG_CREW) : NULL_VALUE;

                        WOData.setEnddate(enddateNode);
                        WOData.setLatitude(latitudeNode);
                        WOData.setAddress(addressNode);
                        WOData.setState(stateNode);
                        WOData.setType(typeNode);
                        WOData.setStartdate(startdateNode);
                        WOData.setUuid(uuidNode);
                       WOData.setStatus(statusNode);
                        WOData.setCustomer(customerNode);
                        WOData.setLongitude(longitudeNode);
                        WOData.setCrew(crewNode);
                        WOData.setWOId(uuidNode);
                        WOData.setWorker(crewNode);
                        WOData.setDate(startdateNode);
                        WOArray.add(WOData);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                Log.d(LOG_TAG, "WOArray constructed.");
            }
        } else {
            Log.e(LOG_TAG, "Couldn't get any data from the url");
        }

        return WOArray;

    }
}