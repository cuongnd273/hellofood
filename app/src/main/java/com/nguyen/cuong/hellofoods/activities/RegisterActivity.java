package com.nguyen.cuong.hellofoods.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.constants.APIConstant;
import com.nguyen.cuong.hellofoods.interfaces.API;
import com.nguyen.cuong.hellofoods.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    EditText email,password,repeat_password,name,phone;
    Button register;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getControls();
    }
    void getControls(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        repeat_password= (EditText) findViewById(R.id.repeat_password);
        name= (EditText) findViewById(R.id.name);
        phone= (EditText) findViewById(R.id.phone);
        register= (Button) findViewById(R.id.register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng ký");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        register.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(email.getText().length()==0){
            Toast.makeText(this, "Hãy nhập email", Toast.LENGTH_SHORT).show();
        }else if(password.getText().length()<6){
            Toast.makeText(this, "Hãy nhập mật khẩu lớn hơn 5 ký tự", Toast.LENGTH_SHORT).show();
        }else if(repeat_password.getText().equals(password.getText().toString())){
            Toast.makeText(this, "Hãy nhập lại đúng mật khẩu", Toast.LENGTH_SHORT).show();
        }else if(name.getText().length()==0){
            Toast.makeText(this, "Hãy nhập họ tên", Toast.LENGTH_SHORT).show();
        }else if(phone.getText().length()==0){
            Toast.makeText(this, "Hãy nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else{
            dialog=new ProgressDialog(this);
            dialog.setMessage("Loading...");
            dialog.show();
            register(name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString());
        }
    }
    void register(String name,String email,String phone,String password){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<User> call = api.register(name,email,phone,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if(response.body().getIDTaiKhoan()==0){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
