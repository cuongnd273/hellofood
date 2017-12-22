package com.nguyen.cuong.hellofoods.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class AddCartActivity extends AppCompatActivity implements View.OnClickListener{
    RoundedImageView image;
    TextView name,price,countProduct;
    ImageView up,down;
    int count;
    Button addCart;
    Product product;
    Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);
        this.setFinishOnTouchOutside(false);
        getControls();
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        if(type.equals("add")){
            loadDataToAdd();
        }else if(type.equals("edit")){
            loadDataToEdit();
        }else
            finish();
    }
    void loadDataToAdd(){
        Intent intent=getIntent();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        product= (Product) intent.getSerializableExtra("product");
        Picasso.with(this).load(product.getImage()).into(image);
        name.setText(product.getName());
        price.setText(formatter.format(product.getSale())+" đ");
    }
    void loadDataToEdit(){
        Intent intent=getIntent();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        cart= (Cart) intent.getSerializableExtra("cart");
        Picasso.with(this).load(cart.getImage()).into(image);
        name.setText(cart.getName());
        price.setText(formatter.format(cart.getPrice())+" đ");
        countProduct.setText(String.valueOf(cart.getCount()));
        addCart.setText("Cập nhập");
    }
    void getControls(){
        image= (RoundedImageView) findViewById(R.id.image);
        name= (TextView) findViewById(R.id.name);
        price= (TextView) findViewById(R.id.price);
        countProduct= (TextView) findViewById(R.id.count);
        up= (ImageView) findViewById(R.id.up);
        down= (ImageView) findViewById(R.id.down);
        addCart= (Button) findViewById(R.id.add_cart);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        addCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.up){
            count=Integer.valueOf(countProduct.getText().toString());
            count++;
            countProduct.setText(String.valueOf(count));
        }else if(v.getId()==R.id.down){
            count=Integer.valueOf(countProduct.getText().toString());
            if(count>0){
                count--;
                countProduct.setText(String.valueOf(count));
            }
        }else if(v.getId()==R.id.add_cart){
            if(product!=null){
                if(Integer.valueOf(countProduct.getText().toString())>0){
                    Intent intent=new Intent();
                    Cart cart=new Cart();
                    cart.setId(product.getId());
                    cart.setName(product.getName());
                    cart.setImage(product.getImage());
                    cart.setCount(Integer.parseInt(countProduct.getText().toString()));
                    cart.setPrice(product.getSale());
                    intent.putExtra("cart",cart);
                    setResult(RESULT_OK,intent);
                }
            }else if(cart!=null){
                    Intent intent=new Intent();
                    Cart c=new Cart();
                    c.setId(cart.getId());
                    c.setName(cart.getName());
                    c.setImage(cart.getImage());
                    c.setCount(Integer.parseInt(countProduct.getText().toString()));
                    c.setPrice(cart.getPrice());
                    intent.putExtra("cart",c);
                    setResult(RESULT_OK,intent);
            }
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
