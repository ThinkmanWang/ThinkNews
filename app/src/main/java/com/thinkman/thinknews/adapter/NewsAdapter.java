package com.thinkman.thinknews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.captain_miao.recyclerviewutils.BaseWrapperRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.models.NewsModel;

import java.util.List;

/**
 * @author YanLu
 * @since 15/9/15
 */
public class NewsAdapter extends BaseWrapperRecyclerAdapter<NewsModel, NewsAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    public NewsAdapter(List<NewsModel> items) {
        appendToList(items);
    }


    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new NewsAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(NewsAdapter.ItemViewHolder vh, int position) {
        vh.mTvContent.setText(getItem(position).getTitle());
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()){
            case R.id.tv_content:
                Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();
                break;
            default:
                //mock click todo  last item
                remove(position);
                notifyItemRemoved(position);
        }
    }


    public class ItemViewHolder extends ClickableViewHolder {
        public TextView mTvContent;

        public ItemViewHolder(View view) {
            super(view);
            mTvContent = (TextView) view.findViewById(R.id.tv_content);
            setOnRecyclerItemClickListener(NewsAdapter.this);
            addOnItemViewClickListener();
            addOnViewClickListener(mTvContent);
        }
    }

}
