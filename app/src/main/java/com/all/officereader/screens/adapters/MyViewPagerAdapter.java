package com.all.officereader.screens.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.all.officereader.R;
import com.all.officereader.helpers.utils.SharedPreferencesUtility;
import com.all.officereader.screens.activities.IntroductionActivity;
import com.all.officereader.screens.activities.MainActivity;
import com.all.officereader.screens.ocr.general.PermissionUtils;

import org.jetbrains.annotations.NotNull;

public class MyViewPagerAdapter extends PagerAdapter {
    private LayoutInflater mLayoutInflater;
    private TextView btnIntroGet;
    private Activity mActivity;
    private int[] mLayoutList;

    public MyViewPagerAdapter(Activity activity, int[] layoutList) {
        this.mActivity = activity;
        this.mLayoutList = layoutList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(mLayoutList[position], container, false);

        if (position == 2) {
            btnIntroGet = view.findViewById(R.id.tv_submit);
            btnIntroGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PermissionUtils.isPermission(IntroductionActivity.PERMISSION_EXTERNAL, mActivity)) {
                        launchMainScreen(mActivity);
                    }
                }
            });
        }

        container.addView(view);
        return view;
    }

    public static void launchMainScreen(Activity activity) {
        SharedPreferencesUtility.setCheckPermission(activity, true);
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    public int getCount() {
        return mLayoutList.length;
    }

    @Override
    public void destroyItem(final @NotNull View container, final int position, final @NotNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }
}
