package mo.macauhub.macauhub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mo.macauhub.macauhub.R;
import mo.macauhub.macauhub.bean.Categorys;

/**
 * Created by ymn on 2017/4/13.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Categorys> categoryses = new ArrayList<>();
    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener lisenter;

    public CategoryAdapter(Context context, List<Categorys> categoryses) {
        this.categoryses = categoryses;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public CategoryAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(categoryses.get(position).getImageUrl()!=0)
            holder.icon.setImageResource(categoryses.get(position).getImageUrl());
        holder.title.setText(categoryses.get(position).getAtitle());
        holder.llHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisenter.onItemClickListener(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryses.size();
    }

    public void addAll(List<Categorys> categoryses) {
        this.categoryses = categoryses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_head)
        LinearLayout llHead;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.icon)
        SimpleDraweeView icon;

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
