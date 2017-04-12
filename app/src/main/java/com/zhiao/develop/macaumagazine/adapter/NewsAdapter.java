package com.zhiao.develop.macaumagazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymn on 2017/4/13.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<News> news = new ArrayList<>();
    private Context context;
    private LayoutInflater mLayoutInflater;

    public NewsAdapter(Context context, List<News> news) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
