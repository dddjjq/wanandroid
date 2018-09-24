package com.dingyl.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.data.KnowDataBean;

import java.util.ArrayList;

public class KnowRecyclerAdapter extends RecyclerView.Adapter<KnowRecyclerAdapter.KnowViewViewHolder> {

    private ArrayList<KnowData.DataBean> dataBeans;
    private Context context;

    public KnowRecyclerAdapter(Context context,ArrayList<KnowData.DataBean> dataBeans){
        this.context = context;
        this.dataBeans = dataBeans;

    }

    @NonNull
    @Override
    public KnowViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.know_item,parent,false);
        return new KnowViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KnowViewViewHolder holder, int position) {
        holder.knowTitle.setText(dataBeans.get(position).getName());
        ArrayList<KnowDataBean> knowDataBeans = dataBeans.get(position).getChildren();
        StringBuilder knowContentText = new StringBuilder();
        for (KnowDataBean kdb : knowDataBeans){
            knowContentText.append(kdb.getName());
            knowContentText.append("  ");
        }
        holder.knowContent.setText(knowContentText.toString().trim());
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    class KnowViewViewHolder extends RecyclerView.ViewHolder{

        TextView knowTitle,knowContent;
        CardView knowCard;

        private KnowViewViewHolder(View itemView) {
            super(itemView);
            knowTitle = itemView.findViewById(R.id.know_title);
            knowContent = itemView.findViewById(R.id.know_content);
            knowCard = itemView.findViewById(R.id.know_card);
        }
    }
}
