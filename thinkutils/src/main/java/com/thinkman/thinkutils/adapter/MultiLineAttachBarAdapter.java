package com.thinkman.thinkutils.adapter;

/**
 * Created by wangx on 2016/6/13.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.view.ImagePickerView;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

public class MultiLineAttachBarAdapter extends ThinkBaseAdapter<PhotoInfo> {

    public MultiLineAttachBarAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_ip_image_item, parent, false);
        }

        ImageView ivImg = (ImageView) ViewHolder.get(convertView, R.id.iv_img);
        ImageView ivDel = (ImageView) ViewHolder.get(convertView, R.id.iv_del);

        Glide.with(mContext)                             //配置上下文
                .load(this.getItem(position).getPhotoPath())                  //设置图片路径
                .error(R.drawable.default_image)           //设置错误图片
                .placeholder(R.drawable.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(ivImg);

        ivDel.setVisibility(View.GONE);

        return convertView;
    }

}
