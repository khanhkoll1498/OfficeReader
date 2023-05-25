package com.all.officereader.screens.ocr.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.all.officereader.R;
import com.all.officereader.screens.ocr.gallery.GalleryActivity;
import com.all.officereader.screens.ocr.gallery.GalleryAdapter;
import com.all.officereader.screens.ocr.QueryAllStorage;
import com.all.officereader.screens.ocr.gallery.FolderAdapter;
import com.all.officereader.screens.ocr.model.ImageFolder;
import com.all.officereader.screens.ocr.model.PictureFacer;

import java.util.ArrayList;

public class FolderFragments extends Fragment implements GalleryAdapter.onClickItem {

    private ArrayList<ImageFolder> imageFolders;
    private FolderAdapter folderAdapter;
    private RecyclerView rvListGallery;

    private ArrayList<String> selectedImageList;
    private ArrayList<PictureFacer> mListImage;
    private GalleryAdapter galleryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_image, container, false);
        rvListGallery = view.findViewById(R.id.rv_folder_image);
        initViews();
        return view;
    }

    private void initViews() {
        mListImage = new ArrayList<>();
        selectedImageList = new ArrayList<>();
        selectedImageList.clear();
        mListImage.clear();

        if (getActivity() != null) {
            imageFolders = QueryAllStorage.queryAllPicture(getActivity());
            folderAdapter = new FolderAdapter(imageFolders, getActivity());
            folderAdapter.setOnClickItem(new FolderAdapter.onClickItem() {
                @Override
                public void onClickItem(int position) {
                    mListImage = QueryAllStorage.getAllImagesByFolder(getActivity(), imageFolders.get(position).getPath());
                    galleryAdapter = new GalleryAdapter(mListImage, getActivity(), GalleryActivity.savedImgList);
                    galleryAdapter.setOnClickItem(FolderFragments.this);
                    rvListGallery.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                    rvListGallery.setAdapter(galleryAdapter);
                    galleryAdapter.notifyDataSetChanged();
                }
            });

            rvListGallery.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvListGallery.setAdapter(folderAdapter);
            folderAdapter.notifyDataSetChanged();
        }
    }

    public boolean onBackFolder() {
        if (mListImage != null && mListImage.size() > 0) {
            initViews();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClickItem(int position) {
        if (!mListImage.get(position).isSelected()) {
            selectImage(position);
        } else {
            unSelectImage(position);
        }
        GalleryActivity.countImage(GalleryActivity.savedImgList);
    }

    public void selectImage(int position) {
        if (!selectedImageList.contains(mListImage.get(position).getPicturePath())) {
            mListImage.get(position).setSelected(true);
            selectedImageList.add(mListImage.get(position).getPicturePath());
            GalleryActivity.savedImgList.add(mListImage.get(position).getPicturePath());
            galleryAdapter.notifyDataSetChanged();
        }
    }

    public void unSelectImage(int position) {
        for (int i = 0; i < selectedImageList.size(); i++) {
            if (mListImage.get(position).getPicturePath() != null) {
                if (selectedImageList.get(i).equals(mListImage.get(position).getPicturePath())) {
                    mListImage.get(position).setSelected(false);
                    selectedImageList.remove(i);
                    GalleryActivity.savedImgList.remove(mListImage.get(position).getPicturePath());
                    galleryAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
