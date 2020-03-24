package com.example.attendancesystem_client_android.student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.Picture;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.bean.Course;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentSignIn extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView textView;
    private Spinner spinner_type;
    private ArrayAdapter<String> spinner_type_adapter;
    private Button submit;
    private int type;
    private ImageView imageView;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_sign_in);
        bindView();
        draw();
    }

    private void bindView(){
        ArrayList<String> list = new ArrayList<>();
        list.add("出勤");
        list.add("病假");
        list.add("事假");
        list.add("缺勤");
        textView = findViewById(R.id.course_information);
        spinner_type = findViewById(R.id.type);
        spinner_type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(spinner_type_adapter);
        spinner_type.setOnItemSelectedListener(this);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        imageView = findViewById(R.id.camera);
        imageView.setOnClickListener(this);
        //spinner_type.setOnItemClickListener(this);


    }
    private void draw(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Attendance attendance = GlobalVariable.getInstance().getStudent_message().get(GlobalVariable.getInstance().getStudent_position());
                String text = "课程号:" +  attendance.getCourse_id() + "    课程名:" + attendance.getCourse_name() + "    第" +
                        attendance.getSerial_number() + "次点名";
                textView.setText(text);
//                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
//                if (Build.VERSION.SDK_INT >= 24) {
//                    imageUri = FileProvider.getUriForFile(getApplicationContext(),
//                            "com.example.cameraalbumtest.fileprovider", outputImage);
//                } else {
//                    imageUri = Uri.fromFile(outputImage);
//                }
//                Log.e("StudentSignIn...", imageUri.getPath());
//                Bitmap bitmap = null;
//                try {
//                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Log.e("StudentSignIn...", imageUri.getPath());
//                imageView.setImageBitmap(bitmap);
//                Log.e("StudentSignIn...", imageUri.getPath());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == submit){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Attendance attendance = GlobalVariable.getInstance().getStudent_message().get(GlobalVariable.getInstance().getStudent_position());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("action", "student_sign_in");
                    map.put("student_id", attendance.getStudent_id());
                    map.put("course_id", attendance.getCourse_id());
                    map.put("teacher_id", attendance.getTeacher_id());
                    map.put("serial_number", attendance.getSerial_number());
                    map.put("type",""+type);

                    OkHttp.Response response;
                    if(imageUri == null){
                        response = OkHttp.httpGetForm("http://192.168.137.1/mgr/student/", map);
                    }
                    else{
                        response = OkHttp.httpPostForm("http://192.168.137.1/mgr/student/", map, imageUri.getPath());
                    }
                    assert response != null;
                    Map content = (Map) JSONObject.parse(response.content);
                    String string = Objects.requireNonNull(content.get("data")).toString();
                    Log.e("StudentSign",string);
                    setResult(0,new Intent());
                    finish();
                }
            }).start();
        }
        else if(v == imageView){
            takePhoto();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        type = 0;
    }

    private void takePhoto(){
        //创建File对象，用于储存拍照后的图片
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
        //启动相机程序
        GlobalVariable.getInstance().setFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 0);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap compressBitmap = Picture.compressBitmap(bitmap);
                        bitmapSaveToNative(compressBitmap);
                        imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
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
}



