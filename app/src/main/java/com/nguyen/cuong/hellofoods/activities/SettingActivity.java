package com.nguyen.cuong.hellofoods.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.adapters.SettingAdapter;
import com.nguyen.cuong.hellofoods.models.Setting;

import java.util.ArrayList;

/**
 * Created by cuong on 11/28/2017.
 */

public class SettingActivity extends AppCompatActivity {
    private ArrayList<Setting> settings;
    private SettingAdapter adapter;
    private ListView listView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getControls();
    }
    void getControls(){
        listView= (ListView) findViewById(R.id.list_setting);
        appBarLayout= (AppBarLayout) findViewById(R.id.appBar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cài đặt");
        settings=new ArrayList<>();
        settings.add(new Setting(1,"Ngôn ngữ"));
        adapter=new SettingAdapter(this,settings);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
