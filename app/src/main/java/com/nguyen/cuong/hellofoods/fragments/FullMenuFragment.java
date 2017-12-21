package com.nguyen.cuong.hellofoods.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.activities.AddCartActivity;
import com.nguyen.cuong.hellofoods.adapters.ProductAdapter;
import com.nguyen.cuong.hellofoods.databases.CartDao;
import com.nguyen.cuong.hellofoods.models.Cart;
import com.nguyen.cuong.hellofoods.models.Product;

import java.util.ArrayList;

public class FullMenuFragment extends Fragment implements ProductAdapter.ProductEvent{

    private ArrayList<Product> products;
    private ProductAdapter adapter;
    private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_full_menu,container,false);
        listView= (ListView) view.findViewById(R.id.list_full_menu);
        products=new ArrayList<>();
        products.add(new Product("Caffe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg","Cà phê ngon nhất thế giới",20000,10));
        products.add(new Product("Caffe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg","Cà phê ngon nhất thế giới",20000,0));
        products.add(new Product("Caffe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg","Cà phê ngon nhất thế giới",20000,12));
        products.add(new Product("Caffe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg","Cà phê ngon nhất thế giới",20000,10));
        products.add(new Product("Caffe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg","Cà phê ngon nhất thế giới",20000,5));
        products.add(new Product("Caffe","http://kinhdoanhcafe.com/wp-content/uploads/2015/05/huong-dan-cach-lam-ca-phe-capuchino-ngon-tai-nha1.jpg","Cà phê ngon nhất thế giới",20000,0));
        adapter=new ProductAdapter(getContext(),products,this);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void addCart(int position) {
        Intent intent=new Intent(getContext(), AddCartActivity.class);
        intent.putExtra("product",products.get(position));
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getActivity().RESULT_OK){
            CartDao dao=new CartDao(getContext());
            dao.addCart((Cart) data.getSerializableExtra("cart"));
        }
    }
}
