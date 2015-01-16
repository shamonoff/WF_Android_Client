package com.example.jbulavincev.mobileappexperiments;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by jbulavincev on 06.01.2015.
 */
class CheckCredsTask {

    private static final String HOST_NAME = "http://ichigo.dyndns-work.com/";
    private static final String URL = "http://ichigo.dyndns-work.com:7001/WorkforceManagementMobile/AuthenticateMobileApp.jsp";
    private static final String USER_NAME = "operator";
    private static final String PASSWORD = "operator";
    Context mContext;

    public CheckCredsTask (Context context){
        mContext = context;
    }

    protected String CheckCreds() {
        Log.d("WSInteraction", "start DoInBackground");
        DBClass db = new DBClass(mContext);
        String CredsBase64 = db.getCredsBase64();
        String Response = "NO RESPONSE";
        DefaultHttpClient httpclient = null;
        try {
            Log.d("WSInteraction", "Start try in WSInteraction");
            String CredsUTF8 = new String(Base64.decode(CredsBase64,android.util.Base64.NO_WRAP), "UTF-8");
            String[] credsparts = CredsUTF8.split(":");
            String loginutf8 = credsparts[0].trim(); // STRING_VALUES
            String credutf8 = credsparts[1].trim(); // STRING_VALUES
            Log.d("WSInteraction", "CredsUTF: "+CredsUTF8);
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
            httpclient = new DefaultHttpClient(httpParams);
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(HOST_NAME, 7001),
                    new UsernamePasswordCredentials(loginutf8, credutf8));
            Log.d("WSInteraction", "httpCliend creds set");
            HttpGet httpget = new HttpGet(URL);
            //  httpget.addHeader("Authorization", "Basic "+ Base64.encodeToString((USER_NAME+":"+PASSWORD).getBytes(),android.util.Base64.NO_WRAP));
            httpget.addHeader("Authorization", "Basic "+ CredsBase64);
            Log.d("WSInteraction", "httpGet generated");
            System.out.println("executing headers" + httpget.toString());
            System.out.println("executing request" + httpget.getRequestLine());
            Log.d("WSInteraction", "executing request: " + httpget.getRequestLine());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                Log.d("WSInteraction", "Response content length: " + entity.getContentLength());
                Response = EntityUtils.toString(entity);
                //  if (Response.substring(0, 9) == "<!DOCTYPE") throw new Exception() ;
                Log.d("WSInteraction", "EntityUtils.toString(entity): " + Response);
                if ((Response.substring(2, 9).compareToIgnoreCase("doctype") == 0) || (Response.substring(0, 10).compareToIgnoreCase("http error") == 0)) Response = "http error" ;
                Log.d("WSInteraction", "Response.substring(0, 9): " + Response.substring(0, 9));
                /*System.out.println(EntityUtils.toString(entity));*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            Response = "CONNECTION ERROR";
            Log.d("WSInteraction", "Empty response or error");
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            Log.d("WSInteraction", "Finally stack");
            return Response;
        }
    }

}