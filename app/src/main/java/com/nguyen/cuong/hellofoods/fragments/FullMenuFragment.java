package com.nguyen.cuong.hellofoods.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.activities.AddCartActivity;
import com.nguyen.cuong.hellofoods.adapters.ProductAdapter;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.databases.CartDao;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.Product;
import com.nguyen.cuong.hellofoods.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FullMenuFragment extends Fragment implements ProductAdapter.ProductEvent{

    private ArrayList<Product> products;
    private ProductAdapter adapter;
    private ListView listView;
    int page=1;
    ProgressDialog dialog;
    boolean flag_loading=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_full_menu,container,false);
        listView= (ListView) view.findViewById(R.id.list_full_menu);
        products=new ArrayList<>();
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        loadData(page);
        adapter=new ProductAdapter(getContext(),products,this);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flag_loading == false)
                    {
                        flag_loading = true;
                        page=page+1;
                        loadData(page);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void addCart(int position) {
        Intent intent=new Intent(getContext(), AddCartActivity.class);
        intent.putExtra("product",products.get(position));
        intent.putExtra("type","add");
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getActivity().RESULT_OK){
            CartDao dao=new CartDao(getContext());
            dao.addCart((Cart) data.getSerializableExtra("cart"));
        }
    }
    void loadData(int numPage){
        flag_loading=false;
        dialog.show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<List<Product>> call = api.getAll(numPage);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                dialog.dismiss();
                if(response.body()!=null && response.body().size()>0){
                    for(Product product : response.body()){
                        products.add(product);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
