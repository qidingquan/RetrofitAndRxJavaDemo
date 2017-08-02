package com.example.qdq.myapplication.util;

import android.content.Context;

import com.example.qdq.myapplication.api.IUserBiz;
import com.example.qdq.myapplication.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/8/1.
 */

public class RetrofitTest {
    private Context context;

    public RetrofitTest(Context context) {
        this.context = context;
    }
    public void test1() throws IOException {
        //创建retrofit实例
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.31.242:8080/springmvc_users/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //
        IUserBiz userBiz=retrofit.create(IUserBiz.class);
        Call<List<User>> call=userBiz.getUserList("qdq");
        //异步请求
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        /*//同步请求
        Response<List<User>> response=call.execute();
        List<User> userList=response.body();*/
    }
    public void test2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.242:8080/springmvc_users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);
        //使用retrofit
        userBiz.getUser("1", new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                //如果此时需要在数据库中查找是否存在该用户，需要开启工作线程查询，再切换到主线程中修改ui,代码不易维护
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        //使用retrofit+rxjava
        userBiz.getUser("1")
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        //在数据库中查找是否存在该用户 链式结构代码逻辑更清晰
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        //在主线程更新ui
                    }
                });
    }
}
