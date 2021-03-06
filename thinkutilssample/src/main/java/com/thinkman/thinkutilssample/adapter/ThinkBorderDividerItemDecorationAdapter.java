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

package com.thinkman.thinkutilssample.adapter;

import android.widget.ImageView;
import android.widget.TextView;


import com.thinkman.thinkutils.adapter.ThinkRecyclerViewAdapter;
import com.thinkman.thinkutils.adapter.ThinkRecyclerViewHolder;
import com.thinkman.thinkutils.layout.SwipeLayout;
import com.thinkman.thinkutilssample.R;
import com.thinkman.thinkutilssample.models.ThinkRecyclerViewData;

public class ThinkBorderDividerItemDecorationAdapter extends ThinkRecyclerViewAdapter {
    /**
     * Please return RecyclerView loading layout Id array
     * 请返回RecyclerView加载的布局Id数组
     *
     * @return 布局Id数组
     */
    @Override public int[] getItemLayouts() {
        return new int[] { R.layout.item_border };
    }


    /**
     * butt joint the onBindViewHolder and
     * If you want to write logic in onBindViewHolder, you can write here
     * 对接了onBindViewHolder
     * onBindViewHolder里的逻辑写在这
     *
     * @param viewHolder viewHolder
     * @param position position
     */
    @Override public void onBindRecycleViewHolder(ThinkRecyclerViewHolder viewHolder, int position) {
        ThinkRecyclerViewData data = this.getItem(position);
        if (data == null) return;

        ImageView borderIv = viewHolder.findViewById(R.id.border_item_iv);
        TextView borderTv = viewHolder.findViewById(R.id.border_item_tv);

//        SwipeLayout sdv_DeleteView = viewHolder.findViewById(R.id.sdv_delete_view);
//        SwipeLayout.addSwipeView(sdv_DeleteView);

        borderIv.setImageResource(data.imageResId);
        borderTv.setText(data.content);
    }


    /**
     * Please write judgment logic when more layout
     * and not write when single layout
     * 如果是多布局的话，请写判断逻辑
     * 单布局可以不写
     *
     * @param position Item position
     * @return 布局Id数组中的index
     */
    @Override public int getRecycleViewItemType(int position) {
        return 0;
    }
}
