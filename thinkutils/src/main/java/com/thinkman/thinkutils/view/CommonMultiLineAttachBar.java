package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.adapter.ImagePicketAdapter;
import com.thinkman.thinkutils.adapter.MultiLineAttachBarAdapter;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by wangx on 2016/7/12.
 */
public class CommonMultiLineAttachBar extends RelativeLayout {

    Context mContext = null;
    private View contentView = null;
    GridViewScrollable m_gvImages = null;
    MultiLineAttachBarAdapter mAdapter = null;

    public CommonMultiLineAttachBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonMultiLineAttachBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonMultiLineAttachBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;

        contentView = LayoutInflater.from(context).inflate(R.layout.layout_imagepicker_view, this, true);
        m_gvImages = (GridViewScrollable) contentView.findViewById(R.id.gv_images);
        mAdapter = new MultiLineAttachBarAdapter(mContext);
        m_gvImages.setAdapter(mAdapter);

        m_gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null == mItemClickListener) {
                    return;
                }

                PhotoInfo info = mAdapter.getItem(position);
                mItemClickListener.onItemClick(position, info);
            }
        });
    }

    public void clear() {
        mAdapter.clear();
    }

    public void addPhoto(String szPath) {
        PhotoInfo info = new PhotoInfo();
        info.setPhotoPath(szPath);
        addPhoto(info);
    }

    public void addPhoto(PhotoInfo info) {
        mAdapter.add(info);
    }

    public void addPhoto(List<PhotoInfo> list) {
        mAdapter.addAll(list);
    }

    OnItemClickListener mItemClickListener = null;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int nPosition, PhotoInfo info);
    }

    public List<PhotoInfo> getItems() {
        return mAdapter.getItems();
    }
}
