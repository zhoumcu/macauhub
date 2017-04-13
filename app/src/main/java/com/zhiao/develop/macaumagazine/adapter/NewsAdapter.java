package com.zhiao.develop.macaumagazine.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ymn on 2017/4/13.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News.ContentBean> news = new ArrayList<>();
    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener lisenter;

    public NewsAdapter(Context context, List<News.ContentBean> news) {
        this.news = news;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public NewsAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.icon.setImageURI(Uri.parse(news.get(position).getThemeImg()));
        holder.title.setText(news.get(position).getAtitle());
        holder.tvDate.setText(news.get(position).getPubdate());
        holder.llHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisenter.onItemClickListener(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return news.size();
    }

    public void addAll(List<News.ContentBean> newses) {
        this.news = newses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_head)
        LinearLayout llHead;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.icon)
        SimpleDraweeView icon;
        @Bind(R.id.tv_date)
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.lisenter = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }
}
