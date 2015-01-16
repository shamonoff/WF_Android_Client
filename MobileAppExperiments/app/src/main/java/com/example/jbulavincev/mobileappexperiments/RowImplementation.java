package com.example.jbulavincev.mobileappexperiments;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jbulavincev on 05.01.2015.
 */
public class RowImplementation extends Activity {

    public RowImplementation()
    {}

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row);
    }

   // public void onClick(View view) {
   //     Intent myIntent = new Intent(RowImplementation.this, WorkorderDetails.class);
    //    RowImplementation.this.startActivity(myIntent);
   // }
}
