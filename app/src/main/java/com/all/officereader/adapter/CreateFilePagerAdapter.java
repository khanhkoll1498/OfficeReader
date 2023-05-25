package com.all.officereader.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.all.officereader.R;
import com.all.officereader.screens.fragments.CreateNewFileFragment;
import com.all.officereader.screens.fragments.CreateTemplateFragment;

import java.util.ArrayList;

public class CreateFilePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();
    private String[] mFragmentTitles;

    public CreateFilePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mFragmentArrayList.add(new CreateNewFileFragment());
        mFragmentArrayList.add(new CreateTemplateFragment());
        mFragmentTitles = new String[]{
                context.getString(R.string.file_reader_tab),
                context.getString(R.string.recent_tab)};
    }

    public ArrayList<Fragment> getFragmentArrayList() {
        return mFragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {

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
