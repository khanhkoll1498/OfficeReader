package com.all.officereader.screens.ocr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.all.officereader.R;
import com.all.officereader.screens.ocr.general.ControlUtils;

import java.io.File;
import java.util.ArrayList;

public class EditOCRActivity extends FragmentActivity implements View.OnClickListener {

    private static EditOCRActivity editOCRActivity = null;

    private ImageView imgBack;
    private TextView tvFinish;
    private TextView tvSave;
    private TextView tvIndicator;

    private ViewPager viewPager2;
    private LinearLayout lnCopy;
    private LinearLayout lnEdit;
    private LinearLayout lnShare;

    private int currentPosition = -1;
    private String isGallery;
    private EditOCRAdapter editOCRAdapter;
    private ArrayList<String> savedImgList;
    private ArrayList<String> listPareOCR = new ArrayList();
    private ArrayList<String> newListEditOCR = new ArrayList();

    public static EditOCRActivity getInstance() {
        return editOCRActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ocr);

        initViews();
        initEvent();
    }

    private void initViews() {
        editOCRActivity = this;

        imgBack = findViewById(R.id.iv_back);
        tvFinish = findViewById(R.id.tv_finish);
        tvSave = findViewById(R.id.tv_save);
        tvIndicator = findViewById(R.id.tv_indicator);

        viewPager2 = findViewById(R.id.vp_result);
        lnCopy = findViewById(R.id.ln_copy);
        lnEdit = findViewById(R.id.ln_edit);
        lnShare = findViewById(R.id.ln_share);

        savedImgList = new ArrayList();
        savedImgList.clear();
        savedImgList = getIntent().getStringArrayListExtra("list_data");
        isGallery = getIntent().getStringExtra("isOpenGallery");

        initAdapterOCR(savedImgList, isGallery);
    }

    private void initAdapterOCR(ArrayList<String> mListOCR, String isGallery) {
        tvIndicator.setText(1 + File.separator + mListOCR.size());
        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvIndicator.setText((position + 1) + File.separator + mListOCR.size());
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        for (int i = 0; i < savedImgList.size(); i++) {
            Bitmap bm = BitmapFactory.decodeFile(savedImgList.get(i));
            runTextRecognition(bm);
        }
    }

    private void runTextRecognition(Bitmap decodeByteArray) {
        listPareOCR.add(ControlUtils.inspectFromBitmap(this, decodeByteArray));
        initAdapter(listPareOCR);
    }

    private void initEvent() {
        imgBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        lnCopy.setOnClickListener(this);
        lnEdit.setOnClickListener(this);
        lnShare.setOnClickListener(this);
    }

    private void initAdapter(ArrayList<String> listOCR) {
        editOCRAdapter = new EditOCRAdapter(this, listOCR);
        viewPager2.setCurrentItem(0);
        viewPager2.setAdapter(editOCRAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == imgBack) {
            finish();

        } else if (v == tvFinish) {
            String fileName = "NewOCR" + System.currentTimeMillis() + ".txt";
            for (int i = 0; i < listPareOCR.size(); i++) {
                if (editOCRAdapter.pathOCR == null) {
                    newListEditOCR.add(listPareOCR.get(i));
                } else
                    newListEditOCR.add(i, editOCRAdapter.pathOCR);
            }
            ControlUtils.funcCreateFileOCR(EditOCRActivity.this, fileName, newListEditOCR);

        } else if (v == tvSave) {

        } else if (v == lnCopy) {
            ControlUtils.copyToClipboard(EditOCRActivity.this, listPareOCR.get(viewPager2.getCurrentItem()));
            editOCRAdapter.notifyDataSetChanged();

        } else if (v == lnEdit) {
            if (editOCRAdapter != null) {
                if (editOCRAdapter.editText != null) {
                    editOCRAdapter.editText.setFocusable(true);
                    editOCRAdapter.editText.requestFocus();
                    ControlUtils.showKeyboard(this);
                    editOCRAdapter.notifyDataSetChanged();
                }
            }

        } else if (v == lnShare) {
            ControlUtils.shareText(this, listPareOCR.get(viewPager2.getCurrentItem()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editOCRActivity = null;
    }
}
