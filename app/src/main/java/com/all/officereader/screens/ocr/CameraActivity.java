package com.all.officereader.screens.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.all.officereader.R;
import com.all.officereader.screens.ocr.fragments.TabOCRFragment;
import com.all.officereader.screens.ocr.fragments.TabScannerFragment;
import com.all.officereader.screens.ocr.gallery.GalleryActivity;
import com.all.officereader.screens.ocr.general.ControlUtils;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Flash;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CameraActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView imgBackExit;
    private ImageView imgFlash;
    private ImageView imgListImage;
    private ImageView imgTakeCamera;
    private ImageView imgSaveFile;

    private CameraView cameraView;
    private ViewPager viewPager2;
    public static ArrayList<String> savedImgList = new ArrayList();
    public static ArrayList<Bitmap> listBitmapOCR = new ArrayList();
    private GalleryPagerAdapter galleryPagerAdapter;
    private ArrayList<Fragment> mFragmentArrayList;
    private TabOCRFragment tabOCRFragment;
    private TabScannerFragment tabScannerFragment;

    private TextView tvNext;
    private TextView tvCount;
    private int imgCount;
    private boolean isClickFlash = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initViews();
        initEvent();
    }

    private void initViews() {
        imgBackExit = findViewById(R.id.iv_back);
        imgFlash = findViewById(R.id.iv_flash);
        imgListImage = findViewById(R.id.iv_img_list);
        imgTakeCamera = findViewById(R.id.iv_take_pic);
        imgSaveFile = findViewById(R.id.iv_saved_file);

        tvNext = findViewById(R.id.tv_next);
        tvCount = findViewById(R.id.tv_img_count);
        cameraView = findViewById(R.id.camera);
        viewPager2 = findViewById(R.id.vp_tab);

        tabOCRFragment = new TabOCRFragment();
        tabScannerFragment = new TabScannerFragment();

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(tabOCRFragment);
        mFragmentArrayList.add(tabScannerFragment);

        galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(), mFragmentArrayList);
        viewPager2.setAdapter(galleryPagerAdapter);
        viewPager2.setCurrentItem(0);
        viewPager2.setOffscreenPageLimit(2);

        listBitmapOCR.clear();
        savedImgList.clear();
        cameraView.setLifecycleOwner(this);
        CameraLogger.setLogLevel(CameraLogger.LEVEL_VERBOSE);
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull @NotNull PictureResult result) {
                imgCount += 1;
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(result.getData(), 0, result.getData().length);
                imgListImage.setImageBitmap(decodeByteArray);
                listBitmapOCR.add(decodeByteArray);

                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(String.valueOf(imgCount));
                savedImgList.add(ControlUtils.saveImage(CameraActivity.this, result.getData()));
            }
        });
    }

    public static ArrayList<String> getAllImageSelect() {
        return savedImgList;
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] b = byteArray.toByteArray();
        String result = Base64.encodeToString(b, Base64.DEFAULT);
        return result;
    }

    private void initEvent() {
        imgBackExit.setOnClickListener(this);
        imgFlash.setOnClickListener(this);
        imgListImage.setOnClickListener(this);
        imgTakeCamera.setOnClickListener(this);
        imgSaveFile.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgBackExit) {
            finish();

        } else if (v == imgFlash) {
            if (cameraView != null) {
                if (isClickFlash) {
                    cameraView.setFlash(Flash.TORCH);
                    imgFlash.setImageResource(R.drawable.ic_flash_off);
                } else {
                    cameraView.setFlash(Flash.OFF);
                    imgFlash.setImageResource(R.drawable.ic_flash_on);
                }
                isClickFlash = !isClickFlash;
            }

        } else if (v == imgListImage) {
            Intent intent = new Intent(this, GalleryActivity.class);
            if (viewPager2.getCurrentItem() == 0) {
                intent.putExtra("type", "OCR");
            } else {
                intent.putExtra("type", "Scanner");
            }
            startActivity(intent);
            finish();

        } else if (v == imgTakeCamera) {
            cameraView.takePicture();

        } else if (v == imgSaveFile) {
            Intent intent = new Intent(this, ListOCRActivity.class);
            startActivity(intent);
            finish();

        } else if (v == tvNext) {
            if (savedImgList != null && savedImgList.size() > 0) {
                if (viewPager2.getCurrentItem() == 0) {
                    Intent intent = new Intent(this, EditScannerActivity.class);
                    intent.putExtra("type", "OCR");
                    intent.putExtra("isOpenGallery", "false");
                    intent.putStringArrayListExtra("list_data", savedImgList);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(this, EditScannerActivity.class);
                    intent.putExtra("type", "Scanner");
                    intent.putExtra("isOpenGallery", "false");
                    intent.putStringArrayListExtra("list_data", savedImgList);
                    startActivity(intent);
                }
                finish();
            } else {
                Toast.makeText(this, "Please choose a Photo!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
