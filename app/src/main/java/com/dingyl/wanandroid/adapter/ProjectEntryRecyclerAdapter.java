package com.dingyl.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.ProjEntryDataBean;
import com.dingyl.wanandroid.util.Constants;
import com.dingyl.wanandroid.util.GlideUtil;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
import com.dingyl.wanandroid.util.Tools;

import java.util.ArrayList;

public class ProjectEntryRecyclerAdapter extends RecyclerView.Adapter<ProjectEntryRecyclerAdapter.ProjectViewHolder>{

    private static final String TAG = "EntryRecyclerAdapter";
    private ArrayList<ProjEntryDataBean> projEntryDataBeans;
    private Context context;
    private int currentPosition;

    public ProjectEntryRecyclerAdapter(Context context,ArrayList<ProjEntryDataBean> projEntryDataBeans){
        this.context = context;
        this.projEntryDataBeans = projEntryDataBeans;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.proj_item,parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, final int position) {
        GlideUtil.loadImage(context,projEntryDataBeans.get(position).getEnvelopePic(),holder.projItemImage);
        holder.projItemTitle.setText(projEntryDataBeans.get(position).getTitle());
        holder.projItemContent.setText(projEntryDataBeans.get(position).getDesc());
        holder.projItemDate.setText(projEntryDataBeans.get(position).getNiceDate());
        holder.projItemAuthor.setText(projEntryDataBeans.get(position).getAuthor());
        holder.projItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = SharedPreferenceUtil.getInstance(context).getProjectLinkType();
                if (type == Constants.TYPE_WEB){
                    Tools.startWebActivity(context,projEntryDataBeans.get(position).getLink()
                            ,projEntryDataBeans.get(position).getTitle());
                }else {
                    Tools.startWebActivity(context,projEntryDataBeans.get(position).getProjectLink()
                            ,projEntryDataBeans.get(position).getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return projEntryDataBeans.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{
        CardView projItemCard;
        ImageView projItemImage;
        TextView projItemTitle,projItemContent,projItemDate,projItemAuthor;

        private ProjectViewHolder(View itemView) {
            super(itemView);
            projItemCard = itemView.findViewById(R.id.proj_card);
            projItemImage = itemView.findViewById(R.id.proj_image);
            projItemTitle = itemView.findViewById(R.id.proj_title);
            projItemContent = itemView.findViewById(R.id.proj_content);
            projItemDate = itemView.findViewById(R.id.proj_time);
            projItemAuthor = itemView.findViewById(R.id.proj_author);
        }
    }
}
