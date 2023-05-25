package com.all.officereader.screens.fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.all.officereader.R;
import com.all.officereader.adapter.FileManagerAdapter;
import com.all.officereader.helpers.bases.BaseFragment;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.helpers.utils.ExtensionFilter;
import com.all.officereader.helpers.utils.FileUtility;
import com.all.officereader.helpers.utils.Utility;
import com.all.officereader.model.DialogProperties;
import com.all.officereader.model.FileListItem;
import com.all.officereader.screens.activities.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class FileManagerFragment extends BaseFragment implements FileManagerAdapter.onFileManagerAdapterInterface {
    private static final String TAG = FileManagerFragment.class.getCanonicalName();
    private ExtensionFilter mExtensionFilter;
    private FileManagerAdapter mFileManagerAdapter;
    private ArrayList<FileListItem> mFileListItems;
    private DialogProperties mDialogProperties;
    private ItemFileClickListener mItemFileClickListener;

    private RelativeLayout flipFileManagerFlipView;
    private ImageView imvFileManagerNoInternet;
    private RelativeLayout rllFileManagerBanner;
    private RecyclerView rcvFragmentFileManagerMain;
    private fileManagerFragmentListener mFileManagerFragmentListener;

    public FileManagerFragment() {
    }

    @Override
    public void onAttach(@NotNull Context mContext) {
        super.onAttach(mContext);
        mItemFileClickListener = (ItemFileClickListener) mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file_manager, container, false);
    }

    public void setFileManagerFragmentListener(fileManagerFragmentListener fileManagerFragmentListener) {
        mFileManagerFragmentListener = fileManagerFragmentListener;
    }

    @Override
    public void loadingNativeBanner(MainActivity activity) {
        Log.e(TAG, "checkInternetConnected: 1");
    }

    @Override
    public void checkInternetConnected(MainActivity activity) {
        if (imvFileManagerNoInternet != null && imvFileManagerNoInternet.getVisibility() == View.VISIBLE) {
            imvFileManagerNoInternet.setVisibility(View.GONE);
        }
    }

    private void initOnStart() {
        File currLoc;
        mFileListItems.clear();
        if (mDialogProperties.offset.isDirectory() && validateOffsetPath()) {
            currLoc = new File(mDialogProperties.offset.getAbsolutePath());
            FileListItem parent = new FileListItem();
            parent.setFilename(mContext.getString(R.string.label_parent_dir));
            parent.setDirectory(true);
            parent.setLocation(currLoc.getParentFile().getAbsolutePath());
            parent.setTime(currLoc.lastModified());
            mFileListItems.add(parent);
        } else if (mDialogProperties.root.exists() && mDialogProperties.root.isDirectory()) {
            currLoc = new File(mDialogProperties.root.getAbsolutePath());
        } else {
            currLoc = new File(mDialogProperties.error_dir.getAbsolutePath());
        }
        mFileListItems = Utility.prepareFileListEntries(mFileListItems, currLoc, mExtensionFilter);
        mFileManagerAdapter = new FileManagerAdapter(mFileListItems, mContext, this);
        rcvFragmentFileManagerMain.setAdapter(mFileManagerAdapter);
    }

    private boolean validateOffsetPath() {
        String offset_path = mDialogProperties.offset.getAbsolutePath();
        String root_path = mDialogProperties.root.getAbsolutePath();
        return !offset_path.equals(root_path) && offset_path.contains(root_path);
    }

    @Override
    protected void initData() {
        initOnStart();
    }

    @Override
    protected void initView(View view) {
        flipFileManagerFlipView = view.findViewById(R.id.flip_file_manager__flipView);
        imvFileManagerNoInternet = view.findViewById(R.id.imv_file_manager__no_internet);
        rllFileManagerBanner = view.findViewById(R.id.rll_file_manager_banner);
        rcvFragmentFileManagerMain = view.findViewById(R.id.rcv_fragment_file_manager_main);

        rcvFragmentFileManagerMain.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        mFileListItems = new ArrayList<>();
        mDialogProperties = new DialogProperties();
        mExtensionFilter = new ExtensionFilter(mDialogProperties);
    }

    public void scrollToPage(final int i) {
    }

    @Override
    public void onClickItem(FileListItem fileListItem) {
        if (fileListItem.isDirectory()) {
            if (new File(fileListItem.getLocation()).canRead()) {
                File currLoc = new File(fileListItem.getLocation());
                mFileListItems.clear();
                Log.d(TAG, "onClickItem: 1");
                if (!currLoc.getName().equals(mDialogProperties.root.getName())) {
                    FileListItem parent = new FileListItem();
                    parent.setFilename(mContext.getString(R.string.label_parent_dir));
                    parent.setDirectory(true);
                    parent.setLocation(currLoc.getParentFile().getAbsolutePath());
                    parent.setTime(currLoc.lastModified());
                    mFileListItems.add(parent);
                    Log.d(TAG, "onClickItem: 2");
                }
                mFileListItems = Utility.prepareFileListEntries(mFileListItems, currLoc, mExtensionFilter);
                mFileManagerAdapter.updateData(mFileListItems);

            } else {
                Toast.makeText(mContext, R.string.error_dir_access, Toast.LENGTH_SHORT).show();
            }

        } else {
            try {
                File file = new File(fileListItem.getLocation());
                FileUtility.openFile(getActivity(), file, 0);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(mContext, "Cant Find Your File", Toast.LENGTH_LONG).show();
            }
        }

        if (mFileManagerFragmentListener != null) {
            mFileManagerFragmentListener.onBackFile(fileListItem);
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public void onClickItemBack() {

    }

    @Override
    public void updateAds(boolean isNoAds) {
        if (isNoAds) {
            if (rllFileManagerBanner.getVisibility() == View.VISIBLE) {
                rllFileManagerBanner.setVisibility(View.GONE);
            }
        } else {
            if (rllFileManagerBanner.getVisibility() == View.GONE) {
                rllFileManagerBanner.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface fileManagerFragmentListener {
        void onBackFile(FileListItem fileListItem);
    }
}
