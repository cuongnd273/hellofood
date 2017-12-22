package com.nguyen.cuong.hellofoods.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.activities.AddAddressActivity;
import com.nguyen.cuong.hellofoods.activities.AddCartActivity;
import com.nguyen.cuong.hellofoods.activities.ChoiceRestaurantActivity;
import com.nguyen.cuong.hellofoods.adapters.CartAdapter;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.databases.CartDao;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.InfoOrder;
import com.nguyen.cuong.hellofoods.models.Order;
import com.nguyen.cuong.hellofoods.models.User;
import com.nguyen.cuong.hellofoods.utils.AccountInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class CartFragment extends Fragment implements CartAdapter.CartEvent,View.OnClickListener {
    private ListView listView;
    private ArrayList<Cart> carts;
    private CartAdapter adapter;
    private CartDao dao;
    private Button order;
    private TextView money;
    int totalMoney = 0;
    int idNhaHang=0;
    String address;
    User user;
    int REQUEST_EDIT=2;
    int REQUEST_RESTAURANT=3;
    int REQUEST_ADDRESS=4;
    ProgressDialog dialog;
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
        order.setOnClickListener(this);
        user=new AccountInfo(getContext()).getAccount();
        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
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
                        loadData();
                        adapter.refest(carts);
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

    @Override
    public void editCart(int position) {
        Intent intent = new Intent(getContext(), AddCartActivity.class);
        intent.putExtra("cart", carts.get(position));
        intent.putExtra("type", "edit");
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT) {
            Cart cart = (Cart) data.getSerializableExtra("cart");
            dao.edit(cart);
            loadData();
            adapter.refest(carts);
        }else if(requestCode==REQUEST_RESTAURANT){
            if(resultCode==RESULT_OK){
                idNhaHang=data.getIntExtra("id",0);
                if(idNhaHang!=0){
                    Intent intent=new Intent(getContext(), AddAddressActivity.class);
                    startActivityForResult(intent,REQUEST_ADDRESS);
                }else{
                    Toast.makeText(getContext(), "Chưa chọn được nhà hàng", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Chưa chọn nhà hàng", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==REQUEST_ADDRESS){
            if(resultCode==RESULT_OK){
                address=data.getStringExtra("address");
                order();
            }else{
                Toast.makeText(getContext(), "Chưa lấy được địa chỉ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.order){
            Intent intent=new Intent(getContext(), ChoiceRestaurantActivity.class);
            startActivityForResult(intent,REQUEST_RESTAURANT);
        }
    }
    void order(){
        dialog.show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        JsonObject json=new JsonObject();
        JsonArray array=new JsonArray();
        int total=0;
        List<InfoOrder> infoOrders=new ArrayList<>();
        for(Cart cart : carts){
            infoOrders.add(new InfoOrder(cart.getId(),cart.getCount()));
            total=total+cart.getPrice()*cart.getCount();
        }
        Order order=new Order(idNhaHang,user.getIDTaiKhoan(),address,infoOrders,total);
        Call<String> call=api.order("application/json",order);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                try {
                    JsonObject object= (JsonObject) new JsonParser().parse(response.body());
                    if(object.get("status").getAsInt()==0){
                        dao.deleteAllCarts();
                        carts=dao.getCarts();
                        adapter.refest(carts);
                        Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "Đặt hàng lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Đặt hàng thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
