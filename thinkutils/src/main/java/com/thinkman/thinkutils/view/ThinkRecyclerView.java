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

package com.thinkman.thinkutils.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description：EasyRecyclerView
 * Created by：CaMnter
 * Time：2015-12-28 11:51
 */
public class ThinkRecyclerView extends RecyclerView {

    private LinearLayoutManager mLinearLayoutManager;


    public ThinkRecyclerView(Context context) {
        super(context);
        this.initRecyclerView(context);
    }


    public ThinkRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initRecyclerView(context);
    }


    public ThinkRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initRecyclerView(context);
    }


    /**
     * Init the recycler view
     *
     * @param context context
     */
    protected void initRecyclerView(Context context) {
        // init LinearLayoutManager
        this.mLinearLayoutManager = new LinearLayoutManager(context);
        // set the VERTICAL layout
        this.mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // set layout manager
        this.setLayoutManager(this.mLinearLayoutManager);
        // set item animator
        this.setItemAnimator(new DefaultItemAnimator());
        // keep recyclerview fixed size
        this.setHasFixedSize(true);
    }


    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }


    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    private boolean m_bVerticalScrollOnly = false;
    public void setVerticalScrollOnly(boolean bVerticalScroll) {
        m_bVerticalScrollOnly = bVerticalScroll;
    }

    private float xDistance, yDistance;
    private float xLast, yLast;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > yDistance && m_bVerticalScrollOnly) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
