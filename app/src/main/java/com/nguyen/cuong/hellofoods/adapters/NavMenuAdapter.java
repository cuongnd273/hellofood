package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.NavMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cuong on 11/18/2017.
 */

public class NavMenuAdapter extends BaseAdapter {
    private ArrayList<NavMenu> navMenus;
    private Context context;

    public NavMenuAdapter(ArrayList<NavMenu> navMenus, Context context) {
        this.navMenus = navMenus;
        this.context = context;
    }

    @Override
    public int getCount() {
        return navMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return navMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view == null) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.item_nav,parent,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.layout= (LinearLayout) view.findViewById(R.id.layout_item);
            viewHolder.icon= (ImageView) view.findViewById(R.id.icon_menu);
            viewHolder.title= (TextView) view.findViewById(R.id.title_menu);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        viewHolder.icon.setImageDrawable(context.getResources().getDrawable(navMenus.get(position).getIcon()));
        viewHolder.title.setText(navMenus.get(position).getTitle());
        if(navMenus.get(position).isSelected()){
            viewHolder.icon.setColorFilter(Color.WHITE);
            viewHolder.title.setTextColor(Color.WHITE);
            viewHolder.layout.setBackgroundColor(Color.RED);
        }else{
            viewHolder.icon.setColorFilter(context.getResources().getColor(R.color.menu_text_normal));
            viewHolder.title.setTextColor(context.getResources().getColor(R.color.menu_text_normal));
            viewHolder.layout.setBackgroundColor(Color.WHITE);
        }
        return view;
    }

    class ViewHolder {
        public LinearLayout layout;
        public ImageView icon;
        public TextView title;
    }
}
