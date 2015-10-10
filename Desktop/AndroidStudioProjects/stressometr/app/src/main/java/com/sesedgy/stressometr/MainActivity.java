package com.sesedgy.stressometr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    public int strOfDay = 0; //СТРЕСС За день


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button reportBtn = (Button) findViewById(R.id.report);
        Button settingsBtn = (Button) findViewById(R.id.settings);
        Button helpBtn = (Button) findViewById(R.id.help);
        Button vidBtn = (Button) findViewById(R.id.vidget);



        reportBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        vidBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                Intent reportIntent = new Intent(this, ReportActivity.class);
                startActivity(reportIntent);
                break;

            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.help:
                Intent helpIntent = new Intent(this, HelpActivity.class);
                startActivity(helpIntent);
                break;
            case R.id.vidget:
                Intent vidgetIntent = new Intent(this, WidgetActivity.class);
                startActivity(vidgetIntent);
                break;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
