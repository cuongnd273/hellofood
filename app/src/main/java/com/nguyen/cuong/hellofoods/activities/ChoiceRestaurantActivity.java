package com.nguyen.cuong.hellofoods.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.adapters.RestaurantAdapter;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChoiceRestaurantActivity extends AppCompatActivity implements RestaurantAdapter.RestaurantEvent{
    ArrayList<Restaurant> restaurants;
    ListView listView;
    RestaurantAdapter adapter;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_restaurant);
        this.setFinishOnTouchOutside(false);
        getControls();
        getData();
    }
    void getControls(){
        restaurants=new ArrayList<>();
        adapter=new RestaurantAdapter(this,restaurants,this);
        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
    }
    void getData(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<List<Restaurant>> call=api.getRestaurants();
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.body()!=null && response.body().size()>0){
                    for(Restaurant restaurant: response.body()){
                        restaurants.add(restaurant);
                    }
                    for(Restaurant restaurant: response.body()){
                        restaurants.add(restaurant);
                    }
                    for(Restaurant restaurant: response.body()){
                        restaurants.add(restaurant);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Intent intent=new Intent();
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                dialog.dismiss();
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    @Override
    public void select(RadioButton v, int position) {
        Intent intent=new Intent();
        intent.putExtra("id",restaurants.get(position).getId());
        setResult(RESULT_OK,intent);
        finish();
    }
}
