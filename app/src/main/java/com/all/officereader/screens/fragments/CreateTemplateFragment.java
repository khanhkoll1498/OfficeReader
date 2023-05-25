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

import static com.artifex.solib.a.a;


public class CreateTemplateFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = CreateTemplateFragment.class.getSimpleName();

    private RecyclerView rcvFragmentTemplateList;
    private CreateFileAdapter mCreateFileAdapter;
    public ArrayList<FileAssetModel> mFileArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_create, container, false);
    }

    @Override
    protected void initData() {
        mFileArrayList = new ArrayList<>();
        mFileArrayList.add(new FileAssetModel("template-docx-a.docx", "template-docx-a.png", "Resume.docx"));
        mFileArrayList.add(new FileAssetModel("template-docx-b.docx", "template-docx-b.png", "Report II.docx"));
        mFileArrayList.add(new FileAssetModel("template-pptx-a.pptx", "template-pptx-a.png", "Dark II.pptx"));
        mFileArrayList.add(new FileAssetModel("template-pptx-b.pptx", "template-pptx-b.png", "Light II.pptx"));
        mFileArrayList.add(new FileAssetModel("template-xlsx-a.xlsx", "template-xlsx-a.png", "Expenses II.xlsx"));
        mFileArrayList.add(new FileAssetModel("template-xlsx-b.xlsx", "template-xlsx-b.png", "Invoice.xlsx"));
        mFileArrayList.add(new FileAssetModel("document-letter.docx", "document-letter.png", "Letter.docx"));
        mFileArrayList.add(new FileAssetModel("document-report.docx", "document-report.png", "Report I.docx"));
        mFileArrayList.add(new FileAssetModel("presentation-dark-amber.pptx", "presentation-dark-amber.png", "Dark I.pptx"));
        mFileArrayList.add(new FileAssetModel("presentation-light-bubbles.pptx", "presentation-light-bubbles.png", "Light I.pptx"));
        mFileArrayList.add(new FileAssetModel("spreadsheet-chart-data.xlsx", "spreadsheet-chart-data.png", "Chart.xlsx"));
        mFileArrayList.add(new FileAssetModel("spreadsheet-expense-budget.xlsx", "spreadsheet-expense-budget.png", "Expenses I.xlsx"));
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

        rcvFragmentTemplateList.setAdapter(mCreateFileAdapter);

    }

    @Override
    protected void initView(View view) {

        rcvFragmentTemplateList = view.findViewById( R.id.rcv_fragment_template__list );

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2,LinearLayoutManager.VERTICAL,false);
        rcvFragmentTemplateList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
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

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
