package com.nguyen.cuong.hellofoods.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nguyen.cuong.hellofoods.R;

public class AddAddressActivity extends AppCompatActivity {
    EditText address;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        this.setFinishOnTouchOutside(false);
        address= (EditText) findViewById(R.id.address);
        ok= (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.getText().length()==0){
                    Toast.makeText(AddAddressActivity.this,"Chưa nhập địa chỉ nhận hàng",Toast.LENGTH_SHORT);
                }else{
                    Intent intent=new Intent();
                    intent.putExtra("address",address.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
