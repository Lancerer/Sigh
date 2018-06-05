package com.example.lancer.sigh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lancer.sigh.R;
import com.example.lancer.sigh.model.HappyBean;
import com.example.lancer.sigh.model.PeBean;

import java.util.List;


public class PeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<PeBean.NewslistBean> lists;
    private Context mContext;

    public PeAdapter(List<PeBean.NewslistBean> lists, Context mContext) {
        this.lists = lists;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_news, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        String picUrl = lists.get(position).getPicUrl();
        String replace = picUrl.replace("\\", "");
        Glide.with(mContext).load(replace).placeholder(R.drawable.cat).into(viewHolder.imageView);
        viewHolder.tvtitle.setText(lists.get(position).getTitle());
        viewHolder.tvdes.setText(lists.get(position).getDescription());
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return  lists==null?0:lists.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle;
        TextView tvdes;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvdes = itemView.findViewById(R.id.tv_time);
            tvtitle = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.iv_news);
        }
    }

    //声明一个这个接口的变量
    private PicAdapter.OnItemClickListener mOnItemClickListener = null;

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
    public void setOnItemClickListener(PicAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
