package com.dingyl.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.HomeData;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    private static final String TAG = "HomeRecyclerAdapter";
    private Context context;
    private ArrayList<HomeData.DataBeans.DataBean> dataBeans;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private View headerView;

    public HomeRecyclerAdapter(Context context,ArrayList<HomeData.DataBeans.DataBean> dataBeans){
        this.context = context;
        this.dataBeans = dataBeans;
        Log.d(TAG,"dataBeans size : " + dataBeans.size());
    }


    public void setHeaderView(View headerView){
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position){
        if(headerView == null){
            return TYPE_NORMAL;
        }
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(headerView != null && viewType == TYPE_HEADER){
            return new HomeViewHolder(headerView);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            return;
        }
        Log.d(TAG,"111");
        int realPos = getRealPosition(position);
        holder.userName.setText(dataBeans.get(realPos).getAuthor());
        holder.publishTime.setText(dataBeans.get(realPos).getNiceDate());
        holder.content.setText(dataBeans.get(realPos).getTitle());
        holder.rootTitle.setText(dataBeans.get(realPos).getSuperChapterName());
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    private int getRealPosition(int position){
        return headerView == null ? position : position - 1;
    }
    class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView userName,publishTime,content,rootTitle,rootCategory;
        ImageButton addLoveBtn;

        public HomeViewHolder(View itemView) {
            super(itemView);
            if(itemView == headerView){
                return;
            }
            userName = itemView.findViewById(R.id.item_title_user);
            publishTime = itemView.findViewById(R.id.item_time);
            content = itemView.findViewById(R.id.item_content);
            rootTitle = itemView.findViewById(R.id.item_root_title);
            rootCategory = itemView.findViewById(R.id.item_root_category);
            addLoveBtn = itemView.findViewById(R.id.add_love_button);
        }
    }

}
