package com.example.jbulavincev.mobileappexperiments;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import java.util.HashMap;
import java.util.Map;

public class TabListActivity extends TabActivity {

    TabHost myTabHost;

    private static final String FIRST_TAB_NAME = "WO";
    private static final String SECOND_TAB_NAME = "MAP";

    enum TABNAMES {FIRST_TAB_NAME, SECOND_TAB_NAME};

    {
        Map<String, Class> Contexts = new HashMap<String, Class>();
        Contexts.put(FIRST_TAB_NAME, WorkordersActivity.class);
        Contexts.put(SECOND_TAB_NAME, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);
        myTabHost=getTabHost();

        TabHost.TabSpec tabSpec;

       /* for (TABNAMES tabname : TABNAMES.values())
        {
            tabSpec = myTabHost.newTabSpec(tabname.toString());
            tabSpec.setIndicator(tabname.toString());
            tabSpec.setContent(new Intent(this, Contexts.get(tabname.toString())));
            myTabHost.addTab(tabSpec);
        }*/

        tabSpec = myTabHost.newTabSpec(FIRST_TAB_NAME);
        tabSpec.setIndicator(FIRST_TAB_NAME);
        tabSpec.setContent(new Intent(this, WorkordersActivity.class));
        myTabHost.addTab(tabSpec);

        tabSpec = myTabHost.newTabSpec(SECOND_TAB_NAME);
        tabSpec.setIndicator(SECOND_TAB_NAME);
        tabSpec.setContent(new Intent(this, MapActivity.class));
        myTabHost.addTab(tabSpec);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_tab_list, menu);
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
