package com.example.mylog;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String extpath;
    private EditText mEdit;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mEdit = (EditText) findViewById(R.id.EditText);
        getSDPath();
//        List<String> extPaths = getExtSDpath();
//        for (String path : extPaths) {
//            Log.e("", "外置SD卡的路径为：" + path);
//        }
        extpath = getExtSDpath();//外置SD的路径
        Log.e("", "外置SD卡的路径为extpath=" + extpath);
    }

    //获取外置SD卡路径
    public String getExtSDpath() {
        /**
         List<string> lResult = new ArrayList<string>();
         try {
         Runtime rt = Runtime.getRuntime();
         Process proc = rt.exec("mount");
         InputStream is = proc.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader br = new BufferedReader(isr);
         String line;
         while ((line = br.readLine()) != null) {
         if (line.contains("extSdCard"))
         {
         String [] arr = line.split(" ");
         String path = arr[1];
         File file = new File(path);
         if (file.isDirectory())
         {
         lResult.add(path);
         }
         }
         }
         isr.close();
         } catch (Exception e) {
         }
         return lResult;
         }

         * */
//        List<String> lReslut = new ArrayList<String>();
//        Runtime rt = Runtime.getRuntime();
//        try {
//            Process proc = rt.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.contains("extSdCard")) {
//                    String[] arr = line.split(" ");
//                    String path = arr[1];
//                    File file = new File(path);
//                    if (file.isDirectory()) {
//                        lReslut.add(path);
//                    }
//                }
//            }
//            isr.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /**
         *  public String getPath2() {
         String sdcard_path = null;
         String sd_default = Environment.getExternalStorageDirectory()
         .getAbsolutePath();
         Log.d("text", sd_default);
         if (sd_default.endsWith("/")) {
         sd_default = sd_default.substring(0, sd_default.length() - 1);
         }
         // 得到路径
         try {
         Runtime runtime = Runtime.getRuntime();
         Process proc = runtime.exec("mount");
         InputStream is = proc.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         String line;
         BufferedReader br = new BufferedReader(isr);
         while ((line = br.readLine()) != null) {
         if (line.contains("secure"))
         continue;
         if (line.contains("asec"))
         continue;
         if (line.contains("fat") && line.contains("/mnt/")) {
         String columns[] = line.split(" ");
         if (columns != null && columns.length > 1) {
         if (sd_default.trim().equals(columns[1].trim())) {
         continue;
         }
         sdcard_path = columns[1];
         }
         } else if (line.contains("fuse") && line.contains("/mnt/")) {
         String columns[] = line.split(" ");
         if (columns != null && columns.length > 1) {
         if (sd_default.trim().equals(columns[1].trim())) {
         continue;
         }
         sdcard_path = columns[1];
         }
         }
         }
         } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }
         Log.d("text", sdcard_path);
         return sdcard_path;
         }
         }
         * */
        String sdCard_path = null;
        String sdCard_default = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (sdCard_default.equals("/")) {
            sdCard_default = sdCard_default.substring(0, sdCard_default.length() - 1);
        }
        //路径
        Runtime runtime = Runtime.getRuntime();
        try {
            java.lang.Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line = null;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String colums[] = line.split(" ");
                    if (colums != null && colums.length > 1) {
                        if (sdCard_default.trim().equals(colums[1].trim())) {
                            continue;
                        }
                        sdCard_path = colums[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String colums[] = line.split(" ");
                    if (colums != null && colums.length > 1) {
                        if (sdCard_default.trim().equals(colums[1].trim())) {
                            continue;
                        }
                        sdCard_path = colums[1];
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sdCard_path;
    }

    //获取内置SD卡路径
    public void getSDPath() {
        /**
         * File sdDir = null;
         boolean sdCardExist = Environment.getExternalStorageState()
         .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
         if (sdCardExist)
         {
         sdDir = Environment.getExternalStorageDirectory();//获取跟目录
         }
         return sdDir.toString();
         * */
        File sdDir = null;
        boolean sdCardExit = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExit) {//判断SD卡是否存在，如果存在获取SD卡的根目录
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
        }
        Log.e("", "内置SD卡的根目录：" + sdDir);
    }

    //保存数据
    public void saveData(String str) {
        FileOutputStream fos = null;
        File file = new File(extpath);
        FileWriter fw = null;
        PrintWriter log = null;
        try {
            fos = openFileOutput("data", Context.MODE_APPEND);//得到写入数据的文件在原有的数据后面继续追加内容
            fw = new FileWriter("/mnt/media_rw/sdcard/data.txt", true);//无法写入数据到外置SD卡中
            log = new PrintWriter(fw);
            log = new PrintWriter(fos);
            log.println("-------------------------");
            log.println(str);
            Date date = new Date();
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = mFormat.format(date);
            log.println("---" + time + "---");
            log.flush();
            log.close();//关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //在程序退出之前保存数据
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String str = mEdit.getText().toString();
        saveData("用户名:" + str);
        Log.e("", "数据保存成功");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mylog/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mylog/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //读取data文件中的数据
    public void readData() {

    }
}
