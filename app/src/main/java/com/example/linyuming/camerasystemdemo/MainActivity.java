package com.example.linyuming.camerasystemdemo;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private String imagepath;
    private ImageView imageView;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"打开系统照相机");
        menu.add(0,1,0,"打开系统项目，显示拍照结果");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Intent intent = new Intent();//调用照相机
                intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
                startActivity(intent);
                break;
            case 2:
                takephoto();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void takephoto() {
        imagepath = android.os.Environment.getExternalStorageDirectory().getPath();
        imageView = (ImageView) this.findViewById(R.id.imageview);
        //以当前时间为相片名字，其中yyyy表示年，MM表示月，dd表示日，hh表示时,mm表示分，ss表示秒
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前系统的时间
        String str = formatter.format(curDate);
        imagepath = imagepath+"/"+str+"jpeg";
        //创建文件
        file = new File(imagepath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        //打开系统照相机
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(file.exists()){
            imageView.setImageURI(Uri.fromFile(file));
        }
    }
}
