package com.thinkman.thinkutils.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.adapter.ImagePicketAdapter;

import cn.finalteam.galleryfinal.PhotoPreviewActivity;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import com.baoyz.actionsheet.ActionSheet;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.thinkman.thinkutils.listener.GlidePauseOnScrollListener;
import com.thinkman.thinkutils.loader.GlideImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.xutils.x;



/**
 * Created by wangx on 2016/6/13.
 */
public class ImagePickerView extends RelativeLayout {

    Context mContext = null;
    private View contentView = null;
    GridViewScrollable m_gvImages = null;
    ImagePicketAdapter mAdapter = null;

    private int m_nMaxCount = 9;

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;

    Handler mHandler = new Handler();

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

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImagePickerView, defStyle, 0);
        m_nMaxCount = a.getInteger(R.styleable.ImagePickerView_maxCount, 9);

        contentView = LayoutInflater.from(context).inflate(R.layout.layout_imagepicker_view, this, true);
        m_gvImages = (GridViewScrollable) contentView.findViewById(R.id.gv_images);
        mAdapter = new ImagePicketAdapter(mContext);
        m_gvImages.setAdapter(mAdapter);
        PhotoInfo ivAdd = new PhotoInfo();
        ivAdd.setPhotoPath(ImagePicketAdapter.IMAGEITEM_DEFAULT_ADD);
        mAdapter.add(ivAdd);
        m_gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoInfo item = mAdapter.getItem(position);
                if (ImagePicketAdapter.IMAGEITEM_DEFAULT_ADD.equals(item.getPhotoPath())) {
                    //open gallery final
                    openGalleryFinal();
                } else {
                    //show preview
                    Intent intent = new Intent(mContext, PhotoPreviewActivity.class);
                    intent.putExtra("photo_list", (ArrayList<PhotoInfo>)getSelectedPhotos());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    public void init(Activity activity) {
        initImageLoader(mContext);
        initFresco();
        x.Ext.init(activity.getApplication());
        initGalleryFinal();
    }

    FunctionConfig mFunctionConfig = null;
    private void initGalleryFinal() {
        ThemeConfig themeConfig = ThemeConfig.DEFAULT;

        cn.finalteam.galleryfinal.ImageLoader imageLoader = new GlideImageLoader();

        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        //functionConfigBuilder.setEnableEdit(true);
        functionConfigBuilder.setMutiSelectMaxSize(m_nMaxCount);
        functionConfigBuilder.setRotateReplaceSource(true);
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setEnableCamera(true);
        //functionConfigBuilder.setEnablePreview(true);
        functionConfigBuilder.setSelected(mAdapter.getItems());//添加过滤集合

        mFunctionConfig = functionConfigBuilder.build();

        PauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);

        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, themeConfig)
                .setFunctionConfig(mFunctionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(false)
                .build();
        GalleryFinal.init(coreConfig);
    }

    private void openGalleryFinal() {
        initGalleryFinal();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, mFunctionConfig, mOnHanlderResultCallback);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mAdapter.getItems().clear();
                mAdapter.getItems().addAll(resultList);

                PhotoInfo ivAdd = new PhotoInfo();
                ivAdd.setPhotoPath(ImagePicketAdapter.IMAGEITEM_DEFAULT_ADD);
                mAdapter.add(ivAdd);

                if (mListener != null) {
                    mListener.onImagePick(resultList);
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(mContext)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(mContext, config);
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public int getSelectedCount() {
        return mAdapter.getCount() - 1;
    }

    public List<PhotoInfo> getSelectedPhotos() {
        List<PhotoInfo> lstRet = new ArrayList<PhotoInfo>();
        lstRet.addAll(mAdapter.getItems());
        lstRet.remove(mAdapter.getCount() - 1);

        return lstRet;
    }

    private OnImagePickListener mListener = null;
    public interface OnImagePickListener {
        void onImagePick(List<PhotoInfo> resultList);
    }

    public void setOnImagePickListener(OnImagePickListener listener) {
        this.mListener = listener;
        mAdapter.setOnOnImagePickListener(mListener);
    }

    public void setMaxCount(int nCount) {
        m_nMaxCount = nCount;
    }

    public int getMaxCount() {
        return m_nMaxCount;
    }

    public void preview() {
        Intent intent = new Intent(mContext, PhotoPreviewActivity.class);
        intent.putExtra("photo_list", (ArrayList<PhotoInfo>)getSelectedPhotos());
        mContext.startActivity(intent);
    }

    public void openCamera() {
        initGalleryFinal();
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mFunctionConfig, mOnHanlderResultCallback);
    }
}
