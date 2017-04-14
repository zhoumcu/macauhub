package com.zhiao.develop.macaumagazine.vholder;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.News;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author：Administrator on 2016/12/8 14:42
 * company: xxxx
 * email：1032324589@qq.com
 */
public class NewsViewHolder extends BaseViewHolder<News.ContentBean> {

    @Bind(R.id.ll_head)
    LinearLayout llHead;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.icon)
    SimpleDraweeView icon;
    @Bind(R.id.tv_date)
    TextView tvDate;

    public NewsViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_news);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(News.ContentBean news) {
        super.setData(news);
        icon.setImageURI(Uri.parse(news.getThemeImg()));
        title.setText(news.getAtitle());
        tvDate.setText(news.getPubdate());
    }
}
