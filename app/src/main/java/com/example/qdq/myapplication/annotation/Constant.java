package com.example.qdq.myapplication.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/8/1.
 * Description:常量类
 */
@StringDef({
        Constant.CODE_SALE,Constant.CODE_LEASE
})
@Retention(RetentionPolicy.CLASS)
public @interface Constant {
    String CODE_SALE = "1";        // 出售
    String CODE_LEASE = "2";       // 整租
}
