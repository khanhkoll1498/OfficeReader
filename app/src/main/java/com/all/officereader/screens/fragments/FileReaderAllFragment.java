package com.all.officereader.screens.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.Wave;
import com.all.officereader.R;
import com.all.officereader.adapter.FileAdsFragmentAdapter;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.helpers.utils.FileUtility;

import java.io.File;
import java.util.ArrayList;

public class FileReaderAllFragment extends Fragment implements FileAdsFragmentAdapter.fileAdsFragmentAdapterListener {
    private RecyclerView rcvFragmentFileList;
    private LinearLayoutManager mLinearLayoutManager;
    private FileAdsFragmentAdapter mFileFragmentAdapter;
    private ProgressBar pgbFragmentFileLoading;
    private ItemFileClickListener mIoIOnTemClickListener;
    private LinearLayout rllFragmentFileNoFile;
    private ArrayList<File> mFileArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView =  inflater.inflate(R.layout.fragment_file, container, false);
        mIoIOnTemClickListener = (ItemFileClickListener) getActivity();
        initView(mView);
        initData();
        return mView;
    }

    protected void initData() {
        Wave doubleBounce = new Wave();
        doubleBounce.setColor(getResources().getColor(R.color.colorWord_2392FF));
        pgbFragmentFileLoading.setIndeterminateDrawable(doubleBounce);
    }

    protected void initView(View view) {
        rcvFragmentFileList = view.findViewById(R.id.rcv_fragment_file_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rllFragmentFileNoFile = view.findViewById(R.id.rll_fragment_file__no_file);
        pgbFragmentFileLoading = view.findViewById(R.id.pgb_fragment_file__loading);
        rcvFragmentFileList.setLayoutManager(mLinearLayoutManager);

        updateData();
    }

    public void updateData() {
        Log.e("AAAAAAAA", "loadingFile: updateData: " + FileUtility.mAllFileOffice.size() + "...");
        mFileArrayList = new ArrayList<>();
        mFileArrayList.clear();
        mFileArrayList.addAll(FileUtility.mAllFileOffice);
        initDataRecent();
    }

    private void initDataRecent() {
        Log.e("AAAAAAAA", "loadingFile: initDataRecent_111111");
        try {
            Log.e("AAAAAAAA", "loadingFile: mFileArrayList: " + "..." + mFileArrayList.size() + "...FileUtility.mAllFileOffice: " + FileUtility.mAllFileOffice.size());
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
            if (FileUtility.mAllFileOffice.size() <= 0) {
                rllFragmentFileNoFile.setVisibility(View.VISIBLE);
                rcvFragmentFileList.setVisibility(View.GONE);
            } else {
                rllFragmentFileNoFile.setVisibility(View.GONE);
                rcvFragmentFileList.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("AAAAAAAA", "loadingFile: initDataRecent_Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onShowNativeBannerSuccess(boolean b) {
    }
}
