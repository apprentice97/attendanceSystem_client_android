package com.example.attendancesystem_client_android.student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.Picture;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.ToastChildThread;
import com.example.attendancesystem_client_android.bean.Attendance;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.RandomAccess;

public class StudentRequestForLeave extends AppCompatActivity implements View.OnClickListener {
    private TextView serial_number;
    private TextView student;
    private TextView course;
    private TextView time;
    private RadioGroup radio_group;
    private RadioButton sick_leave;
    private RadioButton affair_leave;
    private int position;
    private Attendance attendance;
    private int attendance_type;
    private Button picture_file;
    private Button submit;
    private Boolean select_picture;
    private Uri imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_request_for_leave);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initView();
        draw();
    }

    private void initView(){
        serial_number = findViewById(R.id.serial_number);
        student = findViewById(R.id.student);
        course = findViewById(R.id.course);
        time = findViewById(R.id.time);

        /*
        * type == 0 出勤
        * type == 1 病假
        * type == 2 事假
        * type == 3 缺勤
        * modify == 0 或者 modify == 2 默认为缺勤
        * */
        attendance_type = 1;
        radio_group = findViewById(R.id.radio_group);
        radio_group.setOnClickListener(this);
        sick_leave = findViewById(R.id.sick_leave);
        affair_leave = findViewById(R.id.affair_leave);
        picture_file = findViewById(R.id.picture_file);
        picture_file.setOnClickListener(this);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        select_picture = false;
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                position = GlobalVariable.getInstance().getStudent_attendance_position();
                attendance = GlobalVariable.getInstance().getStudent_attendance().get(position);
                serial_number.setText(attendance.getSerial_number());
                student.setText(attendance.getStudent_name());
                course.setText(attendance.getCourse_name());
                time.setText(attendance.getTime());
                createUploadFile();
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        if(v == picture_file){
            getPhotoFromAlbum();
        }
        else if(v == submit){
            if(!select_picture){
                Toast.makeText(getBaseContext(), "请先选择上传文件", Toast.LENGTH_SHORT).show();
            }
            else{
                new Thread(() -> {
                        attendance.setType(attendance_type+"");
                        Attendance attendance = GlobalVariable.getInstance().getStudent_attendance().get(GlobalVariable.getInstance().getStudent_attendance_position());
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("action", "request_for_leave");
                        map.put("serial_number", attendance.getSerial_number());
                        map.put("student_id",attendance.getStudent_id());
                        map.put("teacher_id",attendance.getTeacher_id());
                        map.put("course_id",attendance.getCourse_id());
                        map.put("modify","2");
                        map.put("type",attendance.getType());
                        OkHttp.Response response = OkHttp.httpPostForm("http://192.168.137.1/mgr/student/", map, imageUri.getPath());
                        assert response != null;
                        Map content = (Map) JSONObject.parse(response.content);
                        String code = Objects.requireNonNull(content.get("ret")).toString();
                        Log.e("StudentSignIn", code);
                        setResult(1,new Intent());
                        finish();
                    }).start();
                }
            }

    }

    private void getPhotoFromAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
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
        picture_file.setText("选择请假条成功！");

    }

    private void bitmapSaveToNative(Bitmap bitmap){
        select_picture = true;
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

    private int getType(){
        if(sick_leave.isChecked()) return 1;
        else if(affair_leave.isChecked()) return 2;
        else return 3;
    }
}
