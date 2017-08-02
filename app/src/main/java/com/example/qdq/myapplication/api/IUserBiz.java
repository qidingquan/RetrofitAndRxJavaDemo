package com.example.qdq.myapplication.api;

import com.example.qdq.myapplication.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qdq on 2017/8/1.
 * Description:用户请求接口
 */

public interface IUserBiz {
//    http://192.168.1.102:8080/springmvc_users/user/zhy
//    http://192.168.1.102:8080/springmvc_users/user/lmj
    @GET("username")//@GET表示gei请求 value中的值和baseUrl组成完整路径
    //{username}当做占位符，username参数进行替换。
    Call<List<User>> getUserList(@Path("username")String username);
    //查询参数设置
    //http://baseurl/users?sex=男
    //http://baseurl/users?sex=女
    @GET("users")//url
    Call<List<User>> getBySexUserList(@Query("sex")String sex);
    @POST("add")//使用Body来代表参数对象
    Call<List<User>> addUser(@Body User user);
    @POST("login")//url
    @FormUrlEncoded//以表单方式提交
    Call<User> login(@Field("username")String username,@Field("password")String password);
    @Multipart//单文件上传 允许多个part
    @POST("register")//url
    //MultipartBody 上传文件 其它键值对
    Call<User> registerUser(@Part MultipartBody photo, @Part("username")RequestBody username,@Part("password")RequestBody password);

    @GET("/user")//使用retrofit
    public void getUser(@Query("userId")String userId, Callback<User> callback);
    @GET("/user")//使用retrofit+rxjava
    public Observable<User> getUser(@Query("userId")String userId);
}
