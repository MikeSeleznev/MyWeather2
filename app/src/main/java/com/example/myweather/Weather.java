package com.example.myweather;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
   static String text;
   static String degrees;

    public static void weatherPoint(final String v){new Thread(){
        public void run(){
            final JSONObject json = RemoteFetch.getJSON("Москва");
            //Handler handler = new Handler();
            if(json == null){ bad(v);}
                else {
                        renderWeather(json);
                    }
                };


    }.start();


    }

    private static void renderWeather(JSONObject json) {
        try {
            JSONObject main = json.getJSONObject("main");
            text = json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country");
            degrees = String.format("%.2f", main.getDouble("temp")/32)+ " ℃";
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

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }

    }

    public static void bad(String v){
       text = "Bad";

    }

    public static void good(String v){
        text = "Good";
    }

    public static String getWeather(){

        weatherPoint(text);
        return degrees;
    }
}
