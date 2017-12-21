package com.nguyen.cuong.hellofoods.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.adapters.CartAdapter;
import com.nguyen.cuong.hellofoods.databases.CartDao;
import com.nguyen.cuong.hellofoods.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment implements CartAdapter.CartEvent {
    private ListView listView;
    private ArrayList<Cart> carts;
    private CartAdapter adapter;
    private CartDao dao;
    private Button order;
    private TextView money;
    int totalMoney = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        getControls(view);
        return view;
    }

    void getControls(View v) {
        listView = (ListView) v.findViewById(R.id.list_cart);
        order = (Button) v.findViewById(R.id.order);
        money = (TextView) v.findViewById(R.id.total_money);
        dao = new CartDao(getContext());
        loadData();
        adapter = new CartAdapter(getContext(), carts, this);
        listView.setAdapter(adapter);
    }

    void loadData() {
        carts = dao.getCarts();
        totalMoney = 0;
        for (Cart cart : carts) {
            totalMoney += cart.getCount() * cart.getPrice();
        }
        DecimalFormat format = new DecimalFormat("#,###,###");
        money.setText("Tổng tiền : " + format.format(totalMoney) + " đ");
        if (carts == null)
            order.setVisibility(View.INVISIBLE);
        else {
            if (carts.size() == 0)
                order.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void deleteCart(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn muốn xóa sản phẩm này không ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deleteCart(carts.get(position).getId());
                        carts.clear();
                        loadData();
                        ((CartAdapter) listView.getAdapter()).notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
