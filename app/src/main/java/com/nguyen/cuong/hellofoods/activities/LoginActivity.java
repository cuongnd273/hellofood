package com.nguyen.cuong.hellofoods.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.nguyen.cuong.hellofoods.utils.AccountInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login, register;
    private EditText email, password;
    private AccountInfo accountInfo;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getControls();
        if (accountInfo.getAccount() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void getControls() {
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        accountInfo = new AccountInfo(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            if (email.getText().length()==0) {
                Toast.makeText(this, "Hãy nhập email", Toast.LENGTH_SHORT).show();
            } else if (password.getText().length() == 0) {
                Toast.makeText(this, "Hãy nhập mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                dialog=new ProgressDialog(this);
                dialog.setMessage("Loading...");
                dialog.show();
                login(email.getText().toString(), password.getText().toString());
            }
        } else if (v.getId() == R.id.register) {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }

    public void login(String email, String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<User> call = api.login(new User(email, password));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if (response.body().getIDTaiKhoan() == 0) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    accountInfo.setAccount(response.body());
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
