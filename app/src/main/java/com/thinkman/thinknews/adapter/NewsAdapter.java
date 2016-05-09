package com.thinkman.thinknews.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.captain_miao.recyclerviewutils.BaseWrapperRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.models.NewsModel;
import com.thinkman.thinkutils.DisplayUtil;

import java.util.List;

/**
 * @author YanLu
 * @since 15/9/15
 */
public class NewsAdapter extends BaseWrapperRecyclerAdapter<NewsModel, NewsAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    private Context mContext = null;

    public NewsAdapter(Context context, List<NewsModel> items) {
        mContext = context;
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
        vh.tv_content.setText(getItem(position).getTitle());

        NewsModel news = getItem(position);
        if (news != null && false == TextUtils.isEmpty(news.getPicUrl())) {
            int nPicSize = mContext.getResources().getDimensionPixelSize(R.dimen.news_image_size);
            Glide.with(mContext)
                    .load(news.getPicUrl())
                    .override(nPicSize, nPicSize)
                    .into(vh.iv_left_image);
        }
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()){
            case R.id.tv_content:
                Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();
                break;
            //default:
                //mock click todo  last item
//                remove(position);
//                notifyItemRemoved(position);
        }
    }


    public class ItemViewHolder extends ClickableViewHolder {
        public ImageView iv_left_image;
        public TextView tv_content;

        public ItemViewHolder(View view) {
            super(view);

            iv_left_image = (ImageView) view.findViewById(R.id.iv_left_image);

            tv_content = (TextView) view.findViewById(R.id.tv_content);
            setOnRecyclerItemClickListener(NewsAdapter.this);
            addOnItemViewClickListener();
            addOnViewClickListener(tv_content);
        }
    }

}
