package com.nguyen.cuong.hellofoods.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.User;
import com.nguyen.cuong.hellofoods.utils.AccountInfo;
import com.nguyen.cuong.hellofoods.utils.ImageFilePath;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cuong on 11/28/2017.
 */

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private Button updateInfo;
    private EditText name, phone;
    boolean statusEdit = false;
    private CircleImageView avatar;
    private AccountInfo accountInfo;
    private User user;
    ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getControls();
        getData();
    }

    void getControls() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        updateInfo = (Button) findViewById(R.id.update_info);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        accountInfo=new AccountInfo(this);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin tài khoản");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        updateInfo.setOnClickListener(this);
//        avatar.setOnClickListener(this);
    }

    void getData() {
        user=accountInfo.getAccount();
        name.setText(user.getTenTaiKhoan());
        phone.setText(user.getSoDienThoai());
//        Picasso.with(this).load(account.getAvatar()).into(avatar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update_info) {
            if (statusEdit == false) {
                statusEdit = true;
                name.setEnabled(true);
                phone.setEnabled(true);
                updateInfo.setText("Lưu");
            } else {
                statusEdit = false;
                name.setEnabled(false);
                phone.setEnabled(false);
                updateInfo.setText("Thay đổi thông tin");
                update(user.getIDTaiKhoan(),name.getText().toString(),phone.getText().toString());
            }
        } else if (v.getId() == R.id.avatar) {
            checkPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri uri = data.getData();
            String realPath = ImageFilePath.getPath(AccountActivity.this, data.getData());
            Log.i("onActivityResult", realPath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                avatar.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void choiceImage(){
        Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
        mediaChooser.setType("image/*");
        startActivityForResult(mediaChooser, 1);
    }
    private void checkPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            choiceImage();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    choiceImage();
                }
                break;

            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    void update(int id,String name,String phone){
        dialog.show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<User> call=api.update(id,name,phone);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null && response.body().getIDTaiKhoan()!=0){
                    accountInfo.setAccount(response.body());
                    getData();
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    getData();
                    Toast.makeText(AccountActivity.this, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AccountActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
