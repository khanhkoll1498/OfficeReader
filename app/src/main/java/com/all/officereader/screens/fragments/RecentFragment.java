package com.all.officereader.screens.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.all.officereader.R;
import com.all.officereader.adapter.FileAdsFragmentAdapter;
import com.all.officereader.helpers.bases.BaseFragment;
import com.all.officereader.helpers.utils.StorageUtils;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.screens.activities.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class RecentFragment extends BaseFragment implements FileAdsFragmentAdapter.fileAdsFragmentAdapterListener {
    private static final String TAG = RecentFragment.class.getSimpleName();
    private LinearLayoutManager mLinearLayoutManager;
    private FileAdsFragmentAdapter mFileFragmentAdapter;
    private ArrayList<File> mListFileRecent = new ArrayList<>();
    private ItemFileClickListener mIoIOnTemClickListener;
    private StorageUtils mStorageUtils;
    private View view;
    private RecyclerView rcvFragmentRecentList;
    private LinearLayout rllRcvFragmentRecentNoFile;
    private ImageView imvFragmentRecentNoInternet;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIoIOnTemClickListener = (ItemFileClickListener) context;
    }

    public RecentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent, container, false);
        return view;
    }

    @Override
    public void loadingNativeBanner(MainActivity mainActivity) {
    }

    @Override
    public void checkInternetConnected(MainActivity activity) {
        if (imvFragmentRecentNoInternet != null && imvFragmentRecentNoInternet.getVisibility() == View.VISIBLE) {
            imvFragmentRecentNoInternet.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(View view) {
        mStorageUtils = new StorageUtils(mContext);
        mListFileRecent = new ArrayList<>();
        mListFileRecent = mStorageUtils.getRecent(mContext);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rcvFragmentRecentList = view.findViewById(R.id.rcv_fragment_recent__list);
        rllRcvFragmentRecentNoFile = view.findViewById(R.id.rll_rcv_fragment_recent__no_file);
        imvFragmentRecentNoInternet = view.findViewById(R.id.imv_fragment_recent__no_internet);
        rcvFragmentRecentList.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void initData() {
        initDataRecent();
    }

    private void initDataRecent() {
        if (mListFileRecent != null && mListFileRecent.size() == 0) {
            rllRcvFragmentRecentNoFile.setVisibility(View.VISIBLE);
        } else if (mListFileRecent != null) {
            rllRcvFragmentRecentNoFile.setVisibility(View.GONE);
            rcvFragmentRecentList.setVisibility(View.VISIBLE);
            mFileFragmentAdapter = new FileAdsFragmentAdapter(mListFileRecent, mContext, new ItemFileClickListener() {
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

            if (mListFileRecent.size() > 0) {
                rllRcvFragmentRecentNoFile.setVisibility(View.GONE);
            } else {
                rllRcvFragmentRecentNoFile.setVisibility(View.VISIBLE);
            }
            rcvFragmentRecentList.setAdapter(mFileFragmentAdapter);
            mFileFragmentAdapter.notifyDataSetChanged();
        }
    }

    public void updateData() {
        try {
            if (mListFileRecent != null && mListFileRecent.size() > 0) {
                mListFileRecent.clear();
            }
            mListFileRecent = new ArrayList<>();
            if (mStorageUtils != null) {
                mListFileRecent = mStorageUtils.getRecent(mContext);
            }
            mLinearLayoutManager = new LinearLayoutManager(mContext);
            if (rcvFragmentRecentList != null)
                rcvFragmentRecentList.setLayoutManager(mLinearLayoutManager);
            initDataRecent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdateData(Activity activity) {
        try {
            if (mFileFragmentAdapter != null) {
                if (mListFileRecent != null)
                    mListFileRecent.clear();
                if (mStorageUtils != null)
                    mListFileRecent.addAll(mStorageUtils.getRecent(activity));
                mFileFragmentAdapter.notifyDataSetChanged();

            } else {
                updateData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAds(boolean isNoAds) {
    }

    @Override
    public void onShowNativeBannerSuccess(boolean isShow) {
    }
}
