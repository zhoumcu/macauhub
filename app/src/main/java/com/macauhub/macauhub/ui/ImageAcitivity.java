package com.macauhub.macauhub.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.macauhub.macauhub.R;
import com.macauhub.macauhub.utils.SmoothImageView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ymn on 2017/4/16.
 */
public class ImageAcitivity extends AppCompatActivity {
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.img_photo)
    SmoothImageView imgPhoto;
    private int mLocationX;
    private int mLocationY;
    private int mHeight;
    private int mWidth;
    private String mDatas;
    private float scale = 1.0f;
    private PhotoViewAttacher attacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_img);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        mDatas = (String) getIntent().getStringExtra("images");
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);
        imgPhoto.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imgPhoto.transformIn();
        //imgPhoto.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imgPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        attacher = new PhotoViewAttacher(imgPhoto);
        attacher.setZoomable(true);
        //attacher.setMinimumScale(1.50f);
        attacher.setZoomTransitionDuration(1000);
        attacher.update();
//        ImageLoader.getInstance().displayImage(mDatas, imageView);
        imgPhoto.setImageResource(R.drawable.map);
        handler.postDelayed(runnable, 100);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scale += 0.1f;
            if (scale < 3.00f) {
                attacher.setScale(scale, true);
                attacher.update();
                handler.postDelayed(runnable, 100);
            }
        }
    };
    private Handler handler = new Handler();

    @OnClick(R.id.close)
    public void onClick() {
        finish();
    }
}
