package com.example.attendancesystem_client_android.teacher;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem_client_android.R;

public class TeacherRecyclerAdapter2 extends RecyclerView.Adapter<TeacherRecyclerAdapter2.ViewHolder> {
    private LayoutInflater mInflater;
    private String[] mTitles;
    private  OnRecyclerItemClickListener onRecyclerItemClickListener;


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

    public TeacherRecyclerAdapter2(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.mInflater=LayoutInflater.from(context);
        this.mTitles = new String[]{"暗裔剑魔亚托克斯","九尾妖狐阿狸","离群之刺阿卡丽",
                "牛头酋长阿利斯塔","殇之木乃伊阿木木","冰晶凤凰艾尼维亚","黑暗之女安妮",
                "寒冰射手艾希","铸星龙王奥瑞利安·索尔","沙漠皇帝阿兹尔","星界游神巴德",
                "暗裔剑魔亚托克斯","九尾妖狐阿狸","离群之刺阿卡丽",
                "牛头酋长阿利斯塔","殇之木乃伊阿木木","冰晶凤凰艾尼维亚","黑暗之女安妮",
                "寒冰射手艾希","铸星龙王奥瑞利安·索尔","沙漠皇帝阿兹尔","星界游神巴德"};
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
        holder.item_tv.setText(mTitles[position]);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            item_tv = view.findViewById(R.id.t_f2_item);
        }
    }
}
