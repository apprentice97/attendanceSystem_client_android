package com.example.attendancesystem_client_android.teacher;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeacherRecyclerAdapter2 extends RecyclerView.Adapter<TeacherRecyclerAdapter2.ViewHolder> {
    private LayoutInflater mInflater;
    private List<String> mTitles=null;
    private  OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void to(){
        Log.e("hahha", "hhahah");
    }

    public TeacherRecyclerAdapter2(){

    }

    public TeacherRecyclerAdapter2(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.mInflater=LayoutInflater.from(context);
        this.mTitles = new ArrayList<String>();
        this.mTitles.add("暗裔剑魔亚托克斯");
        this.mTitles.add("九尾妖狐阿狸");
        this.mTitles.add("离群之刺阿卡丽");
        //this.mTitles = GlobalVariable.getInstance().getCourse();
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
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
        view.setBackgroundColor(Color.WHITE);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_tv.setText(mTitles.get(position));
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
    //删除数据
    public void removeItem(String data) {
        int position = mTitles.indexOf(data);
        mTitles.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(List<Course> course) {
        mTitles = new ArrayList<String>();
        for(int i = 0; i < course.size(); i ++){
            mTitles.add(course.get(i).toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            item_tv = view.findViewById(R.id.t_f2_item);
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
}
