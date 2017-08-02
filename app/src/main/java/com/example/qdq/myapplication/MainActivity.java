package com.example.qdq.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qdq.myapplication.util.PermissionTest;
import com.example.qdq.myapplication.util.RxdownloadTest;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView photoTv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoTv = (ImageView) findViewById(R.id.photo_iv);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
    }
    public void click(View view){
        takePhoto();
    }
    private void download(){
        RxdownloadTest.download(this, new RxdownloadTest.DownloadListener() {

            @Override
            public void download(long progress, long max) {
                progressBar.setProgress((int) progress);
                progressBar.setMax((int) max);
            }

            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void takePhoto(){
        PermissionTest.takePhoto(this);
    }
}
