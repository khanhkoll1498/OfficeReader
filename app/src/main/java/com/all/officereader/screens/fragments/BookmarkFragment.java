package com.all.officereader.screens.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.all.officereader.R;
import com.all.officereader.helpers.bases.BaseFragment;
import com.all.officereader.helpers.utils.StorageUtils;
import com.all.officereader.adapter.BookmarkAdapter;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.screens.activities.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class BookmarkFragment extends BaseFragment {
    private static final String TAG = BookmarkFragment.class.getSimpleName();
    private LinearLayoutManager mLinearLayoutManager;
    private BookmarkAdapter mFileFragmentAdapter;
    private ArrayList<File> mListFileBookMark = new ArrayList<>();
    private ItemFileClickListener mIoIOnTemClickListener;
    private StorageUtils mStorageUtils;
    private View view;
    private ImageView imvFragmentBookmarkNoInternet;
    private RelativeLayout rllFragmentBookmarkBanner;
    private RecyclerView rcvFragmentBookmarkList;
    private LinearLayout rllRcvFragmentBookMarkNoFile;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIoIOnTemClickListener = (ItemFileClickListener) context;
    }

    public BookmarkFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        return view;
    }

    @Override
    public void loadingNativeBanner(MainActivity mainActivity) {
    }

    @Override
    public void checkInternetConnected(MainActivity activity) {
        if (imvFragmentBookmarkNoInternet != null && imvFragmentBookmarkNoInternet.getVisibility() == View.VISIBLE) {
            imvFragmentBookmarkNoInternet.setVisibility(View.GONE);
        }
    }

    private void initDataBookmark() {
        try {
            if (mListFileBookMark != null && mListFileBookMark.size() == 0) {
                rllRcvFragmentBookMarkNoFile.setVisibility(View.VISIBLE);
            } else if (mListFileBookMark != null) {
                rllRcvFragmentBookMarkNoFile.setVisibility(View.GONE);
                mFileFragmentAdapter = new BookmarkAdapter(mListFileBookMark, mContext, new ItemFileClickListener() {
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
                        if (mIoIOnTemClickListener != null) {
                            mIoIOnTemClickListener.onRemoveBookmark(file);
                        }
                    }

                });
                rcvFragmentBookmarkList.setAdapter(mFileFragmentAdapter);
                mFileFragmentAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(View view) {
        mStorageUtils = new StorageUtils(mContext);
        mListFileBookMark = new ArrayList<>();
        mListFileBookMark = mStorageUtils.getBookmark(mContext);
        mLinearLayoutManager = new LinearLayoutManager(mContext);

        imvFragmentBookmarkNoInternet = view.findViewById(R.id.imv_fragment_bookmark__no_internet);
        rllFragmentBookmarkBanner = view.findViewById(R.id.rll_fragment_bookmark_banner);
        rcvFragmentBookmarkList = view.findViewById(R.id.rcv_fragment_bookmark_list);
        rllRcvFragmentBookMarkNoFile = view.findViewById(R.id.rll_rcv_fragment_book_mark__no_file);
        rcvFragmentBookmarkList.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void initData() {
        initDataBookmark();
    }

    public void updateData() {
        try {
            if (mListFileBookMark != null && mListFileBookMark.size() > 0) {
                mListFileBookMark.clear();
            }
            mListFileBookMark = new ArrayList<>();
            if (mStorageUtils != null)
                mListFileBookMark = mStorageUtils.getBookmark(mContext);
            initDataBookmark();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdateData(Activity activity) {
        if (mFileFragmentAdapter != null) {

            mListFileBookMark.clear();
            mListFileBookMark.addAll(mStorageUtils.getBookmark(activity));
            Log.e(TAG, "onUpdateData: " + mListFileBookMark.size());
            Log.e(TAG, "onUpdateData2: " + mStorageUtils.getBookmark(activity).size());
            mFileFragmentAdapter.notifyDataSetChanged();

        } else {
            updateData();
        }
    }

    @Override
    public void updateAds(boolean isNoAds) {
        if (isNoAds) {
            if (rllFragmentBookmarkBanner != null && rllFragmentBookmarkBanner.getVisibility() == View.VISIBLE) {
                rllFragmentBookmarkBanner.setVisibility(View.GONE);
            }
        } else {
            if (rllFragmentBookmarkBanner != null && rllFragmentBookmarkBanner.getVisibility() == View.GONE) {
                rllFragmentBookmarkBanner.setVisibility(View.VISIBLE);
            }
        }
    }
}
