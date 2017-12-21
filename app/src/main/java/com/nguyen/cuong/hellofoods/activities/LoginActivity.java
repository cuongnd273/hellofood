package com.nguyen.cuong.hellofoods.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Account;
import com.nguyen.cuong.hellofoods.utils.AccountInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button login,register,loginFacebook;
    private EditText email,password;
    private AccountInfo accountInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getControls();
        if(accountInfo.getAccount()!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void getControls(){
        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.register);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        loginFacebook= (Button) findViewById(R.id.loginFacebook);
        accountInfo=new AccountInfo(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        loginFacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login){
            Account account=new Account(27,"Nguyễn Đăng Cương","01688790906","Vũ Hội, Vũ Thư, Thái Bình","cuongit95@gmail.com","https://scontent.fhan2-1.fna.fbcdn.net/v/t1.0-9/23621737_952349561583213_4407301914780158662_n.jpg?oh=d2db00101441ea3371ef05bc3d517bcd&oe=5AC82B65");
            AccountInfo info=new AccountInfo(this);
            info.setAccount(account);
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else if(v.getId()==R.id.register){
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}
