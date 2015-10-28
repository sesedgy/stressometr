package com.sesedgy.stressometr;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WidgetActivity extends AppCompatActivity implements View.OnClickListener {

    int stress = 0; //значение стресса за день
    TextView show;
    Button strBut;  //Кнопка стресса
    Calendar dateNow = new GregorianCalendar();  //дата сохранения значения
    Calendar datelast = new GregorianCalendar(); //дата с которой сверяем
    int dayNow;
    int dayLast;
    SharedPreferences save;
    SharedPreferences load;
    SharedPreferences valueOfStr;
    private DBHelper dbhelper;
    int mouth;
    int day;
    int dayOfweek;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        strBut = (Button) findViewById(R.id.strBut);
        show = (TextView) findViewById(R.id.show);
        strBut.setOnClickListener(this);

        load = getPreferences(MODE_PRIVATE);                    //Считываем Дату
        dayLast = load.getInt("DATE + 1", 0);
        valueOfStr = getPreferences(MODE_PRIVATE);
        stress = valueOfStr.getInt("StressValue", 0);           //Сохраняем значение стресса в течении дня
        Log.i("MyLOG", "stress = " + stress );

        dayNow = dateNow.get(Calendar.DAY_OF_YEAR);
//        if ((dayNow + 1) != dayLast)                    //РАБОЧЕЕ ПОЛОЖЕНИЕ(ПРИ тесте первый раз запустить в рабочем положении)
        if ((dayNow + 1) == dayLast)                      //ПОЛОЖЕНИЕ ДЛЯ тестов чтобы заполнить базу данных данными
        {

            day = dateNow.get(Calendar.DATE);
            dayOfweek = dateNow.get(Calendar.DAY_OF_WEEK) - 1;
            mouth = dateNow.get(Calendar.MONTH) + 1;
            if(mouth == 13){mouth = 1;}
            if(dayOfweek == 0){dayOfweek = 7;}
            Log.i("MyLOG", " day | " + day );               //присваивание значений для вноса в бд

            int value = stress;
            dbhelper = new DBHelper(this);                      //создание экземпляра бд
            ContentValues cv =  new ContentValues();
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            cv.put("value", value);                             //присваивание значений "название поля", значение
            cv.put("day", day);
            cv.put("mouth", mouth);
            cv.put("dayofweek", dayOfweek);
            long rowID = db.insert("mytable", null, cv);
            Log.i("MyLOG", " : " + rowID + " | " + value + " | " + day + " | " + mouth + "|" + dayOfweek);
            dbhelper.close();

            dayLast = (dateNow.get(Calendar.DAY_OF_YEAR)) + 1;  //Сохраняем ДАТУ + 1
            save = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = save.edit();
            editor.putInt("DATE + 1", dayLast);
            editor.commit();
            Log.i("MyLOG", Integer.toString(dayNow) + "|" + Integer.toString(dayLast));
            stress = 0;                                         //Обнуляем значение стресса в начале нового дня
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_widget, menu);
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

        if (1==1){}
        stress = stress + 1;
        show.setText(" " + stress);
        valueOfStr = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = valueOfStr.edit();
        ed.putInt("StressValue", stress);
        ed.commit();

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
}
