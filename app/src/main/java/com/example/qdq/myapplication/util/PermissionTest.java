package com.example.qdq.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/8/2.
 * Description:运行时权限
 */

public class PermissionTest {
    private static final int CAMERA = 1;

    /**
     * 请求相机权限
     * @param activity
     */
    public static void takePhoto(final Activity activity){
        RxPermissions permissions=new RxPermissions(activity);
        permissions.request(Manifest.permission.CAMERA)
        .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dir=new File(Environment.getDataDirectory(),"adc");
                if(!dir.exists()){
                    dir.mkdirs();
                }
                // 指定存储照片的路径
                Uri imageUri = Uri.fromFile(new File(dir,"1.png"));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                activity.startActivityForResult(intent, CAMERA);
            }
        });


    }
}
