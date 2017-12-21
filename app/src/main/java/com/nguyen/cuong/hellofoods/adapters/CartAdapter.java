package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.InfoBill;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by cuong on 11/26/2017.
 */

public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Cart> infoBills;
    private CartEvent event;

    public CartAdapter(Context context, ArrayList<Cart> infoBills, CartEvent event) {
        this.context = context;
        this.infoBills = infoBills;
        this.event = event;
    }

    @Override
    public int getCount() {
        return infoBills.size();
    }

    @Override
    public Object getItem(int position) {
        return infoBills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_cart, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.count = (TextView) view.findViewById(R.id.count);
            viewHolder.price = (TextView) view.findViewById(R.id.price);
            viewHolder.image = (RoundedImageView) view.findViewById(R.id.image);
            viewHolder.delete= (ImageView) view.findViewById(R.id.delete);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText(infoBills.get(position).getName());
        viewHolder.count.setText(String.valueOf(infoBills.get(position).getCount()));
        DecimalFormat format = new DecimalFormat("#,###,###");
        viewHolder.price.setText(format.format(infoBills.get(position).getPrice()) + " đ");
        Picasso.with(context).load(infoBills.get(position).getImage()).into(viewHolder.image);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.deleteCart(position);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView name, count, price;
        RoundedImageView image;
        ImageView delete;
    }

    public interface CartEvent {
        void deleteCart(int position);
    }
}
