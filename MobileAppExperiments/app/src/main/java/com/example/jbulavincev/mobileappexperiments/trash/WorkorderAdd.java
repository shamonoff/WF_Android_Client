package com.example.jbulavincev.mobileappexperiments.trash;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jbulavincev.mobileappexperiments.DBClass;
import com.example.jbulavincev.mobileappexperiments.R;
import com.example.jbulavincev.mobileappexperiments.WorkorderData;


public class WorkorderAdd extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_add);
        Button addbutton= (Button) findViewById(R.id.addButton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewWOToSqliteDB();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workorder_add, menu);
        return true;
    }

    public void addNewWOToSqliteDB ()
    {
        WorkorderData WOData = new WorkorderData();
        EditText dateField = (EditText) findViewById(R.id.dateField);
        WOData.setDate(dateField.getText().toString());
        EditText workerField = (EditText) findViewById(R.id.workerField);
        WOData.setWorker(workerField.getText().toString());
        EditText idField = (EditText) findViewById(R.id.idField);
        WOData.setWOId(idField.getText().toString());
        EditText latField = (EditText) findViewById(R.id.latField);
        WOData.latitude = latField.getText().toString();
        EditText longField = (EditText) findViewById(R.id.longField);
        WOData.longitude = longField.getText().toString();
        WOData.status = "Opened";
       // DBClass db = new DBClass(WorkorderAdd.this);
        //db.getWritableDatabase();
        //db.addWorkorder(WOData);
       // db.printAllWOData();
        //FINISH ACTIVITY
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "WOCreateRequestDone");
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void goBackToWOActivity ()
    {

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
