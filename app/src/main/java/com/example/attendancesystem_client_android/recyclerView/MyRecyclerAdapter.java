package com.example.attendancesystem_client_android.recyclerView;

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

import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Course;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<String> mTitles=null;
    private  OnRecyclerItemClickListener onRecyclerItemClickListener;
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 0x0001:
                    notifyDataSetChanged();
                case 0x0002:
                    System.out.println("hello world");
                    break;
            }
            return false;
        }
    });

    public MyRecyclerAdapter(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.mInflater=LayoutInflater.from(context);
        this.mTitles = new ArrayList<String>();
//        for(int i = 0; i < 6; i ++){
//            this.mTitles.add("teacher course " + i);
//        }
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.my_recycler_view_item, parent,false);
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
            mTitles.add(course.get(i).getCourse_id()+ " " + course.get(i).getName());
        }
        Message message = new Message();
        message.what = 0x0001;
        handler.sendMessage(message);
    }

    public void setDataString(List<String> course) {
        mTitles = new ArrayList<String>();
        mTitles.addAll(course);
        Message message = new Message();
        message.what = 0x0001;
        handler.sendMessage(message);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            item_tv = view.findViewById(R.id.recycler_item);
        }
    }

    /**
     * 自定义RecyclerView 中item view点击回调方法
     */
    public interface OnRecyclerItemClickListener{
        /**
         * item view 回调方法
         * @param view  被点击的view
         * @param position 点击索引
         */
        void onItemClick(View view, int position);
    }
}
