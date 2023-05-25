package com.all.officereader.screens.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.isseiaoki.simplecropview.CropImageView;
import com.all.officereader.R;
import com.all.officereader.screens.ocr.popups.SignatureDialog;
import com.all.officereader.screens.ocr.view.StickerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EditScannerActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = EditScannerActivity.class.getName();
    private static EditScannerActivity editScannerActivity = null;
    private TextView tvNumberPage;
    private TextView tvConfirm;
    private ViewPager2 vpListOCR;
    private ImageView imgCancel;
    private ImageView imgDone;
    private ImageView imgBackScreen;
    private LinearLayout pbLoading;

    private LinearLayout lnRotate;
    private LinearLayout lnFlip;
    private LinearLayout lnCrop;
    private LinearLayout lnSign;
    private LinearLayout lnFilter;
    private LinearLayout lnDelete;
    private LinearLayout lnBottomBar;

    private LinearLayout lnBackFilter;
    private LinearLayout lnListFilter;
    private RecyclerView rvListFilter;
    private FilterAdapter filterAdapter;

    private boolean isSignature = false;
    private boolean isFilter = false;
    private boolean isClickCrop = false;
    private boolean isFlip = true;
    private boolean isRotate = true;
    private int mPosition = 0;
    private String path;
    private String mType;
    private String isGallery;
    private PageOcrAdapter pageOcrAdapter;
    private ArrayList<PreviewFragment> fragments;
    public static ArrayList<String> savedImgList = new ArrayList();
    private ArrayList<Float> listFilter = new ArrayList();
    private ArrayList<Integer> mListColor = new ArrayList();
    private PreviewFragment currentChildImage;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static EditScannerActivity getInstance() {
        return editScannerActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_scanner);
        editScannerActivity = this;

        initViews();
        initEvent();
    }

    private void initViews() {
        imgBackScreen = findViewById(R.id.iv_back);
        imgCancel = findViewById(R.id.iv_cancel);
        imgDone = findViewById(R.id.iv_done);
        tvNumberPage = findViewById(R.id.tv_index);
        tvConfirm = findViewById(R.id.iv_confirm);
        vpListOCR = findViewById(R.id.vp_img);
        pbLoading = findViewById(R.id.loading_view);

        lnRotate = findViewById(R.id.ln_rotate);
        lnFlip = findViewById(R.id.ln_flip);
        lnCrop = findViewById(R.id.ln_crop);
        lnSign = findViewById(R.id.ln_sign);
        lnFilter = findViewById(R.id.ln_filter);
        lnDelete = findViewById(R.id.ln_delete);
        lnBottomBar = findViewById(R.id.ln_toolbar);

        lnBackFilter = findViewById(R.id.ln_back_filter);
        rvListFilter = findViewById(R.id.rv_filter);
        lnListFilter = findViewById(R.id.ln_filter_child);

        mType = getIntent().getStringExtra("type");
        isGallery = getIntent().getStringExtra("isOpenGallery");
        savedImgList = getIntent().getStringArrayListExtra("list_data");

        if (mType.contains("OCR")) {
            lnSign.setVisibility(View.GONE);
            lnFilter.setVisibility(View.GONE);

        } else {
            lnSign.setVisibility(View.VISIBLE);
            lnFilter.setVisibility(View.VISIBLE);
        }

        tvNumberPage.setText("1/" + savedImgList.size());
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pbLoading.setVisibility(View.GONE);
        initListFilter();
    }

    private void initListFilter() {
        listFilter.add(0.8f);
        listFilter.add(1.0f);
        listFilter.add(1.2f);
        listFilter.add(1.4f);
        listFilter.add(1.6f);
        listFilter.add(1.8f);
        listFilter.add(2.0f);
        listFilter.add(2.2f);
        listFilter.add(2.4f);
        listFilter.add(2.6f);
        listFilter.add(2.8f);
        listFilter.add(3.0f);
        listFilter.add(3.2f);
        listFilter.add(3.4f);

        mListColor.add(getResources().getColor(R.color.color_filter1));
        mListColor.add(getResources().getColor(R.color.color_filter2));
        mListColor.add(getResources().getColor(R.color.color_filter3));
        mListColor.add(getResources().getColor(R.color.color_filter4));
        mListColor.add(getResources().getColor(R.color.color_filter5));
        mListColor.add(getResources().getColor(R.color.color_filter6));
        mListColor.add(getResources().getColor(R.color.color_filter7));
        mListColor.add(getResources().getColor(R.color.color_filter8));
        mListColor.add(getResources().getColor(R.color.color_filter9));
        mListColor.add(getResources().getColor(R.color.color_filter10));
        mListColor.add(getResources().getColor(R.color.color_filter11));
        mListColor.add(getResources().getColor(R.color.color_filter12));
        mListColor.add(getResources().getColor(R.color.color_filter13));
        mListColor.add(getResources().getColor(R.color.color_filter14));

        Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(vpListOCR.getCurrentItem()));
        filterAdapter = new FilterAdapter(listFilter, this, bm, mListColor);
        filterAdapter.setOnClickItem(new FilterAdapter.onClickItem() {
            @Override
            public void onClickItem(int position) {
                if (currentChildImage.cropImageView != null) {
                    Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(vpListOCR.getCurrentItem()));
                    startFilter(currentChildImage.cropImageView, bm, mListColor.get(position));
                }
            }
        });
        rvListFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvListFilter.setAdapter(filterAdapter);
        filterAdapter.notifyDataSetChanged();
    }

    public void startFilter(CropImageView cropImageView, Bitmap bitmap, float f) {
        Bitmap modifiedBitmap = bitmap == null ? null : BitmapExtKt.adjustedContrast$default(bitmap, f, 0.0f, 0, 6, null);
        cropImageView.setImageBitmap(modifiedBitmap);
    }

    private void initAdapter() {
        fragments = new ArrayList<>();
        fragments.clear();
        for (int i = 0; i < savedImgList.size(); i++) {
            currentChildImage = new PreviewFragment();
            //Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(i));
            currentChildImage.setBitmapCrop(savedImgList.get(i));
            fragments.add(i, currentChildImage);
        }

        pageOcrAdapter = new PageOcrAdapter(EditScannerActivity.this, fragments);
        vpListOCR.setAdapter(pageOcrAdapter);
        vpListOCR.setCurrentItem(0);
        vpListOCR.setPageTransformer(new ZoomOutPageTransformer());
        vpListOCR.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPosition = position;
                tvNumberPage.setText((position + 1) + "/" + savedImgList.size());
                currentChildImage = pageOcrAdapter.getChildAt(position);
            }
        });
        pageOcrAdapter.notifyDataSetChanged();
    }

    private void initEvent() {
        tvConfirm.setOnClickListener(this);
        lnRotate.setOnClickListener(this);
        lnFlip.setOnClickListener(this);
        lnCrop.setOnClickListener(this);
        lnSign.setOnClickListener(this);
        lnFilter.setOnClickListener(this);
        lnDelete.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgBackScreen.setOnClickListener(this);
        lnBackFilter.setOnClickListener(this);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onClick(View v) {
        if (v == tvConfirm) {
            pbLoading.setVisibility(View.VISIBLE);

            if (mType.contains("OCR")) {
                Intent intent = new Intent(this, EditOCRActivity.class);
                intent.putExtra("isOpenGallery", isGallery);
                intent.putStringArrayListExtra("list_data", savedImgList);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(this, PreviewPDFActivity.class);
                intent.putStringArrayListExtra("List_image_scanner", savedImgList);
                startActivity(intent);
                finish();
            }

        } else if (v == lnRotate) {
            Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(vpListOCR.getCurrentItem()));
            if (isRotate) {
                currentChildImage.funcRotate(bm, 90f);
            } else {
                currentChildImage.funcRotate(bm, 0f);
            }
            isRotate = !isRotate;

        } else if (v == lnFlip) {
            Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(vpListOCR.getCurrentItem()));
            if (isFlip) {
                currentChildImage.funcFlip(bm, 180f);
            } else {
                currentChildImage.funcFlip(bm, 0f);
            }
            isFlip = !isFlip;

        } else if (v == lnCrop) {
            funcEditBottom();
            if (!isClickCrop) {
                currentChildImage.startCrop();
                isClickCrop = true;
            }

        } else if (v == lnSign) {
            SignatureDialog signatureDialog = new SignatureDialog(this);
            signatureDialog.setOnExportListener(new SignatureDialog.onDrawListener() {
                @Override
                public void onDraw(Bitmap bitmap) {
                    isSignature = true;
                    funcEditBottom();
                    currentChildImage.funcSignature(bitmap);
                }
            });
            signatureDialog.show();

        } else if (v == lnFilter) {
            funcFilter();

        } else if (v == lnDelete) {
            try {
                if (savedImgList != null) {
                    savedImgList.remove(vpListOCR.getCurrentItem());
                    initAdapter();
                    if (savedImgList.size() == 0) {
                        finish();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "click_Delete_Exception: " + e.getMessage());
                e.printStackTrace();
            }

        } else if (v == imgBackScreen) {
            funcBackOCR();

        } else if (v == lnBackFilter) {
            funcBackOCR();

        } else if (v == imgCancel) {
            if (currentChildImage != null) {
                currentChildImage.cancelCrop();
                funcSelectCrop();
                isClickCrop = false;
            }

        } else if (v == imgDone) {
            funcSelectCrop();

            if (isSignature) {
                if (currentChildImage != null && currentChildImage.flItem != null) {
                    savedImgList.set(mPosition, "");
                    confirmSignature(currentChildImage.flItem);
                }
            } else if (isClickCrop) {
                currentChildImage.stopCrop();
                pageOcrAdapter.notifyDataSetChanged();
                isClickCrop = false;
            }
        }
    }

    private void funcSelectCrop() {
        vpListOCR.setUserInputEnabled(true);
        imgCancel.setVisibility(View.GONE);
        imgDone.setVisibility(View.GONE);
        lnListFilter.setVisibility(View.GONE);

        tvConfirm.setVisibility(View.VISIBLE);
        lnBottomBar.setVisibility(View.VISIBLE);
    }

    private void funcEditBottom() {
        vpListOCR.setUserInputEnabled(false);
        imgCancel.setVisibility(View.VISIBLE);
        imgDone.setVisibility(View.VISIBLE);
        tvConfirm.setVisibility(View.GONE);
    }

    public void confirmSignature(FrameLayout flPreview) {
        int childCount = flPreview.getChildCount();
        if (childCount > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                View childAt = flPreview.getChildAt(i);
                if (childAt instanceof StickerView) {
                    ((StickerView) childAt).setInEdit(false);
                }
                if (i2 < childCount) {
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }

    private void funcFilter() {
        vpListOCR.setUserInputEnabled(false);
        isFilter = true;
        Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(vpListOCR.getCurrentItem()));
        filterAdapter.refreshImage(bm);

        imgCancel.setVisibility(View.VISIBLE);
        imgDone.setVisibility(View.VISIBLE);
        lnListFilter.setVisibility(View.VISIBLE);

        tvConfirm.setVisibility(View.GONE);
        lnBottomBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        funcBackOCR();
    }

    private void funcBackOCR() {
        if (lnBottomBar.getVisibility() == View.GONE) {
            imgCancel.setVisibility(View.GONE);
            imgDone.setVisibility(View.GONE);
            lnListFilter.setVisibility(View.GONE);

            tvConfirm.setVisibility(View.VISIBLE);
            lnBottomBar.setVisibility(View.VISIBLE);
            vpListOCR.setUserInputEnabled(true);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editScannerActivity = null;
    }
}
