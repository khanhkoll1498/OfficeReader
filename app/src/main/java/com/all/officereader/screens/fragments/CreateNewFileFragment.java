package com.all.officereader.screens.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artifex.sonui.editor.Utilities;
import com.all.officereader.R;
import com.all.officereader.adapter.CreateFileAdapter;
import com.all.officereader.helpers.bases.BaseFragment;
import com.all.officereader.helpers.utils.FileUtility;
import com.all.officereader.helpers.utils.ToastUtil;
import com.all.officereader.model.FileAssetModel;
import com.all.officereader.screens.activities.MainActivity;

import java.io.File;
import java.util.ArrayList;


public class CreateNewFileFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = CreateNewFileFragment.class.getSimpleName();
    private RecyclerView rcvFragmentNewFileList;
    private CreateFileAdapter mCreateFileAdapter;
    public ArrayList<FileAssetModel> mFileArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_file_create, container, false);
    }

    @Override
    protected void initData() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2,LinearLayoutManager.VERTICAL,false);
        rcvFragmentNewFileList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
    }

    @Override
    protected void initView(View view) {
        rcvFragmentNewFileList = view.findViewById( R.id.rcv_fragment_new_file__list );
        mFileArrayList = new ArrayList<>();
        mFileArrayList.add(new FileAssetModel("spreadsheet-blank.xlsx", "xlsx-2007.png", "Spreadsheet.xlsx"));
        mFileArrayList.add(new FileAssetModel("spreadsheet-office2003.xls", "xls-1997.png", "2003 spreadsheet.xls", "Spreadsheet.xlsx"));
        mFileArrayList.add(new FileAssetModel("document-blank.docx", "docx-2007.png", "Document.docx"));
        mFileArrayList.add(new FileAssetModel("document-office2003.doc", "doc-1997.png", "2003 document.doc", "Document.docx"));
        mFileArrayList.add(new FileAssetModel("presentation-blank.pptx", "pptx-2007.png", "Presentation.pptx"));
        mFileArrayList.add(new FileAssetModel("presentation-office2003.ppt", "ppt-1997.png", "2003 presentation.ppt", "Presentation.pptx"));

        mCreateFileAdapter = new CreateFileAdapter(mFileArrayList, mContext, new CreateFileAdapter.onItemClickAssetListener() {
            @Override
            public void onClickItemAsset(FileAssetModel fileAssetModel) {
                Utilities.hideKeyboard(mContext);
                String b2 = com.artifex.solib.a.b(mContext, fileAssetModel.getmPath());

                if (b2 != null) {
                    File file = new File(b2);
                    FileUtility.openFile((Activity) mContext,file,1);
                    return;
                }
                ToastUtil.showToast(mContext,"File error" +fileAssetModel.getmPath());

            }
        });

        rcvFragmentNewFileList.setAdapter(mCreateFileAdapter);
    }

    @Override
    public void loadingNativeBanner(MainActivity mainActivity) {
    }

    @Override
    public void checkInternetConnected(MainActivity activity) {

    }


    @Override
    public void onClick(View view) {
    }



    @Override
    public void updateAds(boolean isNoAds) {

    }
}
