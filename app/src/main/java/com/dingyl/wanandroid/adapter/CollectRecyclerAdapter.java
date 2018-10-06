package com.dingyl.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.CollectDataBean;
import com.dingyl.wanandroid.util.Tools;

import java.util.ArrayList;

public class CollectRecyclerAdapter extends RecyclerView.Adapter<CollectRecyclerAdapter.ViewHolder> {

    private ArrayList<CollectDataBean> collectDataBeans;
    private Context context;

    public CollectRecyclerAdapter(Context context, ArrayList<CollectDataBean> collectDataBeans){
        this.collectDataBeans = collectDataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.collect_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.author.setText(collectDataBeans.get(position).getAuthor());
        holder.publishTime.setText(collectDataBeans.get(position).getNiceDate());
        holder.category.setText(collectDataBeans.get(position).getChapterName());
        holder.title.setText(collectDataBeans.get(position).getTitle());
        holder.collectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.startWebActivity(context,collectDataBeans.get(position).getLink(),collectDataBeans.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectDataBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView author,publishTime,category,title;
        RelativeLayout collectItem;

        public ViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.collect_author);
            publishTime = itemView.findViewById(R.id.collect_time);
            category = itemView.findViewById(R.id.collect_category);
            title = itemView.findViewById(R.id.collect_title);
            collectItem = itemView.findViewById(R.id.collect_item);
        }
    }
}
