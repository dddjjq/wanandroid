package com.dingyl.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.HomeDataBean;
import com.dingyl.wanandroid.util.Tools;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    private static final String TAG = "HomeRecyclerAdapter";
    private Context context;
    private ArrayList<HomeDataBean> dataDaoBeans;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private View headerView;
    private OnHomeItemClick onHomeItemClick;

    public HomeRecyclerAdapter(Context context,ArrayList<HomeDataBean> dataDaoBeans){
        this.context = context;
        this.dataDaoBeans = dataDaoBeans;
        Log.d(TAG,"dataBeans size : " + dataDaoBeans.size());
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
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            return;
        }
        Log.d(TAG,"111");
        int realPos = getRealPosition(position);
        holder.userName.setText(dataDaoBeans.get(realPos).getAuthor());
        holder.publishTime.setText(dataDaoBeans.get(realPos).getNiceDate());
        holder.content.setText(dataDaoBeans.get(realPos).getTitle());
        holder.rootTitle.setText(dataDaoBeans.get(realPos).getSuperChapterName());
        holder.homeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = dataDaoBeans.get(position).getLink();
                String title = dataDaoBeans.get(position).getTitle();
                Tools.startWebActivity(context,url,title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataDaoBeans.size();
    }

    private int getRealPosition(int position){
        return headerView == null ? position : position - 1;
    }
    class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView userName,publishTime,content,rootTitle,rootCategory;
        ImageButton addLoveBtn;
        CardView homeItem;

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
            homeItem = itemView.findViewById(R.id.home_item);
        }
    }

    public void setOnHomeItemClick(OnHomeItemClick onHomeItemClick){
        this.onHomeItemClick = onHomeItemClick;
    }

    public interface OnHomeItemClick{
        void click();
    }
}
