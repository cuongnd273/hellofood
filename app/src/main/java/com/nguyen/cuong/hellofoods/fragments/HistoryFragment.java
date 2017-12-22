package com.nguyen.cuong.hellofoods.fragments;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.activities.InfoBillActivity;
import com.nguyen.cuong.hellofoods.adapters.BillAdapter;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.Bill;
import com.nguyen.cuong.hellofoods.models.User;
import com.nguyen.cuong.hellofoods.utils.AccountInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ArrayList<Bill> bills;
    private BillAdapter adapter;
    User user;
    AccountInfo accountInfo;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);
        getControls(view);
        loadData();
        return view;
    }
    void getControls(View v){
        listView= (ListView) v.findViewById(R.id.list_history);
        bills=new ArrayList<>();
        adapter=new BillAdapter(getContext(),bills);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        accountInfo=new AccountInfo(getContext());
        user=accountInfo.getAccount();
        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), InfoBillActivity.class);
        intent.putExtra("id",bills.get(position).getId());
        startActivity(intent);
    }
    void loadData(){
        dialog.show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<List<Bill>> call=api.getHistory(user.getIDTaiKhoan());
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if(response.body()!=null && response.body().size()>0){
                    for(Bill bill : response.body()){
                        bills.add(bill);
                    }
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
