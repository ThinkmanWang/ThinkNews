package com.thinkman.thinknews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.captain_miao.recyclerviewutils.common.BaseLoadMoreFooterView;
import com.github.captain_miao.recyclerviewutils.listener.LinearLayoutWithRecyclerOnScrollListener;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.adapter.NewsAdapter;
import com.thinkman.thinknews.models.NewsListModel;
import com.thinkman.thinknews.models.NewsModel;
import com.thinkman.thinkviewpagerindicator.fragment.LazyFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;


public class NewsFragment extends LazyFragment {
	private ProgressBar progressBar;
	private RelativeLayout mMainLayout;
	private int tabIndex = 0;
	public static final String INTENT_INT_INDEX = "intent_int_index";

	//for show news list
	private RecyclerView mRecyclerView = null;
	private NewsAdapter mAdapter = null;
	LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
	PtrFrameLayout ptrFrameLayout = null;

	//for OKHttp
	OkHttpClient mOkHttpClient = new OkHttpClient();

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_news);
		tabIndex = getArguments().getInt(INTENT_INT_INDEX);
		progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

		mMainLayout = (RelativeLayout) findViewById(R.id.main_layout);
		mHandler.sendEmptyMessageDelayed(1, 2000);

		initView();
		initData();
	}

	private void initView() {
		mRecyclerView =  (RecyclerView) findViewById(R.id.recycler_view);
		//mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
		mRecyclerView.setLayoutManager(mLinearLayoutManager);

		mAdapter = new NewsAdapter(new ArrayList<NewsModel>());
		mAdapter.setLoadMoreFooterView(new BaseLoadMoreFooterView(this.getActivity()) {
			@Override
			public int getLoadMoreLayoutResource() {
				return R.layout.list_load_more;
			}
		});
		mRecyclerView.setAdapter(mAdapter);

		ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.material_style_ptr_frame);
		// header
		final MaterialHeader header = new MaterialHeader(this.getActivity());
		//header.setColorSchemeColors(new int[]{R.color.line_color_run_speed_13});
		int[] colors = getResources().getIntArray(R.array.google_colors);
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
		header.setPtrFrameLayout(ptrFrameLayout);


		ptrFrameLayout.setDurationToCloseHeader(1500);
		ptrFrameLayout.setHeaderView(header);
		ptrFrameLayout.addPtrUIHandler(header);
		ptrFrameLayout.setEnabledNextPtrAtOnce(false);
		ptrFrameLayout.setPtrHandler(mPtrHandler);

		mRecyclerView.addOnScrollListener(mLoadMoreListener);
		ptrFrameLayout.refreshComplete();
		mLoadMoreListener.loadComplete();
	}

	PtrHandler mPtrHandler = new PtrHandler() {
		@Override
		public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
			return mLoadMoreListener.checkCanDoRefresh();
		}

		@Override
		public void onRefreshBegin(PtrFrameLayout frame) {
			mLoadMoreListener.setPagination(1);//恢复第一页
			ptrFrameLayout.postDelayed(new Runnable() {
				@Override
				public void run() {
					ptrFrameLayout.refreshComplete();

					//TODO refresh datas

					mAdapter.hideFooterView();
					mAdapter.notifyDataSetChanged();
					mRecyclerView.scrollToPosition(0);
				}
			}, 500);
		}
	};

	private LinearLayoutWithRecyclerOnScrollListener mLoadMoreListener = new LinearLayoutWithRecyclerOnScrollListener(mLinearLayoutManager) {

		@Override
		public void onLoadMore(final int pagination, int pageSize) {

			//TODO get more data and append to tail


			mRecyclerView.post(new Runnable() {
				@Override
				public void run() {
					mAdapter.showLoadMoreView();
//					if (mAdapter.getItemCount() < MAX_ITEM_COUNT) {
//						mAdapter.showLoadMoreView();
//					} else {
//						mAdapter.showNoMoreDataView();
//					}

				}
			});


			mRecyclerView.postDelayed(new Runnable() {
				@Override
				public void run() {

//					if (mAdapter.getItemCount() >= MAX_ITEM_COUNT) {
//						mAdapter.showNoMoreDataView();
//					} else {
//						mAdapter.append(pagination + " page -> " + mAdapter.getItemCount());
//						mAdapter.append(pagination + " page -> " + mAdapter.getItemCount());
//						mAdapter.append(pagination + " page -> " + mAdapter.getItemCount());
//						mAdapter.append(pagination + " page -> " + mAdapter.getItemCount());
//						mAdapter.append(pagination + " page -> " + mAdapter.getItemCount());
//						mAdapter.notifyDataSetChanged();
//						mAdapter.hideFooterView();
//					}

					mAdapter.notifyDataSetChanged();
					mAdapter.hideFooterView();
					loadComplete();

				}
			}, 1500);
		}
	};

	@Override
	public void onDestroyViewLazy() {
		super.onDestroyViewLazy();
		mHandler.removeMessages(1);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressBar.setVisibility(View.GONE);
			mMainLayout.setVisibility(View.VISIBLE);
		}
	};

	private void initData() {
		Request.Builder requestBuilder = new Request.Builder().url("http://api.huceo.com/wxnew/?key=0ea9db515854676cc3b8059966c77c2a&num=20");
		Request request = requestBuilder.build();
		Call httpCall= mOkHttpClient.newCall(request);

		httpCall.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (null != response.cacheResponse()) {
					String str = response.cacheResponse().toString();
				} else {
					final String szJson = response.body().string();
					//String str = response.networkResponse().toString();

					try {
						JSONObject jsonNewsList = new JSONObject(szJson);

						if (200 != jsonNewsList.getInt("code")) {
							return;
						}

						Gson gson = new Gson();
						final NewsListModel newsList = gson.fromJson(szJson, NewsListModel.class);
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
								mAdapter.clear();
								mAdapter.addAll(newsList.getNewslist());
							}
						});
					} catch (JSONException ex) {

					}
				}


			}
		});
	}
}
