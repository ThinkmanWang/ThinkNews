package com.thinkman.thinkutils.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.commonutils.DisplayUtil;
import com.thinkman.thinkutils.dialog.CommonDialogUtils;
import com.thinkman.thinkutils.layout.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangx on 2016/9/13.
 */
public class TagSelector extends FlowLayout {
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private View mContentView = null;

    public TagSelector(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TagSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TagSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);

        romoveAddBtn();
        initAddBtn();
    }

    public void setBackGround(Color nNormal, Color nSelected) {

    }

    public void setTextColor(Color nNormal, Color nSelected) {

    }

    HashMap<String, RelativeLayout> m_mapTags = new HashMap<>();
    HashMap<String, RelativeLayout> m_mapSelected = new HashMap<>();

    public void addTags(List<String> lstTag) {
        for (String szTag : lstTag) {
            addTag(szTag, true);
        }
    }

    public void initTags(List<String> lstTag) {
        for (String szTag : lstTag) {
            addTag(szTag, false);
        }
    }

    public void initTagsSelected(List<String> lstSelected) {
        for (String szTag : m_mapTags.keySet()) {
            if (lstSelected.contains(szTag)) {
                m_mapSelected.put(szTag, m_mapTags.get(szTag));

                RelativeLayout rlTag = (RelativeLayout) m_mapTags.get(szTag);
                TextView tvTag = (TextView) rlTag.findViewById(R.id.tv_tag);
                tvTag.setTextColor(mContext.getResources().getColor(R.color.white));
                tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.blue_order));
                rlTag.setBackgroundColor(mContext.getResources().getColor(R.color.blue_order));
            }
        }
    }

    public void refresh() {
        for (RelativeLayout rlTag : m_mapTags.values()) {

        }
    }

    public void addTag(String szTag) {
        addTag(szTag, true);
    }

    public void addTag(String szTag, boolean bCallback) {
        if (m_mapTags.containsKey(szTag)) {
            return;
        }

        final RelativeLayout rlTag = (RelativeLayout) mInflater.inflate(R.layout.layout_tag, null);

        rlTag.setMinimumWidth(DisplayUtil.getActionBarHeight(mContext));
        rlTag.setMinimumHeight(DisplayUtil.getActionBarHeight(mContext));

        TextView tvTag = (TextView) rlTag.findViewById(R.id.tv_tag);
        tvTag.setText(szTag);
        tvTag.setTextColor(mContext.getResources().getColor(R.color.black));
        tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
        rlTag.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
        tvTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvTag = (TextView) v;
                if (m_mapSelected.containsKey(tvTag.getText().toString())) {
                    tvTag.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
                    rlTag.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
                    m_mapSelected.remove(tvTag.getText().toString());
                } else {
                    tvTag.setTextColor(mContext.getResources().getColor(R.color.white));
                    tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.blue_order));
                    rlTag.setBackgroundColor(mContext.getResources().getColor(R.color.blue_order));
                    m_mapSelected.put(tvTag.getText().toString(), rlTag);
                }

                if (m_onTagSelecteListener != null) {
                    m_onTagSelecteListener.onTagClicked(tvTag.getText().toString());
                }
            }
        });

        ImageView ivDel = (ImageView) rlTag.findViewById(R.id.iv_del);
        ivDel.setTag(szTag);
        ivDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String szTag = (String) v.getTag();
                new AlertDialog.Builder(mContext)
                        .setMessage("确定要删除标签吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeTag(szTag);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });

        m_mapTags.put(szTag, rlTag);

        if (m_onTagSelecteListener != null && bCallback) {
            m_onTagSelecteListener.onTagAdded(szTag);
        }

        romoveAddBtn();
        addView(rlTag);
        initAddBtn();
        invalidate();
    }

    public void removeTag(String szTag) {
        if (false == m_mapTags.containsKey(szTag)) {
            return;
        }

        this.removeView(m_mapTags.get(szTag));
        m_mapTags.remove(szTag);
        if (m_mapSelected.containsKey(szTag)) {
            m_mapSelected.remove(szTag);
        }

        invalidate();

        if (m_onTagSelecteListener != null) {
            m_onTagSelecteListener.onTagRemoved(szTag);
        }
    }

    public void clear() {
        m_mapTags.clear();
        m_mapSelected.clear();
    }

    private OnTagListener m_onTagSelecteListener = null;
    public void setOnTagSelecteListener(OnTagListener listener) {
        m_onTagSelecteListener = listener;
    }

    public interface OnTagListener {
        void onTagClicked(String szTag);
        void onTagAdded(String szTag);
        void onTagRemoved(String szTag);
    }

    public List<String> getAllTags() {
        ArrayList<String> lstTags = new ArrayList<>();

        for (String szTag : m_mapTags.keySet()) {
            lstTags.add(szTag);
        }

        return lstTags;
    }

    public List<String> geTagsSelected() {
        ArrayList<String> lstTags = new ArrayList<>();

        for (String szTag : m_mapSelected.keySet()) {
            lstTags.add(szTag);
        }

        return lstTags;
    }

    private final int ID_ADD_BTN = 1024;
    private void romoveAddBtn() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View vItem = getChildAt(i);
            if (vItem.getTag() != null && ID_ADD_BTN == (Integer)vItem.getTag()) {
                removeView(vItem);
            }
        }
    }

    private void initAddBtn() {
        ImageView ivAdd = new ImageView(mContext);
        ivAdd.setMinimumWidth(DisplayUtil.getActionBarHeight(mContext));
        ivAdd.setMaxWidth(DisplayUtil.getActionBarHeight(mContext));
        ivAdd.setMinimumHeight(DisplayUtil.getActionBarHeight(mContext));
        ivAdd.setMaxHeight(DisplayUtil.getActionBarHeight(mContext));
        ivAdd.setTag(ID_ADD_BTN);
        ivAdd.setBackgroundResource(R.drawable.selector_image_add);

        ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                com.thinkman.thinkutils.dialog.CommonDialogUtils.showInputDialog(mContext, "标签", "输入新的标签", "标签", new CommonDialogUtils.OnInputDialogResult() {
                    @Override
                    public void onOk(String szText) {
                        TagSelector.this.addTag(szText, true);
                    }
                });
            }
        });

        addView(ivAdd);
    }
}
