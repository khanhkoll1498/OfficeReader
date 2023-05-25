package com.all.officereader.screens.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.Wave;
import com.all.officereader.R;
import com.all.officereader.adapter.FileAdsFragmentAdapter;
import com.all.officereader.helpers.bases.BaseFragment;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.helpers.utils.FileUtility;
import com.all.officereader.screens.activities.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class FileReaderPdfFragment extends BaseFragment implements FileAdsFragmentAdapter.fileAdsFragmentAdapterListener {
    private RecyclerView rcvFragmentFileList;
    private LinearLayoutManager mLinearLayoutManager;
    private FileAdsFragmentAdapter mFileFragmentAdapter;
    private LinearLayout rllFragmentFileNoFile;
    private ArrayList<File> mFileArrayList;

    private ItemFileClickListener mIoIOnTemClickListener;
    private ProgressBar pgbFragmentFileLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIoIOnTemClickListener = (ItemFileClickListener) context;
    }

    public FileReaderPdfFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

    @Override
    public void loadingNativeBanner(MainActivity mainActivity) {
    }

    @Override
    public void checkInternetConnected(MainActivity activity) {
    }

    @Override
    protected void initView(View view) {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rcvFragmentFileList = view.findViewById(R.id.rcv_fragment_file_list);
        rllFragmentFileNoFile = view.findViewById(R.id.rll_fragment_file__no_file);
        pgbFragmentFileLoading = view.findViewById(R.id.pgb_fragment_file__loading);
        rcvFragmentFileList.setLayoutManager(mLinearLayoutManager);

        updateData();
    }

    @Override
    protected void initData() {
        Wave doubleBounce = new Wave();
        doubleBounce.setColor(getResources().getColor(R.color.colorWord_2392FF));
        pgbFragmentFileLoading.setIndeterminateDrawable(doubleBounce);
    }

    public void updateData(){
        mFileArrayList = new ArrayList<>();
        mFileArrayList.clear();
        mFileArrayList.addAll(FileUtility.mPdfFiles);
        initDataRecent();
    }

    private void initDataRecent() {
        try {
            mFileFragmentAdapter = new FileAdsFragmentAdapter(mFileArrayList, getActivity(), new ItemFileClickListener() {
                @Override
                public void onItemClick(File file) {
                    if (mIoIOnTemClickListener != null) {
                        mIoIOnTemClickListener.onItemClick(file);
                    }
                }

                @Override
                public void onAddToBookmark(File file) {
                    if (mIoIOnTemClickListener != null) {
                        mIoIOnTemClickListener.onAddToBookmark(file);
                    }
                }

                @Override
                public void onCreateShortCut(File file) {
                    if (mIoIOnTemClickListener != null) {
                        mIoIOnTemClickListener.onCreateShortCut(file);
                    }
                }

                @Override
                public void onShareFile(File file) {
                    if (mIoIOnTemClickListener != null) {
                        mIoIOnTemClickListener.onShareFile(file);
                    }
                }

                @Override
                public void onRemoveBookmark(File file) {

                }

            }, this);
            rcvFragmentFileList.setAdapter(mFileFragmentAdapter);
            mFileFragmentAdapter.notifyDataSetChanged();
            pgbFragmentFileLoading.setVisibility(View.GONE);
            if (FileUtility.mPdfFiles.size() <= 0) {
                rllFragmentFileNoFile.setVisibility(View.VISIBLE);
                rcvFragmentFileList.setVisibility(View.GONE);
            } else {
                rllFragmentFileNoFile.setVisibility(View.GONE);
                rcvFragmentFileList.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAds(boolean isNoAds) {
        // TODO
    }

    @Override
    public void onShowNativeBannerSuccess(boolean b) {
        // TODO
    }
}
