/*
 * Copyright (C) 2015 CaMnter yuanyu.camnter@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinkman.thinkutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public abstract class ThinkRecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList mList;
    private ThinkRecyclerViewHolder.OnItemClickListener onItemClickListener;
    private ThinkRecyclerViewHolder.OnItemLongClickListener onItemLongClickListener;
    private HashMap<Integer, Boolean> mSelected = new HashMap<>();

    public ThinkRecyclerViewAdapter() {
        this.mList = new ArrayList();
        mSelected.clear();
    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override public int getItemCount() {
        return this.mList.size();
    }


    public int getListSize() {
        return this.mList.size();
    }


    @SuppressWarnings("unchecked") public <T> T getItem(int position) {
        return (T) this.mList.get(position);
    }


    public <T> T getItemByPosition(int position) {
        return this.getItem(position);
    }


    @SuppressWarnings("unchecked") public void setList(List list) {
        this.mList.clear();
        mSelected.clear();
        if (list == null) return;
        this.mList.addAll(list);
    }


    public void clear() {
        this.mList.clear();
        mSelected.clear();
    }


    public void remove(Object o) {
        mSelected.remove(mList.indexOf(o));
        this.mList.remove(o);
    }


    public List getList() {
        return this.mList;
    }

    public List getSelectedItems() {
        ArrayList lstRet = new ArrayList();
        for (Integer pos : mSelected.keySet()) {
            lstRet.add(mList.get(pos));
        }

        return lstRet;
    }

    private boolean m_bSingleSelect = false;

    public void setSingleSelect(boolean bSingle) {
        m_bSingleSelect = bSingle;
    }

    public boolean isSelected(int pos) {
        return mSelected.containsKey(pos);
    }

    public void setSelected(int nPos, boolean bSelected) {
        if (m_bSingleSelect) {
            mSelected.clear();
        }

        if (bSelected) {
            mSelected.put(nPos, bSelected);
        } else {
            if (mSelected.containsKey(nPos)) {
                mSelected.remove(nPos);
            }
        }
    }

    public void selectAll(boolean bSelected) {

        if (false == bSelected) {
            mSelected.clear();
            return;
        }

        int nCount = this.getItemCount();

        for (int pos = 0; pos < nCount; ++pos) {
            mSelected.put(pos, true);
        }
    }

    @SuppressWarnings("unchecked") public void addAll(Collection list) {
        this.mList.addAll(list);
    }


    /**
     * Please return RecyclerView loading layout Id array
     * 请返回RecyclerView加载的布局Id数组
     *
     * @return 布局Id数组
     */
    public abstract int[] getItemLayouts();

    /**
     * butt joint the onBindViewHolder and
     * If you want to write logic in onBindViewHolder, you can write here
     * 对接了onBindViewHolder
     * onBindViewHolder里的逻辑写在这
     *
     * @param viewHolder viewHolder
     * @param position position
     */
    public abstract void onBindRecycleViewHolder(ThinkRecyclerViewHolder viewHolder, int position);

    /**
     * Please write judgment logic when more layout
     * and not write when single layout
     * 如果是多布局的话，请写判断逻辑
     * 单布局可以不写
     *
     * @param position Item position
     * @return 布局Id数组中的index
     */
    public abstract int getRecycleViewItemType(int position);


    /**
     * get the itemType by position
     * 根据position获取ItemType
     *
     * @param position Item position
     * @return 默认ItemType等于0
     */
    @Override public int getItemViewType(int position) {
        return this.getRecycleViewItemType(position);
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ThinkRecyclerViewHolder easyRecyclerViewHolder = (ThinkRecyclerViewHolder) holder;
            this.onBindRecycleViewHolder(easyRecyclerViewHolder, position);
            easyRecyclerViewHolder.setOnItemClickListener(this.onItemClickListener, position);
            easyRecyclerViewHolder.setOnItemLongClickListener(this.onItemLongClickListener,
                    position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0) return null;
        if (this.getItemLayouts() == null) return null;
        int[] layoutIds = this.getItemLayouts();
        if (layoutIds.length < 1) return null;

        int itemLayoutId;
        if (layoutIds.length == 1) {
            itemLayoutId = layoutIds[0];
        } else {
            itemLayoutId = layoutIds[viewType];
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ThinkRecyclerViewHolder(view);
    }


    /**
     * set the on item click listener
     * 设置点击事件
     *
     * @param onItemClickListener onItemClickListener
     */
    public void setOnItemClickListener(ThinkRecyclerViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * set the on item long click listener
     * 设置长点击事件
     *
     * @param onItemLongClickListener onItemLongClickListener
     */
    public void setOnItemLongClickListener(ThinkRecyclerViewHolder.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
