package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by cuong on 11/20/2017.
 */

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> products;
    private ProductEvent productEvent;

    public ProductAdapter(Context context, ArrayList<Product> products, ProductEvent productEvent) {
        this.context = context;
        this.products = products;
        this.productEvent = productEvent;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
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
            view = inflater.inflate(R.layout.item_product, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (RoundedImageView) view.findViewById(R.id.image);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.description = (TextView) view.findViewById(R.id.description);
            viewHolder.price_new = (TextView) view.findViewById(R.id.price_new);
            viewHolder.price_old = (TextView) view.findViewById(R.id.price_old);
            viewHolder.add_cart= (ImageView) view.findViewById(R.id.add_cart);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Picasso.with(context).load(products.get(position).getImage()).into(viewHolder.image);
        viewHolder.name.setText(products.get(position).getName());
        viewHolder.description.setText(products.get(position).getDescription());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (products.get(position).getSale() != 0) {
            int price = products.get(position).getPrice();
            int salse = products.get(position).getSale();
            viewHolder.price_new.setText(formatter.format(price - (price * salse) / 100) + " đ");
            viewHolder.price_old.setText(formatter.format(products.get(position).getPrice()) + " đ");
        } else {
            viewHolder.price_new.setText(formatter.format(products.get(position).getPrice()) + " đ");
            viewHolder.price_old.setVisibility(View.INVISIBLE);
        }
        viewHolder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productEvent.addCart(position);
            }
        });
        return view;
    }

    class ViewHolder {
        public TextView name, price_new, price_old, description;
        public RoundedImageView image;
        public ImageView add_cart;
    }

    public interface ProductEvent {
        public void addCart(int position);
    }
}
