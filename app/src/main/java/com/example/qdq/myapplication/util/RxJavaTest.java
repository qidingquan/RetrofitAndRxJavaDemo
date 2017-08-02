package com.example.qdq.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.qdq.myapplication.model.Course;
import com.example.qdq.myapplication.R;
import com.example.qdq.myapplication.model.Student;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/1.
 */

public class RxJavaTest {
    private Context context;
    public RxJavaTest(Context context) {
        this.context=context;
    }

    private void test4() {

        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            courses.add(new Course("课程" + i));
        }
        Student student1 = new Student("张三", 20);
        student1.setCourses(courses);
        Student student2 = new Student("李四", 21);
        student2.setCourses(courses);
        Student student3 = new Student("王五", 22);
        student3.setCourses(courses);
        Student[] students = {student1, student2, student3};

        Observable.from(students)
                /**
                 *flatMap() 的原理是这样的：
                 * 1. 使用传入的事件对象创建一个 Observable 对象；
                 * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
                 * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法
                 */.flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return Observable.from(student.getCourses());
            }
        }).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {
                Log.e(TAG, "onNext: " + course.getName());
            }
        });
    }

    private void test3() {
        Observable.just(R.mipmap.ic_launcher)
                .map(new Func1<Integer, Bitmap>() {//map() 方法将参数中的 Integer 对象转换成一个 Bitmap 对象后返回
                    @Override
                    public Bitmap call(Integer integer) {
                        return BitmapFactory.decodeResource(context.getResources(), integer);
                    }
                }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {

            }
        });
    }

    private void test2() {
        Observable<Drawable> observable = Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_launcher_round);
                subscriber.onNext(drawable);
                Log.e(TAG, "call: " + Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())//订阅时发生在io线程
                .observeOn(Schedulers.io());//Subscriber回调在主线程
        observable.subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                Log.e(TAG, "call: " + Thread.currentThread().getName());
            }
        });
    }

    private void test1() {
        //观察者
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
            }
        };
      /*  //（一）被观察者
        Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                observer.onNext("1");
                observer.onNext("2");
                observer.onNext("3");
                observer.onCompleted();

            }
        });*/
     /*   //（二）将传入的参数依次发送出来。
        Observable observable=Observable.just("1","2","3");*/
        // （三）将传入的数组或 Iterable 拆分成具体对象后，依次发送出来
        String[] data = {"1", "2", "3"};
        Observable observable = Observable.from(data);
        Action1<String> nextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        };
        observable.subscribe(nextAction);
    }
}
