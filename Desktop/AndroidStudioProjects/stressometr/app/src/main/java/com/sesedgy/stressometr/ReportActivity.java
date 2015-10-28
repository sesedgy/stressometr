package com.sesedgy.stressometr;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class ReportActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button Alltime = (Button) findViewById(R.id.forAllTime);
        Button forYear = (Button) findViewById(R.id.forYear);
        Button forMounth = (Button) findViewById(R.id.forMounth);
        Button dayOfWeek = (Button) findViewById(R.id.dayOfWeek);


        Alltime.setOnClickListener(this);
        forYear.setOnClickListener(this);
        forMounth.setOnClickListener(this);
        dayOfWeek.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
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

    @Override
    public void onClick(View v) {
        Intent allTime = new Intent(this, Graphic.class);
        switch (v.getId()){
            case R.id.forAllTime:
                allTime.putExtra("value", "a");
                break;
            case R.id.forYear:
                allTime.putExtra("value", "b");
                break;
            case  R.id.forMounth:
                allTime.putExtra("value", "c");
                break;
            case R.id.dayOfWeek:
                allTime.putExtra("value", "d");
        }
        startActivity(allTime);

    }
}
