package com.macauhub.macauhub.vholder;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.macauhub.macauhub.R;
import com.macauhub.macauhub.bean.News;

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
        if(TextUtils.isEmpty(news.getThemeImg())||news.getThemeImg().equals("")){
            icon.setVisibility(View.GONE);
        }else {
            icon.setVisibility(View.VISIBLE);
            icon.setImageURI(Uri.parse(news.getThemeImg()));
        }
        title.setText(news.getAtitle());
        tvDate.setText(news.getPubdate());
    }
}
