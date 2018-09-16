package com.magicsoft.geekfj.windingpay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JDNew on 2017/8/11.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mLayoutId;
    private List<String> mDataList;
    private Context mContext;


    public MyAdapter(Context context, int layoutId, List<String> dataList) {
        this.mLayoutId = layoutId;
        this.mDataList = dataList;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        return new Item(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item itemHolder = (Item) holder;
        itemHolder.tv_content.setText(mDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void updateData(List<String> dataList){
        mDataList = dataList;
        notifyDataSetChanged();
    }

    class Item extends RecyclerView.ViewHolder {

        public TextView tv_content;

        public Item(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}