package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by cuong on 12/22/2017.
 */

public class RestaurantAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Restaurant> restaurants;
    private RestaurantEvent restaurantEvent;
    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants,RestaurantEvent restaurantEvent) {
        this.context = context;
        this.restaurants = restaurants;
        this.restaurantEvent=restaurantEvent;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.item_restaurant,parent,false);
        RadioButton button= (RadioButton) convertView.findViewById(R.id.name);
        button.setText(restaurants.get(position).getName()+"\n   "+restaurants.get(position).getAddress());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantEvent.select((RadioButton) v,position);
            }
        });
        return convertView;
    }
    public interface RestaurantEvent{
        void select(RadioButton v,int position);
    }
}
