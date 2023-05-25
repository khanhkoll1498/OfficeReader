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
import com.all.officereader.screens.ocr.model.PictureFacer;

import java.util.ArrayList;

public class GalleryFragments extends Fragment implements GalleryAdapter.onClickItem {

    private RecyclerView rvListGallery;
    private ArrayList<PictureFacer> imageFolders;
    private GalleryAdapter galleryAdapter;
    private ArrayList<String> selectedImageList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_image, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        rvListGallery = view.findViewById(R.id.rv_image);

        selectedImageList.clear();
        imageFolders = QueryAllStorage.getAllImageGallery(getActivity());
        galleryAdapter = new GalleryAdapter(imageFolders, getActivity(), GalleryActivity.savedImgList);
        galleryAdapter.setOnClickItem(this);
        rvListGallery.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvListGallery.setAdapter(galleryAdapter);
        galleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(int position) {
        if (!imageFolders.get(position).isSelected()) {
            selectImage(position);
        } else {
            unSelectImage(position);
        }
        GalleryActivity.countImage(GalleryActivity.savedImgList);
    }

    public void selectImage(int position) {
        if (!selectedImageList.contains(imageFolders.get(position).getPicturePath())) {
            imageFolders.get(position).setSelected(true);
            selectedImageList.add(imageFolders.get(position).getPicturePath());
            GalleryActivity.savedImgList.add(imageFolders.get(position).getPicturePath());
            galleryAdapter.notifyDataSetChanged();
        }
    }

    public void unSelectImage(int position) {
        for (int i = 0; i < selectedImageList.size(); i++) {
            if (imageFolders.get(position).getPicturePath() != null) {
                if (selectedImageList.get(i).equals(imageFolders.get(position).getPicturePath())) {
                    imageFolders.get(position).setSelected(false);
                    selectedImageList.remove(i);
                    GalleryActivity.savedImgList.remove(imageFolders.get(position).getPicturePath());
                    galleryAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
