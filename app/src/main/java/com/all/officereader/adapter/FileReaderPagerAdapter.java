package com.all.officereader.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.all.officereader.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FileReaderPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragmentArrayList;
    private String[] mFragmentTitles;

    public FileReaderPagerAdapter(Context context, FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.mFragmentArrayList = fragmentArrayList;
        mFragmentTitles = new String[]{
                context.getString(R.string.file_reader_tab),
                context.getString(R.string.recent_tab),
                context.getString(R.string.book_mark_tab),
                context.getString(R.string.file_manager_tab),
                context.getString(R.string.gift_tab)};
    }

    public ArrayList<Fragment> getFragmentArrayList() {
        return mFragmentArrayList;
    }

    @Override
    public @NotNull Fragment getItem(int position) {
        return mFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles[position];
    }
}
