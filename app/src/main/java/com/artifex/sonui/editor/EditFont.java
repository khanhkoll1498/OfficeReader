package com.artifex.sonui.editor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.supportv1.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;

import com.all.officereader.adapter.FontAdapter;
import com.all.officereader.helpers.utils.FontItem;
import com.all.officereader.helpers.utils.Utilities2;
import com.artifex.solib.SODoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;

public class EditFont {

    private static String[] f1446a;
    private static String[] f1447b = {"6 pt", "8 pt", "9 pt", "10 pt", "12 pt", "14 pt", "16 pt", "18 pt", "20 pt", "24 pt", "30 pt", "36 pt", "48 pt", "60 pt", "72 pt"};
    private static WheelView wheelView;
    private static WheelView wheelView1;
    private static SODoc e;
    private static final List<FontItem> list = new ArrayList<>();


    private static float a(String str) {
        return (float) Integer.parseInt(str.substring(0, str.length() - 3));
    }

    private static void a(Context context) {
        FontListAdapter fontListAdapter = new FontListAdapter(context);
        fontListAdapter.enumerateFonts(e);
        int count = fontListAdapter.getCount();
        f1446a = new String[count];
        for (int i = 0; i < count; i++) {
            f1446a[i] = fontListAdapter.getItem(i);
        }
    }

    private static void d() {
        String selectionFontName = Utilities.getSelectionFontName(e);
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = f1446a;
            if (i2 >= strArr.length) {
                break;
            } else if (strArr[i2].equalsIgnoreCase(selectionFontName)) {
                wheelView.setCurrentItem(i2);
                break;
            } else {
                i2++;
            }
        }
        double selectionFontSize = e.getSelectionFontSize();
        while (true) {
            String[] strArr2 = f1447b;
            if (i >= strArr2.length) {
                return;
            }
            if (((double) a(strArr2[i])) >= selectionFontSize) {
                wheelView1.setCurrentItem(i);
                return;
            }
            i++;
        }
    }

    public static void e() {
        e.setSelectionFontName(f1446a[wheelView.getCurrentItem()]);
    }

    public static void f() {
        e.setSelectionFontSize((double) a(f1447b[wheelView1.getCurrentItem()]));
    }

    public static void g() {
        wheelView.a();
        wheelView1.a();
        wheelView = null;
        wheelView1 = null;
        e = null;
        f1446a = null;
    }

    public static void show(Context context, View view, SODoc sODoc) {
        e = sODoc;
        a(context);
        View inflate = View.inflate(context, R.layout.sodk_editor_edit_font, null);
        wheelView = (WheelView) inflate.findViewById(R.id.left_wheel);

        c myAdapter = new c(context, f1446a);

        //cVar.b(18);
        myAdapter.a(context.getResources().getColor(R.color.sodk_editor_palette_white));
        wheelView.setViewAdapter(myAdapter);
        wheelView.setVisibleItems(5);
        wheelView1 = (WheelView) inflate.findViewById(R.id.right_wheel);

        //wheelView1.b(18);
        //wheelView1.setBackgroundColor(context.getResources().getColor(R.color.sodk_editor_palette_white));
        c cVar2 = new c(context, f1447b);
        cVar2.a(context.getResources().getColor(R.color.sodk_editor_palette_white));
        wheelView1.setViewAdapter(cVar2);
        wheelView1.setVisibleItems(5);
        d();
        wheelView.a((kankan.wheel.widget.d) new kankan.wheel.widget.d() {
            public void a(WheelView wheelView) {
            }

            public void b(WheelView wheelView) {
                EditFont.e();
            }
        });
        wheelView1.a((kankan.wheel.widget.d) new kankan.wheel.widget.d() {
            public void a(WheelView wheelView) {
            }

            public void b(WheelView wheelView) {
                EditFont.f();
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(inflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                EditFont.g();
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }

    public static void showFontSize(Context context, View view, SODoc sODoc) {
        EditFont.e = sODoc;
        a(context);
        ListPopupWindow popupWindowFontName = new ListPopupWindow(context);
        popupWindowFontName.setBackgroundDrawable(ContextCompat.getDrawable(context, com.all.officereader.R.drawable.sodk_editor_menu_popup));
        popupWindowFontName.setModal(true);
        popupWindowFontName.setAnchorView(view);
        double fontSize = sODoc.getSelectionFontSize();
        @SuppressLint("DefaultLocale") String fontCurrent = String.format("%d",(int) fontSize);
        list.clear();
        for (String var5 : f1447b) {
            list.add(new FontItem(var5, false));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.forEach(fontItem -> {
                if (Objects.equals(fontCurrent, fontItem.getText())){
                    Log.d("for_fontName", "fontCurrent: " + fontCurrent + " ,fontItem: " + fontItem.getText());
                    fontItem.setSelected(true);
                }
            });
        }
        final FontAdapter var2 = new FontAdapter(context, list);
        popupWindowFontName.setAdapter(var2);
        popupWindowFontName.setOnItemClickListener((var1, var2x, var3, var4) -> {
            updateFontSize(var3);
            popupWindowFontName.dismiss();
        });

        popupWindowFontName.setContentWidth(150);
        popupWindowFontName.setHeight(800);
        popupWindowFontName.show();
        popupWindowFontName.getListView().setVerticalScrollBarEnabled(true);
        popupWindowFontName.getListView().setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
//        EditFont.sODoc = sODoc;
//        ListPopupWindow popupWindow = new ListPopupWindow(((Activity) context));
//        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, com.all.officereader.R.drawable.sodk_editor_menu_popup));
//        popupWindow.setModal(true);
//        popupWindow.setAnchorView(view);
//
//        final ArrayAdapter var2 = new ArrayAdapter(context, com.all.officereader.R.layout.sodk_editor_menu_popup_item);
//        popupWindow.setAdapter((ListAdapter) var2);
////        NUIDocView.TabData[] var7 = this.getTabData();
//
//        for (String var5 : f1447b) {
//            var2.add(var5);
//            ((SOTextView) ((Activity) context).getLayoutInflater().inflate(com.all.officereader.R.layout.sodk_editor_menu_popup_item, (ViewGroup) null)).setText(var5.split(" pt")[0]);
//        }
//
//
//        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> var1, View var2x, int var3, long var4) {
//                updateFontSize(var3);
//                popupWindow.dismiss();
//            }
//        });
//
//        popupWindow.setContentWidth(a(context, var2));
//        popupWindow.setHeight(800);
//
//        popupWindow.show();
//        popupWindow.getListView().setVerticalScrollBarEnabled(true);
//        popupWindow.getListView().setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
    }

    public static void showFontName(Context context, View view, SODoc doc) {
        EditFont.e = doc;
        a(context);
        ListPopupWindow popupWindowFontName = new ListPopupWindow(context);
        popupWindowFontName.setBackgroundDrawable(ContextCompat.getDrawable(context, com.all.officereader.R.drawable.sodk_editor_menu_popup));
        popupWindowFontName.setModal(true);
        popupWindowFontName.setAnchorView(view);
        String fontCurrent = Utilities2.getSelectionFontName(e);
        list.clear();
        for (String var5 : e.getFontList().split(",")) {
            list.add(new FontItem(var5, false));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.forEach(fontItem -> {
                if (Objects.equals(fontCurrent, fontItem.getText())){
                    Log.d("for_fontName", "fontCurrent: " + fontCurrent + " ,fontItem: " + fontItem.getText());
                    fontItem.setSelected(true);
                }
            });
        }
        final FontAdapter var2 = new FontAdapter(context, list);
        popupWindowFontName.setAdapter(var2);
        popupWindowFontName.setOnItemClickListener((var1, var2x, var3, var4) -> {
            updateFontStyle(var3);
            popupWindowFontName.dismiss();
        });

        popupWindowFontName.setContentWidth(450);
        popupWindowFontName.setHeight(800);
        popupWindowFontName.show();
        popupWindowFontName.getListView().setVerticalScrollBarEnabled(true);
        popupWindowFontName.getListView().setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
    }

    public static void updateFontStyle(int index) {
        e.setSelectionFontName(f1446a[index]);
    }

    public static void updateFontSize(int index) {
        e.setSelectionFontSize((double) a(f1447b[index]));
    }

}
