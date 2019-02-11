package com.example.myweather;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myweather.tasks.ParseResult;
import com.example.myweather.tasks.RequestTask;
import com.example.myweather.tasks.TaskOutput;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FragmentOne extends Fragment {

    Weather weatherToday = new Weather();

    private View rootView;
    private TextView textView_lastUpdate;
    private TextView textView_fragment_degree;
    private TextView textView_Humidity;
    private TextView textView_wind;
    private TextView textView_city;
    private ImageView imageView_picture;
    private ImageButton imageButton;
    private static String text;
    private String iconUrl;
    private String searchCity = "Saint-Petersburg";
    ProgressDialog progressDialog;
    public String recentCityId = "";


    Handler handler;

    public FragmentOne() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        textView_city = (TextView) rootView.findViewById(R.id.textView_city);
        textView_fragment_degree = (TextView) rootView.findViewById(R.id.textView_fragment_degree);
        textView_Humidity = (TextView) rootView.findViewById(R.id.textView_Humidity);
        textView_wind = (TextView) rootView.findViewById(R.id.textView_wind);
        imageButton = (ImageButton) rootView.findViewById(R.id.imageButton_fragment);
        textView_lastUpdate = (TextView) rootView.findViewById(R.id.textView_lastUpdate);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCities(v);
            }
        });

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());

    }

    @Override
    public void onStart() {
        super.onStart();
        preloadWeather();
        //updateWeatherOnStart();
        //weatherPoint(searchCity);
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateWeatherOnStart();
    }



    private void updateWeatherOnStart() {
        try {if (weatherToday.getCountry().isEmpty()){
            preloadWeather();
            return;
        }

        } catch (Exception e){
            preloadWeather();
            return;
        }

    }

    private void preloadWeather() {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        //searchCity = sp.getString("lastToday", "");
       String lastToday = sp.getString("lastToday", "");
        if (!lastToday.isEmpty()) {
         RequestTask rTask = new TodayWeatherTask(lastToday, FragmentOne.this, weatherToday, progressDialog);//new RequestTask(lastToday, FragmentOne.this, weatherToday, progressDialog);
         rTask.execute();

         //setWeatherData();
        //result = rTask.get();
        }

        /*
        String lastLongterm = sp.getString("lastLongterm", "");
        if (!lastLongterm.isEmpty()) {
            new LongTermWeatherTask(this, this, progressDialog).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "cachedResponse", lastLongterm);
        }*/


    }

    class TodayWeatherTask extends RequestTask {

        public TodayWeatherTask(String json, FragmentOne activity, Weather weatherToday, ProgressDialog progressDialog) {
            super(json, activity, weatherToday, progressDialog);
        }

        @Override
        protected void onPreExecute() {
            loading = 0;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String output) {
            super.onPostExecute(output);
            setWeatherData();
        }
    }


    private void setWeatherData() {

        textView_city.setText(weatherToday.getCity());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E yyyy.MM.dd 'и время' hh:mm:ss a zzz");
        textView_lastUpdate.setText(weatherToday.getLastUpdate());
        textView_Humidity.setText(weatherToday.getHumidity());
        textView_wind.setText(weatherToday.getWind());
        textView_fragment_degree.setText(weatherToday.getFragment_degree());
    }


    private void searchCities(View v) {
        Context context = v.getContext();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(context.getString(R.string.search_title));
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setMaxLines(1);
        input.setSingleLine(true);
        alert.setView(input);


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = input.getText().toString();
                if (!result.isEmpty()){
                   searchCity = result;
                    weatherPoint(searchCity);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();

    }

    public void weatherPoint(final String city){new Thread(){
        public void run(){
            final JSONObject json = RemoteFetch.getJSON(city);
            //Handler handler = new Handler();
            if(json == null){
                handler.post(new Runnable(){
                    public void run(){bad();
                    }
                });
            }
            else {
                handler.post(new Runnable(){
                    public void run(){
                       ParseResult result = renderWeather(json.toString());}
                    });
        }
    }
    }.start();

    }

    private ParseResult renderWeather(String result) {

        try {
            JSONObject json = new JSONObject(result);
            JSONObject main = json.getJSONObject("location");
            textView_city.setText(main.getString("name"));
            //textView_city.setText(weatherToday.getCity());

            JSONObject current = json.getJSONObject("current");
            textView_fragment_degree.setText(current.getString("temp_c"));
            textView_Humidity.setText("Влажность: " + current.getString("humidity"));
            textView_wind.setText("Скорость ветра: " + current.getString("wind_kph"));

            JSONObject condition = current.getJSONObject("condition");
            iconUrl = (String)condition.getString("icon");

            //imageView_picture.setImageBitmap(RemoteFetch.getIconNewThread(iconUrl));

            //textView_city.setText(json.getString("name"));
            //textView_city.setText(json.getString("name").toUpperCase(Locale.US) +
            //        ", " +
            //        json.getJSONObject("sys").getString("country"));
            //textView_fragment_degree.setText(String.format("%.2f", main.getDouble("temp")/32)+ " ℃");

            //JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            //textView_Humidity.setText("Humidity: " + main.getString("humidity"));
            //textView_pressure.setText("Pressure: " + main.getString("pressure") + " hPa");

           /* cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            /*JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);*/

           SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
           editor.putString("lastToday", json.toString());
           editor.commit();
        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
            return ParseResult.OK;
    }

    private void bad(){
        text = "Bad";

    }


    public static long saveLastUpdateTime(SharedPreferences sp) {
        Calendar now = Calendar.getInstance();
        sp.edit().putLong("lastUpdate", now.getTimeInMillis()).commit();
        return now.getTimeInMillis();
    }



}

