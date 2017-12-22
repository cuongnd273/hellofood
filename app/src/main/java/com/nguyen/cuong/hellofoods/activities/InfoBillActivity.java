package com.nguyen.cuong.hellofoods.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.adapters.CartAdapter;
import com.nguyen.cuong.hellofoods.adapters.InfoBillAdapter;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.InfoBill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoBillActivity extends AppCompatActivity{
    private ListView listView;
    private ArrayList<InfoBill> infoBills;
    private InfoBillAdapter adapter;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    ProgressDialog dialog;
    int idBill=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bill);
        getControls();
        Intent intent=getIntent();
        idBill=intent.getIntExtra("id",0);
        if(idBill!=0)
            loadData(idBill);
        else
            finish();
    }
    void getControls(){
        listView= (ListView) findViewById(R.id.list_info);
        appBarLayout= (AppBarLayout) findViewById(R.id.appBar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Chi tiết hóa đơn");
        infoBills=new ArrayList<>();
        adapter=new InfoBillAdapter(this,infoBills);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    void loadData(int id){
        dialog.show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<List<InfoBill>> call=api.getInfoBill(id);
        call.enqueue(new Callback<List<InfoBill>>() {
            @Override
            public void onResponse(Call<List<InfoBill>> call, Response<List<InfoBill>> response) {
                if(response.body()!=null && response.body().size()>0){
                    for(InfoBill infoBill : response.body()){
                        infoBills.add(infoBill);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    dialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<InfoBill>> call, Throwable t) {
                dialog.dismiss();
                finish();
            }
        });
    }
}
