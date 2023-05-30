package com.all.officereader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.all.officereader.R;
import com.all.officereader.helpers.utils.FontItem;
import com.artifex.sonui.editor.SOTextView;

import java.util.ArrayList;
import java.util.List;

public class FontAdapter extends BaseAdapter {

    private final Context context;
    private List<FontItem> listFont = new ArrayList<>();

    public FontAdapter(Context context, List<FontItem> list) {
        this.context = context;
        this.listFont = list;
    }

    @Override
    public int getCount() {
        return listFont.size();
    }

    @Override
    public Object getItem(int position) {
        return listFont.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View var5 = convertView;
        View var5 = LayoutInflater.from(this.context).inflate(R.layout.sodk_editor_menu_popup_font_item, parent, false);
        if (listFont.get(position).isSelected()) {
            var5.findViewById(R.id.border).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D0E9DD")));
        }
        ((SOTextView) var5.findViewById(R.id.text1)).setText(listFont.get(position).getText());
        return var5;
    }


}
