package com.nguyen.cuong.hellofoods.interfaces;

import com.nguyen.cuong.hellofoods.models.Bill;
import com.nguyen.cuong.hellofoods.models.InfoBill;
import com.nguyen.cuong.hellofoods.models.Order;
import com.nguyen.cuong.hellofoods.models.Product;
import com.nguyen.cuong.hellofoods.models.Restaurant;
import com.nguyen.cuong.hellofoods.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cuong on 12/22/2017.
 */

public interface API {
    @POST("LoginPost")
    Call<User> login(@Body User user);

    @GET("AccountInsert")
    Call<User> register(@Query("tenTaiKhoan") String hoTen, @Query("email") String email, @Query("soDienThoai") String soDienThoai, @Query("matKhau") String matKhau);

    @GET("getAllMonAn")
    Call<List<Product>> getAll(@Query("numberPage") int page);

    @GET("Histories")
    Call<List<Bill>> getHistory(@Query("idAccount") int taiKhoan);

    @GET("DetailHistory")
    Call<List<InfoBill>> getInfoBill(@Query("idDonHang") int id);

    @GET("AccountUpdate")
    Call<User> update(@Query("idTaiKhoan") int maTK,@Query("tenTaiKhoan") String hoten,@Query("soDienThoai") String sodienthoai);

    @GET("NhaHangs")
    Call<List<Restaurant>> getRestaurants();

    @POST("Order")
    Call<String> order(@Header("Content-Type") String content_type, @Body Order order);
}
