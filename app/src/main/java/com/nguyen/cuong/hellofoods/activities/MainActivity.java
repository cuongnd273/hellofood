package com.nguyen.cuong.hellofoods.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.adapters.NavMenuAdapter;
import com.nguyen.cuong.hellofoods.databases.CartDao;
import com.nguyen.cuong.hellofoods.fragments.CartFragment;
import com.nguyen.cuong.hellofoods.fragments.FullMenuFragment;
import com.nguyen.cuong.hellofoods.fragments.HistoryFragment;
import com.nguyen.cuong.hellofoods.models.NavMenu;
import com.nguyen.cuong.hellofoods.models.User;
import com.nguyen.cuong.hellofoods.utils.AccountInfo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ListView listMenu;
    private CircleImageView avatar;
    private TextView name;
    private ArrayList<NavMenu> navMenus;
    private NavMenuAdapter adapter;
    private AccountInfo accountInfo;
    BottomNavigationView bottomNavigationView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControls();
        CartDao dao = new CartDao(this);
        dao.open();
    }

    void getControls() {
        FullMenuFragment fragment = new FullMenuFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main, fragment).commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listMenu = (ListView) findViewById(R.id.nav_list);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_menu);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        accountInfo = new AccountInfo(this);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        loadMenu();
        listMenu.setOnItemClickListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    public void loadMenu() {
        navMenus = new ArrayList<>();
        navMenus.add(new NavMenu(R.drawable.ic_home, "Menu", true));
        navMenus.add(new NavMenu(R.drawable.ic_shopping_cart, "Giỏ hàng", false));
        navMenus.add(new NavMenu(R.drawable.ic_history, "Lịch sử đặt hàng"));
        navMenus.add(new NavMenu(R.drawable.ic_user, "Tài khoản"));
        navMenus.add(new NavMenu(R.drawable.ic_setting, "Cài đặt"));
        navMenus.add(new NavMenu(R.drawable.ic_logout, "Đăng xuất", false, false));
        adapter = new NavMenuAdapter(navMenus, this);
        listMenu.setAdapter(adapter);
        user = accountInfo.getAccount();
//        Picasso.with(this).load(account.getAvatar()).into(avatar);
        name.setText(user.getTenTaiKhoan());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateMenu(position);
        drawerLayout.closeDrawer(Gravity.START);
        selectMenu(position);
    }
    void updateMenu(int position){
        if (navMenus.get(position).isOpenUI()) {
            for (int i = 0; i < navMenus.size(); i++) {
                NavMenu nav = navMenus.get(i);
                if (i == position)
                    nav.setSelected(true);
                else
                    nav.setSelected(false);
                navMenus.set(i, nav);
            }
            adapter.notifyDataSetChanged();
        }
    }
    private void selectMenu(int positon) {
        Fragment fragment = null;
        FragmentTransaction fragmentTransaction;
        switch (positon) {
            case 0:
                fragment = new FullMenuFragment();
                bottomNavigationView.setSelectedItemId(R.id.bottom_home);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main, fragment).commit();
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.bottom_cart);
                fragment = new CartFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main, fragment).commit();
                break;
            case 2:
                fragment = new HistoryFragment();
                bottomNavigationView.setSelectedItemId(R.id.bottom_history);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main, fragment).commit();
                break;
            case 3:
                Intent intentAcc = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intentAcc);
                break;
            case 4:
                Intent intentSet = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intentSet);
                break;
            case 5:
                logOut();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        FragmentTransaction fragmentTransaction;
        switch (item.getItemId()) {
            case R.id.bottom_home:
                fragment = new FullMenuFragment();
                updateMenu(0);
                break;
            case R.id.bottom_cart:
                fragment = new CartFragment();
                updateMenu(1);
                break;
            case R.id.bottom_history:
                fragment = new HistoryFragment();
                updateMenu(2);
                break;
        }
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main, fragment).commit();
        }
        return true;
    }

    void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn có muốn đăng xuất không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                accountInfo.deleteAccount();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
