package com.nguyen.cuong.hellofoods.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.activities.InfoBillActivity;
import com.nguyen.cuong.hellofoods.adapters.BillAdapter;
import com.nguyen.cuong.hellofoods.models.Bill;

import java.util.ArrayList;

public class HistoryFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ArrayList<Bill> bills;
    private BillAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);
        getControls(view);
        return view;
    }
    void getControls(View v){
        listView= (ListView) v.findViewById(R.id.list_history);
        bills=new ArrayList<>();
        bills.add(new Bill("27-03-2017 13:60",1,350000));
        bills.add(new Bill("27-03-2017 13:60",1,350000));
        bills.add(new Bill("27-03-2017 13:60",1,350000));
        bills.add(new Bill("27-03-2017 13:60",1,350000));
        bills.add(new Bill("27-03-2017 13:60",1,350000));
        bills.add(new Bill("27-03-2017 13:60",1,350000));
        adapter=new BillAdapter(getContext(),bills);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), InfoBillActivity.class);
        startActivity(intent);
    }
}
