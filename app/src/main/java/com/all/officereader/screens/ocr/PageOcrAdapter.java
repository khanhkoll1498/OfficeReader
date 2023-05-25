package com.all.officereader.screens.ocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.isseiaoki.simplecropview.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PageOcrAdapter extends FragmentStateAdapter {

    private ArrayList<PreviewFragment> mListFragments;
    private Bitmap mBitmap;
    public FrameLayout flItem;
    private ArrayList<String> data;
    private Context mContext;
    private onCLickItem onCLickItem;

    public PageOcrAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<PreviewFragment> listFragments) {
        super(fragmentActivity);
        this.mListFragments = listFragments;
    }

    public void setOnCLickItem(PageOcrAdapter.onCLickItem onCLickItem) {
        this.onCLickItem = onCLickItem;
    }

    public final void addFragment(PreviewFragment fragment) {
        this.mListFragments.add(fragment);
    }

    public void updateBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void updateImage(ArrayList<String> listUpdate) {
        data = listUpdate;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public PreviewFragment createFragment(int position) {
        return mListFragments.get(position);
    }

    public final PreviewFragment getChildAt(int i) {
        return this.mListFragments.get(i);
    }

    @Override
    public int getItemCount() {
        return mListFragments.size();
    }

    public interface onCLickItem {
        void onCLick(CropImageView view, int position);
    }
}
