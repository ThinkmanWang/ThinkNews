package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.adapter.ImagePicketAdapter;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by wangx on 2016/6/13.
 */
public class ImagePickerView extends RelativeLayout implements AdapterView.OnItemClickListener {

    Context mContext = null;
    private View contentView = null;
    GridViewScrollable m_gvImages = null;
    ImagePicketAdapter mAdapter = null;


    public ImagePickerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ImagePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ImagePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;

        contentView = LayoutInflater.from(context).inflate(R.layout.layout_imagepicker_view, this, true);
        m_gvImages = (GridViewScrollable) contentView.findViewById(R.id.gv_images);
        mAdapter = new ImagePicketAdapter(mContext);
        m_gvImages.setAdapter(mAdapter);
        PhotoInfo ivAdd = new PhotoInfo();
        ivAdd.setPhotoPath(ImagePicketAdapter.IMAGEITEM_DEFAULT_ADD);
        mAdapter.add(ivAdd);
        m_gvImages.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoInfo item = mAdapter.getItem(position);
        if (ImagePicketAdapter.IMAGEITEM_DEFAULT_ADD.equals(item.getPhotoPath())) {
            //open gallery final
        } else {
            //show preview
        }
    }
}
