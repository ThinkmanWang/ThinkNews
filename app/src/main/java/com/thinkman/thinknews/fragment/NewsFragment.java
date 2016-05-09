package com.thinkman.thinknews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.github.captain_miao.recyclerviewutils.common.BaseLoadMoreFooterView;
import com.github.captain_miao.recyclerviewutils.listener.LinearLayoutWithRecyclerOnScrollListener;
import com.thinkman.fragment.BaseFragment;
import com.thinkman.thinknews.Contant;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.adapter.NewsAdapter;
import com.thinkman.thinknews.models.NewsListModel;
import com.thinkman.thinknews.models.NewsModel;

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


public class NewsFragment extends BaseFragment {
	private ProgressBar progressBar;
	private RelativeLayout mMainLayout;
	private int mTabIndex = 0;
	public static final String INTENT_INT_INDEX = "intent_int_index";

	//for show news list
	private RecyclerView mRecyclerView = null;
	private NewsAdapter mAdapter = null;
	private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
	private PtrFrameLayout ptrFrameLayout = null;
	private int m_nCurrentPage = 1;

	//for OKHttp
	OkHttpClient mOkHttpClient = new OkHttpClient();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		if (null != getContentView()) {
			return getContentView();
		}

		View view = inflater.inflate(R.layout.fragment_news, null);
		mTabIndex = getArguments().getInt(INTENT_INT_INDEX);
		progressBar = (ProgressBar) view.findViewById(R.id.fragment_mainTab_item_progressBar);

		mMainLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
		//mHandler.sendEmptyMessageDelayed(1, 2000);

		initView(view);
		initData();

		setContentView(view);
		return view;
	}

	private void initView(View view) {
		mRecyclerView =  (RecyclerView) view.findViewById(R.id.recycler_view);
		//mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
		mRecyclerView.setLayoutManager(mLinearLayoutManager);

		mAdapter = new NewsAdapter(this.getActivity(), new ArrayList<NewsModel>());
		mAdapter.setLoadMoreFooterView(new BaseLoadMoreFooterView(this.getActivity()) {
			@Override
			public int getLoadMoreLayoutResource() {
				return R.layout.list_load_more;
			}
		});
		mRecyclerView.setAdapter(mAdapter);

		ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.material_style_ptr_frame);
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

			//TODO refresh datas
			initData();

			ptrFrameLayout.postDelayed(new Runnable() {
				@Override
				public void run() {
					ptrFrameLayout.refreshComplete();

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

			mRecyclerView.post(new Runnable() {
				@Override
				public void run() {
					mAdapter.showLoadMoreView();
				}
			});

			//TODO get more data and append to tail
			Request.Builder requestBuilder = new Request.Builder().url(Contant.URLS[mTabIndex]
					.replaceAll("($1)", Contant.APPKEY)
					.replaceAll("($2)", ""+Contant.PAGE_SIZE)
					.replaceAll("($3)", ""+(m_nCurrentPage+1)));

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
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									m_nCurrentPage += 1;
									mAdapter.addAll(newsList.getNewslist());
									mHandler.sendEmptyMessageDelayed(1, 1000);

									mAdapter.notifyDataSetChanged();
									mAdapter.hideFooterView();
									loadComplete();
								}
							}, 1000);
						} catch (JSONException ex) {

						}
					}


				}
			});


//			mRecyclerView.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//
//					mAdapter.notifyDataSetChanged();
//					mAdapter.hideFooterView();
//					loadComplete();
//
//				}
//			}, 1500);
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressBar.setVisibility(View.GONE);
			mMainLayout.setVisibility(View.VISIBLE);
		}
	};

	private void initData() {
		m_nCurrentPage = 1;
		Request.Builder requestBuilder = new Request.Builder().url(Contant.URLS[mTabIndex]
				.replaceAll("\\(\\$1\\)", Contant.APPKEY)
				.replaceAll("\\(\\$2\\)", ""+Contant.PAGE_SIZE)
				.replaceAll("\\(\\$3\\)", ""+m_nCurrentPage));


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

								mAdapter.clear();
								mAdapter.addAll(newsList.getNewslist());
								mHandler.sendEmptyMessageDelayed(1, 1000);
							}
						});
					} catch (JSONException ex) {

					}
				}


			}
		});
	}

	public void onDestroyView() {
		super.onDestroyView();
	}

	public void onDestroy() {
		super.onDestroy();
	}
}
