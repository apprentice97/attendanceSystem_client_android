package com.example.attendancesystem_client_android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.attendancesystem_client_android.manager.ManagerMain;
import com.example.attendancesystem_client_android.student.StudentMain;
import com.example.attendancesystem_client_android.teacher.TeacherMain;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_account;
    EditText et_password;
    Button btn_login;
    TextView tv_register;
    RadioButton rb_student;
    RadioButton rb_teacher;
    RadioButton rb_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView(){
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        rb_student = findViewById(R.id.rbStudent);
        rb_teacher = findViewById(R.id.rbTeacher);
        rb_manager = findViewById(R.id.rbManager);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_login){
            boolean internet = false;
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext() .getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    internet = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    internet = true;
                }
            }
            if(internet){
                GlobalVariable.getInstance().setAccount(String.valueOf(et_account.getText()));
                GlobalVariable.getInstance().setPassword(String.valueOf(et_password.getText()));
                GlobalVariable.getInstance().setType(getType());
                String account =  GlobalVariable.getInstance().getAccount();
                String password =  GlobalVariable.getInstance().getPassword();
                int type = GlobalVariable.getInstance().getType();
                Intent intent = new Intent("android.intent.action.MAIN");
                if(type == 1){
                    intent.setClass(MainActivity.this, StudentMain.class);
                    OkHttp.login(account, password, 1,intent, this);
                }
                else if(type == 2){
                    intent.setClass(MainActivity.this, TeacherMain.class);
                    OkHttp.login(account, password, 2,intent, this);
                }
                else if(type == 3){
                    intent.setClass(MainActivity.this, ManagerMain.class);
                    OkHttp.login(account, password, 3,intent, this);
                }
            }
            else{
                Toast.makeText(MainActivity.this, "请检查网络连接！", Toast.LENGTH_LONG).show();
            }
        }
        else if(view == tv_register){
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClass(MainActivity.this, Register.class);
            //startActivity(intent);
        }
    }

    int getType(){
        if(rb_student.isChecked()) return 1;
        else if(rb_teacher.isChecked()) return 2;
        else return 3;
    }
}
