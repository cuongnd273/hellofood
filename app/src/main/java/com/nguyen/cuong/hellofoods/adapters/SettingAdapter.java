package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Setting;

import java.util.ArrayList;

/**
 * Created by cuong on 11/28/2017.
 */

public class SettingAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Setting> settings;

    public SettingAdapter(Context context, ArrayList<Setting> settings) {
        this.context = context;
        this.settings = settings;
    }

    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int position) {
        return settings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.item_setting,parent,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.name= (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        viewHolder.name.setText(settings.get(position).getName());
        return view;
    }
    class ViewHolder{
        TextView name;
    }
}
