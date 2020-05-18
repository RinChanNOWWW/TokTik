package com.rinchannow.toktik.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import android.util.Log;
import android.widget.Toast;

import java.io.File;



public class downloadService extends Service {

    // 文件保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String APK_dir = "";

    private DownloadManager downloadManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        initAPKDir();// 创建保存路径
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 接收Intent传来的参数:
        // 文件下载路径
        String APK_URL = intent.getStringExtra("apk_url");
        String topic = intent.getStringExtra("topic");

        Log.d("onStartCommand", "APK_URL: " + APK_URL);
        DownFile(APK_URL, topic + ".mp4");

        return super.onStartCommand(intent, flags, startId);
    }

//    private void initAPKDir() {
//        /**
//         * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
//         * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
//         */
//        if (isHasSdcard())// 判断是否插入SD卡
//        {
//            Log.d("InitAPKDir", "hasSdcard");
//            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"; // 保存到SD卡路径下
//            Log.d("InitAPKDir", APK_dir);
//        } else {
//            APK_dir = getApplicationContext().getFilesDir().getAbsolutePath() + "/Download/"; // 保存到app的包名路径下
//        }
//        File destDir = new File(APK_dir);
//        if (!destDir.exists()) {// 判断文件夹是否存在
//            Log.d("InitAPKDir", "make new dir");
//            destDir.mkdirs();
//        }
//    }
//
//    /**
//     * @Description 判断是否插入SD卡
//     */
//    private boolean isHasSdcard() {
//        String status = Environment.getExternalStorageState();
//        if (status.equals(Environment.MEDIA_MOUNTED)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * @param file_url    下载链接
     * @param target_name 保存路径
     */
    private void DownFile(String file_url, String target_name) {
        //下载任务
        //直接使用系统的下载管理器。是不是非常方便
        downloadManager = (DownloadManager)getBaseContext().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(file_url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //通知栏的标题
        request.setTitle("视频下载");
        //显示通知栏的说明
        request.setDescription("测试的广告") ;
        request.setShowRunningNotification(false);//不显示通知栏（若不显示就不需要写上面的内容）
        request.setVisibleInDownloadsUi(true ) ;
        //下载到那个文件夹下，以及命名
        request.setDestinationInExternalPublicDir("Download", target_name);
        //下载的唯一标识，可以用这个标识来控制这个下载的任务enqueue（）开始执行这个任务
        Long reference = downloadManager.enqueue(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}