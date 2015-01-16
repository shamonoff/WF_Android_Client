package com.example.jbulavincev.mobileappexperiments;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by jbulavincev on 15.01.2015.
 */
public class FileRWClass {

    private static final String FILE_NAME = "woarray.txt";
    private static final String LOG_TAG = "FileRWClass" ;
    private Context mContext;

    public FileRWClass(Context mContext)
    {
        this.mContext = mContext;
    }

    public void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.d(LOG_TAG, "File write completed.");
        }
        catch (IOException e) {
            Log.d(LOG_TAG, "File write failed: " + e.toString());
        }
    }


    public String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = mContext.openFileInput(FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

            Log.e(LOG_TAG, "File read completed.");
        }
        catch (FileNotFoundException e) {
            Log.d(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.d(LOG_TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

}
