package com.sesedgy.stressometr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class Graphic extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        GraphView graph = (GraphView) findViewById(R.id.graphic);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();






        Intent intent = getIntent();
        String value = intent.getStringExtra("value");
        Log.e("MyLOG", "VALUE = " + value);



        switch (value){
            case "a":

                Cursor a = db.query("mytable", null, null, null, null, null, null);                                 // ПО МЕСЯЦАМ СОРТИРОВКА
                int count = a.getCount();



                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(generateData(a,count,1));
                graph.addSeries(series);
                break;

            case "b":
                String selection = null;
                String[] selectionArgs = null;
                Calendar now = new GregorianCalendar();

                int nowDay = now.get(Calendar.MONTH) + 1;
                if(nowDay == 13){nowDay = 1;}
                Log.i("MyLOG", " now month : " + nowDay);


                selection = "mouth = ?";
                selectionArgs = new String[] {Integer.toString(nowDay)};
                Cursor b = db.query("mytable",null, selection, selectionArgs, null, null, null );                                //ПО ДНЯМ МЕСЯЦА СОРТИРОВКА
                int count2 = b.getCount();
                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(generateData(b,count2,2));
                graph.addSeries(series2);
                break;

            case "c":

                Cursor c = db.query("mytable", null, null, null, null, null, null);                               // ПО ДНЯМ НЕДЕЛИ СОРТИРОВКА
                int count3 = c.getCount();
                BarGraphSeries<DataPoint> series3 = new BarGraphSeries<DataPoint>(generateData(c,7,3));
                graph.addSeries(series3);
                break;

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graphic, menu);
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

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDataBase", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("MyLOG", "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "value integer,"
                    + "day integer,"
                    + "mouth integer,"
                    + "dayofweek integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private DataPoint[] generateData(Cursor c, int count, int swit) {

        DataPoint[] values = new DataPoint[count];

            if (c.moveToFirst()) {
                int valueColIndex = c.getColumnIndex("value");       // определяем номера столбцов по имени в выборке
                int dayColIndex = c.getColumnIndex("day");
                int mouthColIndex = c.getColumnIndex("mouth");
                int dayOFweekIndex = c.getColumnIndex("dayofweek");
                int dayFromBD;
                int valueFromBD;
                int mouthFromBD;
                int dayOFweekFromBD;
                int i = 0;
                int day1 = 0, day2 = 0, day3 = 0, day4 = 0, day5 = 0, day6 = 0, day7 = 0;
                int col1 = 1, col2 = 1, col3 = 1, col4 = 1, col5 = 1, col6 = 1, col7 =1;


                do {
                    // получаем значения по номерам столбцов и пишем все в лог

                    valueFromBD = c.getInt(valueColIndex);
                    dayFromBD = c.getInt(dayColIndex);
                    mouthFromBD = c.getInt(mouthColIndex);
                    dayOFweekFromBD = c.getInt(dayOFweekIndex);

                    if (swit == 1)                                                                                           // ПО МЕСЯЦАМ СОРТИРОВКА
                    {
                        Log.i("MyTAG", "|" + valueFromBD + "|" + dayFromBD + "|" + mouthFromBD + "|" + dayOFweekFromBD );
                        DataPoint v = new DataPoint(mouthFromBD, valueFromBD);
                        values[i] = v;
                        i++;
                    }

                    if (swit == 2)                                                                                          //ПО ДНЯМ МЕСЯЦА СОТРИРОВКА
                    {
                        Log.i("MyTAG", "|" + valueFromBD + "|" + dayFromBD + "|" + mouthFromBD + "|" + dayOFweekFromBD );
                        DataPoint v = new DataPoint(dayFromBD, valueFromBD);
                        values[i] = v;
                        i++;
                    }

                    if (swit == 3)                                                                                          //ПО ДНЯМ НЕДЕЛИ СОРТИРОВКА
                    {
                        Log.i("MyTAG", "|" + valueFromBD + "|" + dayFromBD + "|" + mouthFromBD + "|" + dayOFweekFromBD );
                        if (dayOFweekFromBD == 1){day1 = day1 + valueFromBD; col1++;}
                        if (dayOFweekFromBD == 2){day2 = day2 + valueFromBD; col2++;}
                        if (dayOFweekFromBD == 3){day3 = day3 + valueFromBD; col3++;}
                        if (dayOFweekFromBD == 4){day4 = day4 + valueFromBD; col4++;}
                        if (dayOFweekFromBD == 5){day5 = day5 + valueFromBD; col5++;}
                        if (dayOFweekFromBD == 6){day6 = day6 + valueFromBD; col6++;}
                        if (dayOFweekFromBD == 7){day7 = day7 + valueFromBD; col7++;}
                        Log.i("MyLOG", day3 + "|" + col3);
                        i++;
                    }

                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                } while (c.moveToNext());

                if (swit == 3){
                    for (int j = 0; j < 7; i++) {
                        DataPoint v = new DataPoint(0,0);
                        if (j == 0) {
                            if (col1 > 1) {col1 = col1 - 1;}
                            if (col2 > 1) {col2 = col2 - 1;}
                            if (col3 > 1) {col3 = col3 - 1;}
                            if (col4 > 1) {col4 = col4 - 1;}
                            if (col5 > 1) {col5 = col5 - 1;}
                            if (col6 > 1) {col6 = col6 - 1;}
                            if (col7 > 1) {col7 = col7 - 1;}
                        }
                        if (j == 0){v = new DataPoint(1,day1/col1);}
                        if (j == 1){v = new DataPoint(2,day2/col2);}
                        if (j == 2){v = new DataPoint(3,day3/col3);}
                        if (j == 3){v = new DataPoint(4,day4/col4);}
                        if (j == 4){v = new DataPoint(5,day5/col5);}
                        if (j == 5){v = new DataPoint(6,day6/col6);}
                        if (j == 6){v = new DataPoint(7,day7/col7);}
                        Log.i("MyLOG", "!!!" + day3/col3 + " | " + day3 + col3);
                        values[j] = v;
                        j++;
                    }
                }

            }else
                Log.i("MyLOG", "0 rows");


            c.close();

        return values;
    }
}
