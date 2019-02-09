package com.example.myweather;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


public class FragmentOne extends Fragment {

    View rootView;
    TextView textView;
    TextView textView_fragment_degree;
    TextView textView_Humidity;
    TextView textView_wind;
    TextView textView_city;
    ImageView imageView_picture;
    ImageButton imageButton;
    static String text;
    String iconUrl;
    String searchCity = "";

    Handler handler;

    public FragmentOne(){
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
       imageView_picture = (ImageView) rootView.findViewById(R.id.imageView_picture);
       imageButton = (ImageButton) rootView.findViewById(R.id.imageButton_fragment);

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
       weatherPoint(searchCity);
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

        AlertDialog alertCreate =  alert.create();
        alertCreate.show();
        Button nbutton = alertCreate.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.MAGENTA);
        Button pbutton = alertCreate.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.YELLOW);
        //alert.show();

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
                        renderWeather(json);}
                    });
        }
    }
    }.start();

    }

    private void renderWeather(JSONObject json) {

        try {
            JSONObject main = json.getJSONObject("location");
            textView_city.setText(main.getString("name"));

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

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }

    }

    private void bad(){
        text = "Bad";

    }






}

