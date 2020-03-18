package com.example.attendancesystem_client_android.teacher;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Course;
import com.example.attendancesystem_client_android.bean.CourseAttendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeacherRecyclerAdapter2 extends RecyclerView.Adapter<TeacherRecyclerAdapter2.ViewHolder> {
    private LayoutInflater mInflater;
    private List<String> mTitles=null;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x0001:
                    notifyDataSetChanged();
                case 0x0002:
                    System.out.println("hello world");
                    break;
            }
            return false;
        }
    });


    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseInformation;
        TextView callName;
        ViewHolder(View view){
            super(view);
            courseInformation = view.findViewById(R.id.courseInformation);
            callName = view.findViewById(R.id.callName);
        }
    }

    /**
     * 自定义RecyclerView 中item view点击回调方法
     */
    interface OnRecyclerItemClickListener{
        /**
         * item view 回调方法
         * @param view  被点击的view
         * @param position 点击索引
         */
        void onItemClick(View view, int position);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }


    //构造函数
    public TeacherRecyclerAdapter2(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.mInflater=LayoutInflater.from(context);
        this.mTitles = new ArrayList<String>();
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.teacher_fragment_2_item, parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecyclerItemClickListener!=null){
                    onRecyclerItemClickListener.onItemClick(view, (int)view.getTag());
                }
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.courseInformation.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                int position = viewHolder.getPosition();
//                Log.e("courseInformation", "" + position);
//            }
//        });
        viewHolder.callName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getPosition();
                        Log.e("TeacherReadapter2", "点名");
                        GlobalVariable.getInstance().getCourse().get(position).getCourse_id();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("action", "call_name");
                        map.put("teacher_id", GlobalVariable.getInstance().getAccount());
                        map.put("course_id",GlobalVariable.getInstance().getCourse().get(GlobalVariable.getInstance().getTeacher_course_id()).getCourse_id());
                        OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                    }
                }).start();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.courseInformation.setText(mTitles.get(position));
        holder.callName.setText("点名");
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public void addItem(String data, int position) {
        mTitles.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(String data) {
        int position = mTitles.indexOf(data);
        mTitles.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(List<Course> course) {
        mTitles = new ArrayList<String>();
        for (int i = 0; i < course.size(); i++) {
            mTitles.add(course.get(i).getCourse_id() + " " + course.get(i).getName());
        }
        Message message = new Message();
        message.what = 0x0001;
        handler.sendMessage(message);
    }
}
