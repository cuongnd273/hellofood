package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ArrayList<Cart> carts;
    private CartEvent event;

    public CartAdapter(Context context, ArrayList<Cart> carts, CartEvent event) {
        this.context = context;
        this.carts = carts;
        this.event = event;
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int position) {
        return carts.get(position);
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
            viewHolder.cart= (LinearLayout) view.findViewById(R.id.cart);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText(carts.get(position).getName());
        viewHolder.count.setText(String.valueOf(carts.get(position).getCount()));
        DecimalFormat format = new DecimalFormat("#,###,###");
        viewHolder.price.setText(format.format(carts.get(position).getPrice()) + " đ");
        Picasso.with(context).load(carts.get(position).getImage()).into(viewHolder.image);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.deleteCart(position);
            }
        });
        viewHolder.cart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                event.editCart(position);
                return true;
            }
        });
        return view;
    }
    public void refest(ArrayList<Cart> carts){
        this.carts.clear();
        this.carts.addAll(carts);
        notifyDataSetChanged();
    }
    class ViewHolder {
        TextView name, count, price;
        RoundedImageView image;
        ImageView delete;
        LinearLayout cart;
    }

    public interface CartEvent {
        void deleteCart(int position);
        void editCart(int position);
    }
}
