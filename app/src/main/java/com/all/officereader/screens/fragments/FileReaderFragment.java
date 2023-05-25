package com.all.officereader.screens.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.all.officereader.R;
import com.all.officereader.adapter.FileReaderPagerAdapter;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FileReaderFragment extends Fragment implements View.OnClickListener {
    private TextView tvAllFile;
    private TextView txvFlipActivityMainWord;
    private TextView txvFlipActivityMainPdf;
    private TextView txvFlipActivityMainExcel;
    private TextView txvFlipActivityMainPpt;
    private TextView tvTxt;
    private ViewPager vpgFragmentFileReaderPager;
    private FileReaderPagerAdapter mFileReaderPagerAdapter;
    private ArrayList<Fragment> mFragmentArrayList;

    private CardView cvAllFile;
    private CardView cvAllWord;
    private CardView cvAllPDF;
    private CardView cvAllExcel;
    private CardView cvAllSlide;
    private CardView cvAllTxt;

    public FileReaderAllFragment fileReaderAllFragment;
    private FileReaderDocFragment fileReaderDocFragment;
    private FileReaderPdfFragment fileReaderPdfFragment;
    private FileReaderExcelFragment fileReaderExcelFragment;
    private FileReaderPptFragment fileReaderPptFragment;
    private FileReaderTxtFragment fileReaderTxtFragment;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_file_reader, container, false);
        initData(mView);
        initView(mView);
        return mView;
    }

    protected void initData(View mView) {
        vpgFragmentFileReaderPager = mView.findViewById(R.id.vpg_fragment_file_reader_pager);

        fileReaderAllFragment = new FileReaderAllFragment();
        fileReaderDocFragment = new FileReaderDocFragment();
        fileReaderPdfFragment = new FileReaderPdfFragment();
        fileReaderExcelFragment = new FileReaderExcelFragment();
        fileReaderPptFragment = new FileReaderPptFragment();
        fileReaderTxtFragment = new FileReaderTxtFragment();

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(fileReaderAllFragment);
        mFragmentArrayList.add(fileReaderDocFragment);
        mFragmentArrayList.add(fileReaderPdfFragment);
        mFragmentArrayList.add(fileReaderExcelFragment);
        mFragmentArrayList.add(fileReaderPptFragment);
        mFragmentArrayList.add(fileReaderTxtFragment);

        mFileReaderPagerAdapter = new FileReaderPagerAdapter(getActivity(), getChildFragmentManager(), mFragmentArrayList);
        vpgFragmentFileReaderPager.setAdapter(mFileReaderPagerAdapter);
        vpgFragmentFileReaderPager.setOffscreenPageLimit(5);
        vpgFragmentFileReaderPager.setCurrentItem(0);
        OverScrollDecoratorHelper.setUpOverScroll(vpgFragmentFileReaderPager);

        vpgFragmentFileReaderPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                checkClickCategory(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    protected void initView(View view) {
        tvAllFile = view.findViewById(R.id.tv_all_file);
        txvFlipActivityMainWord = view.findViewById(R.id.txv_flip_activity_main__word);
        txvFlipActivityMainPdf = view.findViewById(R.id.txv_flip_activity_main__pdf);
        txvFlipActivityMainExcel = view.findViewById(R.id.txv_flip_activity_main__excel);
        txvFlipActivityMainPpt = view.findViewById(R.id.txv_flip_activity_main__ppt);

        cvAllFile = view.findViewById(R.id.cv_all_file);
        cvAllWord = view.findViewById(R.id.cv_word);
        cvAllPDF = view.findViewById(R.id.cv_pdf);
        cvAllExcel = view.findViewById(R.id.cv_excel);
        cvAllSlide = view.findViewById(R.id.cv_slide);
        cvAllTxt = view.findViewById(R.id.cv_txt);

        tvTxt = view.findViewById(R.id.tv_txt_file);
        onListener();
        //updateData();
    }

    public void updateData() {
        Log.e("AAAAAAAA", "loadingFile: updateData_fragment: " + fileReaderAllFragment);
        if (fileReaderAllFragment != null) {
            fileReaderAllFragment.updateData();
        }

        if (fileReaderDocFragment != null)
            fileReaderDocFragment.updateData();
        if (fileReaderPdfFragment != null)
            fileReaderPdfFragment.updateData();
        if (fileReaderExcelFragment != null)
            fileReaderExcelFragment.updateData();
        if (fileReaderPptFragment != null)
            fileReaderPptFragment.updateData();
        if (fileReaderTxtFragment != null)
            fileReaderTxtFragment.updateData();
    }

    private void onListener() {
        tvAllFile.setOnClickListener(this);
        txvFlipActivityMainWord.setOnClickListener(this);
        txvFlipActivityMainPdf.setOnClickListener(this);
        txvFlipActivityMainExcel.setOnClickListener(this);
        txvFlipActivityMainPpt.setOnClickListener(this);
        tvTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tvAllFile) {
            checkClickCategory(0);
        } else if (view == txvFlipActivityMainWord) {
            checkClickCategory(1);
        } else if (view == txvFlipActivityMainPdf) {
            checkClickCategory(2);
        } else if (view == txvFlipActivityMainExcel) {
            checkClickCategory(3);
        } else if (view == txvFlipActivityMainPpt) {
            checkClickCategory(4);
        } else if (view == tvTxt) {
            checkClickCategory(5);
        }
    }

    private void checkClickCategory(int pos) {
        vpgFragmentFileReaderPager.setCurrentItem(pos);
        switch (pos) {
            case 0:
                cvAllFile.setCardBackgroundColor(getResources().getColor(R.color.blue));
                tvAllFile.setTextColor(getResources().getColor(R.color.colorWhite));

                cvAllWord.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainWord.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllPDF.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPdf.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllExcel.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainExcel.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllSlide.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPpt.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllTxt.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvTxt.setTextColor(getResources().getColor(R.color.colorGray));

                break;

            case 1:
                cvAllFile.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvAllFile.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllWord.setCardBackgroundColor(getResources().getColor(R.color.blue));
                txvFlipActivityMainWord.setTextColor(getResources().getColor(R.color.colorWhite));

                cvAllPDF.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPdf.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllExcel.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainExcel.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllSlide.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPpt.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllTxt.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvTxt.setTextColor(getResources().getColor(R.color.colorGray));

                break;
            case 2:
                cvAllFile.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvAllFile.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllWord.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainWord.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllPDF.setCardBackgroundColor(getResources().getColor(R.color.blue));
                txvFlipActivityMainPdf.setTextColor(getResources().getColor(R.color.colorWhite));

                cvAllExcel.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainExcel.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllSlide.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPpt.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllTxt.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvTxt.setTextColor(getResources().getColor(R.color.colorGray));
                break;

            case 3:
                cvAllFile.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvAllFile.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllWord.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainWord.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllPDF.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPdf.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllExcel.setCardBackgroundColor(getResources().getColor(R.color.blue));
                txvFlipActivityMainExcel.setTextColor(getResources().getColor(R.color.colorWhite));

                cvAllSlide.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPpt.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllTxt.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvTxt.setTextColor(getResources().getColor(R.color.colorGray));
                break;

            case 4:
                cvAllFile.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvAllFile.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllWord.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainWord.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllPDF.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPdf.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllExcel.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainExcel.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllSlide.setCardBackgroundColor(getResources().getColor(R.color.blue));
                txvFlipActivityMainPpt.setTextColor(getResources().getColor(R.color.colorWhite));

                cvAllTxt.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvTxt.setTextColor(getResources().getColor(R.color.colorGray));

                break;

            case 5:
                cvAllFile.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvAllFile.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllWord.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainWord.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllPDF.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPdf.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllExcel.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainExcel.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllSlide.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvFlipActivityMainPpt.setTextColor(getResources().getColor(R.color.colorGray));

                cvAllTxt.setCardBackgroundColor(getResources().getColor(R.color.blue));
                tvTxt.setTextColor(getResources().getColor(R.color.colorWhite));

                break;
        }
    }
}
