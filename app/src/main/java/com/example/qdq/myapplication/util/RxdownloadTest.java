package com.example.qdq.myapplication.util;

import android.content.Context;
import android.util.Log;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadStatus;


/**
 * Created by Administrator on 2017/8/2.
 * Description:下载
 */

public class RxdownloadTest {
    private static final String DOWNLOAD_URL="http://www.meeidol.com/download/mio.apk";
    private static final String TAG = "RxdownloadTest";
    public static void download(final Context context, final DownloadListener downloadListener){
        RxDownload.getInstance()
                .download(DOWNLOAD_URL,"草.apk",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DownloadStatus>() {
                    @Override
                    public void onCompleted() {
                        downloadListener.onCompleted();
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onNext(DownloadStatus downloadStatus) {
                        downloadListener.download(downloadStatus.getDownloadSize(),downloadStatus.getTotalSize());
                        Log.e(TAG, "download: "+downloadStatus.getDownloadSize()+" total:"+downloadStatus.getTotalSize());
                    }
                });
    }
    public interface DownloadListener{
        void download(long progress,long max);
        void onCompleted();
    }
}
