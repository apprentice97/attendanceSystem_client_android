package com.example.attendancesystem_client_android.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.Picture;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.ToastChildThread;
import com.example.attendancesystem_client_android.bean.Attendance;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentSignIn extends AppCompatActivity implements View.OnClickListener {
    private Button submit;
    private int type;
    private ImageView imageView;
    private Uri imageUri = null;
    private Boolean takenPhoto;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_sign_in);
        takenPhoto=false;
        bindView();
        draw();
    }

    private void bindView(){
        mContext = StudentSignIn.this;
        ArrayList<String> list = new ArrayList<>();
        list.add("出勤");
        list.add("病假");
        list.add("事假");
        list.add("缺勤");
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        imageView = findViewById(R.id.camera);
        imageView.setOnClickListener(this);
    }
    private void draw(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Attendance attendance = GlobalVariable.getInstance().getStudent_message().get(GlobalVariable.getInstance().getStudent_position());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == submit){
            if(takenPhoto){
                attendanceWithPhoto();
            }
            else{
                Toast.makeText(this, "请先拍照!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == imageView){
            createUploadFile();
            takePhoto();
            // todo 改成从相机选择照片
//            takenPhoto = true;
//            getPhotoFromAlbum();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        takenPhoto = true;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap compressBitmap = Picture.compressBitmap(bitmap);
                        bitmapSaveToNative(compressBitmap);
                        imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    handleImageOnKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    private void createUploadFile(){
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this,
                    "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        GlobalVariable.getInstance().setFile(outputImage);
    }


    private void bitmapSaveToNative(Bitmap bitmap){
        File f = new File(getExternalCacheDir(), "output_image.jpg");
        try{
          FileOutputStream out = new FileOutputStream(f);
          bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
          out.flush();
          out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void takePhoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 0);
    }

    private void attendanceWithPhoto(){
        new Thread(() -> {
            Attendance attendance = GlobalVariable.getInstance().getStudent_message().get(GlobalVariable.getInstance().getStudent_position());
            Map<String, String> map = new HashMap<String, String>();
            map.put("action", "student_sign_in");
            map.put("student_id", attendance.getStudent_id());
            map.put("course_id", attendance.getCourse_id());
            map.put("teacher_id", attendance.getTeacher_id());
            map.put("serial_number", attendance.getSerial_number());
            map.put("type",""+type);
            OkHttp.Response response = OkHttp.httpPostForm("http://192.168.137.1/mgr/student/", map, imageUri.getPath());
            assert response != null;
            Map content = (Map) JSONObject.parse(response.content);
            String code = Objects.requireNonNull(content.get("ret")).toString();
            Log.e("StudentSignIn", code);
            if(code.equals("0")){
                setResult(1,new Intent());
                finish();
            }
            else if(code.equals("1")){
                ToastChildThread.show(mContext, "人脸识别失败，请重新识别！", Toast.LENGTH_SHORT);
            }
            else if(code.equals("2")){
                ToastChildThread.show(mContext, "超过签到时间了！", Toast.LENGTH_SHORT);
            }
        }).start();
    }

    private void attendanceWithOutPhoto(){
        new Thread(() -> {
            Attendance attendance = GlobalVariable.getInstance().getStudent_message().get(GlobalVariable.getInstance().getStudent_position());
            Map<String, String> map = new HashMap<String, String>();
            map.put("action", "student_sign_in");
            map.put("student_id", attendance.getStudent_id());
            map.put("course_id", attendance.getCourse_id());
            map.put("teacher_id", attendance.getTeacher_id());
            map.put("serial_number", attendance.getSerial_number());
            map.put("type",""+type);
            OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/student/", map);
            Map content = (Map) JSONObject.parse(response.content);
            String string = Objects.requireNonNull(content.get("data")).toString();
            Log.e("StudentSign",string);
            setResult(0,new Intent());
            finish();
        }).start();
    }

    private void getPhotoFromAlbum() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void handleImageOnKitKat(Intent data) {
        String imagesPath = null;
        Uri uri = data.getData();
        assert uri != null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagesPath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagesPath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagesPath = uri.getPath();
        }
        displayImage(imagesPath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagesPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagesPath);
        Bitmap compressBitmap = Picture.compressBitmap(bitmap);
        bitmapSaveToNative(compressBitmap);
        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}



