package com.thinkman.thinkutils.adapter;

/**
 * Created by wangx on 2016/6/13.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.finalteam.galleryfinal.model.PhotoInfo;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thinkman.thinkutils.R;

public class ImagePicketAdapter extends ThinkBaseAdapter<PhotoInfo> {

    public static final String IMAGEITEM_DEFAULT_ADD = "default_add";

    public ImagePicketAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_ip_image_item, parent, false);
        }

        ImageView ivImg = (ImageView) ViewHolder.get(convertView, R.id.iv_img);

        if (IMAGEITEM_DEFAULT_ADD.equals(this.getItem(position).getPhotoPath())) {
            ivImg.setImageResource(R.drawable.selector_image_add);
        } else {
            Glide.with(mContext)                             //配置上下文
                    .load("file://" + this.getItem(position).getPhotoPath())                  //设置图片路径
                    .error(R.drawable.default_image)           //设置错误图片
                    .placeholder(R.drawable.default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(ivImg);
        }

        return convertView;
    }

}
