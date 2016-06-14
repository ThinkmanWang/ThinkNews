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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_ip_image_item, parent, false);
        }

        ImageView ivImg = (ImageView) ViewHolder.get(convertView, R.id.iv_img);
        ImageView ivDel = (ImageView) ViewHolder.get(convertView, R.id.iv_del);

        if (IMAGEITEM_DEFAULT_ADD.equals(this.getItem(position).getPhotoPath())) {
            ivImg.setImageResource(R.drawable.selector_image_add);
            ivDel.setVisibility(View.GONE);
        } else {
            Glide.with(mContext)                             //配置上下文
                    .load("file://" + this.getItem(position).getPhotoPath())                  //设置图片路径
                    .error(R.drawable.default_image)           //设置错误图片
                    .placeholder(R.drawable.default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(ivImg);

            ivDel.setVisibility(View.VISIBLE);
            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("")
                            .setMessage("确认要删除这张照片吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    remove(position);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            })
                            .show();
                }
            });
        }

        return convertView;
    }

}
