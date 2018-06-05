package com.example.lancer.sigh.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lancer.sigh.R;
import com.example.lancer.sigh.activity.PicActivity;
import com.example.lancer.sigh.model.PicBean;


import java.util.List;

/**
 * Created by Lancer on 2018/6/4.
 */

public class PicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    public List<PicBean.NewslistBean> picList;
    public Context mContext;
    private float scaleh;
    private float scalew;
    private Bitmap bm;

    public PicAdapter(List<PicBean.NewslistBean> picList, Context mContext) {
        this.picList = picList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.pic_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        String picUrl = picList.get(position).getPicUrl();
        String replace = picUrl.replace("\\", "");
        Glide.with(mContext).load(replace).placeholder(R.drawable.cat).into(viewHolder.imageView);

        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
          return  picList==null?0:picList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_item_pic);
        }
    }
    //声明一个这个接口的变量
    private OnItemClickListener mOnItemClickListener = null;

    //在MyAdapter中定义如下接口,模拟ListView的OnItemClickListener
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 将点击事件转移给外面的调用者
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //最后暴露给外面的调用者，定义一个设置Listener的方法（）：
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
