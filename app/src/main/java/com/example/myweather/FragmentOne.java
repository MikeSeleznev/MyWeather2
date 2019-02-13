package com.example.myweather;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myweather.POJO.PostModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOne extends Fragment {

    Weather weatherToday = new Weather();

    private View rootView;
    private TextView textView_lastUpdate;
    private TextView textView_fragment_degree;
    private TextView textView_Humidity;
    private TextView textView_wind;
    private TextView textView_city;
    private ImageButton imageButton;
    private static String text;
    private String iconUrl;
    private String searchCity = "Saint-Petersburg";
    private PostModel postModel;
    ProgressDialog progressDialog;


    List<PostModel> posts;
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

        posts = new ArrayList<>();
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        String json = new Gson().toJson(postModel);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString("lastToday", json.toString());
        editor.commit();
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
        /*
        Проверяем сохранены ли у нас данные в формате Json
         */
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
       String lastToday = sp.getString("lastToday", "");
       if (!lastToday.isEmpty()) {
            postModel = new Gson().fromJson(lastToday, PostModel.class);
            setWeatherData(postModel);
            }
           else {
               getWeather(searchCity);
        }

    }

    private void getWeather(String searchCity) {
        App.getApi().getData("a79eb4cd14284cdfa1e141121190802", searchCity).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                // posts.addAll(response.body());
                PostModel postModel = response.body();
                setWeatherData(postModel);

                //String example = new Gson().toJson(responseB);//Преобразование класса в Json
                //PostModel responseC = new Gson().fromJson(example, PostModel.class);// преобразование из Json в класс
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {

            }
        });

    }


    private void setWeatherData(PostModel postModel) {

        textView_city.setText(postModel.getLocation().getName());
        textView_lastUpdate.setText("Последнее обновление " + postModel.getCurrent().getLastUpdated());
        textView_Humidity.setText("Влажность " + postModel.getCurrent().getHumidity().toString() + "%");
        textView_wind.setText("Скорость ветра " + postModel.getCurrent().getWindKph().toString() + " км/ч");
        textView_fragment_degree.setText(postModel.getCurrent().getTempC().toString());
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
                    getWeather(result);
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







}

