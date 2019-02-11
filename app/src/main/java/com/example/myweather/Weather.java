package com.example.myweather;


import android.util.Log;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {

    private String lastUpdate;
    private String city;
    private String country;
    private String humidity;
    private String wind;
    private static String text;
    private static String degrees;
    private String fragment_degree;

    public String getCountry(){return country;}

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

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
       return this.city;
    }

    public void setLastUpdate(String lastUpdate){

        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdate(){
        return  lastUpdate;
    }

    public void setHumidity(String humadity){
        this.humidity = humadity;
    }

    public String getHumidity(){
        return this.humidity;
    }

    public void setWind (String wind){
        this.wind = wind;
    }

    public String getWind(){
        return this.wind;
    }

    public void setFragment_degree (String degree){
        this.fragment_degree = degree;
    }

    public String getFragment_degree(){
        return  this.fragment_degree;
    }
}
