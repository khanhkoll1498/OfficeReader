package com.all.officereader.screens.ocr.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.ads.control.AdmobHelp;
import com.all.officereader.R;
import com.all.officereader.screens.ocr.EditScannerActivity;
import com.all.officereader.screens.ocr.GalleryPagerAdapter;
import com.all.officereader.screens.ocr.fragments.FolderFragments;
import com.all.officereader.screens.ocr.fragments.GalleryFragments;

import java.util.ArrayList;

public class GalleryActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView imgBack;
    private static TextView tvConfirm;
    private ViewPager vpListGallery;
    private GalleryPagerAdapter galleryPagerAdapter;
    private ArrayList<Fragment> mFragmentArrayList;

    private GalleryFragments galleryFragments;
    private FolderFragments folderFragments;

    private CardView cvImage;
    private CardView cvAlbum;

    private TextView tvImage;
    private TextView tvAlbum;

    private String type;
    public static ArrayList<String> savedImgList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image);

        initViews();
        initEvent();
        initAdapter();
    }

    private void initViews() {
        type = getIntent().getStringExtra("type");

        vpListGallery = findViewById(R.id.vp_image);
        tvConfirm = findViewById(R.id.tv_confirm);
        imgBack = findViewById(R.id.iv_back);

        cvImage = findViewById(R.id.cv_image);
        cvAlbum = findViewById(R.id.cv_album);
        tvImage = findViewById(R.id.tv_image);
        tvAlbum = findViewById(R.id.tv_album);
    }

    private void initEvent() {
        imgBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        cvImage.setOnClickListener(this);
        cvAlbum.setOnClickListener(this);
    }

    private void initAdapter() {
        galleryFragments = new GalleryFragments();
        folderFragments = new FolderFragments();

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(galleryFragments);
        mFragmentArrayList.add(folderFragments);

        savedImgList.clear();
        galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(), mFragmentArrayList);
        vpListGallery.setAdapter(galleryPagerAdapter);
        vpListGallery.setOffscreenPageLimit(2);
        vpListGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                funcScroll(position, false);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvConfirm.setText(getResources().getString(R.string.confirm_x));

        funcCountSelect();
        AdmobHelp.getInstance().loadBanner(this);
    }

    private void funcScroll(int position, boolean isScroll) {
        switch (position) {
            case 0:
                if (isScroll)
                    vpListGallery.setCurrentItem(0);
                cvImage.setCardBackgroundColor(getResources().getColor(R.color.blue));
                tvImage.setTextColor(getResources().getColor(R.color.colorWhite));

                cvAlbum.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvAlbum.setTextColor(getResources().getColor(R.color.colorGray));
                break;

            case 1:
                if (isScroll)
                    vpListGallery.setCurrentItem(1);
                cvImage.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvImage.setTextColor(getResources().getColor(R.color.colorGray));

                cvAlbum.setCardBackgroundColor(getResources().getColor(R.color.blue));
                tvAlbum.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
        }
    }

    private static void funcCountSelect() {
        if (GalleryActivity.savedImgList != null) {
            if (GalleryActivity.savedImgList.size() > 0) {
                tvConfirm.setEnabled(true);
                tvConfirm.setBackgroundResource(R.drawable.bg_enable_dialog);
            } else {
                tvConfirm.setEnabled(false);
                tvConfirm.setBackgroundResource(R.drawable.bg_disable);
            }
        } else {
            tvConfirm.setEnabled(false);
            tvConfirm.setBackgroundResource(R.drawable.bg_disable);
        }
    }

    public static void countImage(ArrayList<String> listImage) {
        tvConfirm.setText("Confirm " + listImage.size());
        funcCountSelect();
    }

    @Override
    public void onClick(View v) {
        if (v == imgBack) {
            funcBackScreen();

        } else if (v == tvConfirm) {
            if (type.contains("OCR")) {
                Intent intent = new Intent(this, EditScannerActivity.class);
                intent.putExtra("type", "OCR");
                intent.putExtra("isOpenGallery", "true");
                intent.putStringArrayListExtra("list_data", savedImgList);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(this, EditScannerActivity.class);
                intent.putExtra("type", "Scanner");
                intent.putExtra("isOpenGallery", "true");
                intent.putStringArrayListExtra("list_data", savedImgList);
                startActivity(intent);
                finish();
            }
        } else if (v == cvImage) {
            funcScroll(0, true);

        } else if (v == cvAlbum) {
            funcScroll(1, true);
        }
    }

    @Override
    public void onBackPressed() {
        funcBackScreen();
    }

    private void funcBackScreen() {
        if (vpListGallery.getCurrentItem() == 0) {
            finish();
        } else {
            if (folderFragments != null && folderFragments.isVisible()) {
                if (!folderFragments.onBackFolder()) {
                    finish();
                }
            } else {
                finish();
            }
        }
    }
}
