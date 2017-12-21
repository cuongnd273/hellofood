package com.nguyen.cuong.hellofoods.activities;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.adapters.CartAdapter;
import com.nguyen.cuong.hellofoods.adapters.InfoBillAdapter;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.InfoBill;

import java.util.ArrayList;

public class InfoBillActivity extends AppCompatActivity{
    private ListView listView;
    private ArrayList<InfoBill> infoBills;
    private InfoBillAdapter adapter;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bill);
        getControls();
    }
    void getControls(){
        listView= (ListView) findViewById(R.id.list_info);
        appBarLayout= (AppBarLayout) findViewById(R.id.appBar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Chi tiết hóa đơn");
        infoBills=new ArrayList<>();
        infoBills.add(new InfoBill("Cafe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg",3,45000));
        infoBills.add(new InfoBill("Cafe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg",3,45000));
        infoBills.add(new InfoBill("Cafe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg",3,45000));
        infoBills.add(new InfoBill("Cafe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg",3,45000));
        infoBills.add(new InfoBill("Cafe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg",3,45000));
        adapter=new InfoBillAdapter(this,infoBills);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
