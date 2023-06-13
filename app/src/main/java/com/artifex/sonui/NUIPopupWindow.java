//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;

public class NUIPopupWindow extends PopupWindow {
    public NUIPopupWindow(Context var1) {
        super(var1);
        this.a();
    }

    public NUIPopupWindow(View var1) {
        super(var1);
        this.a();
    }

    public NUIPopupWindow(View var1, int var2, int var3) {
        super(var1, var2, var3);
        this.a();
    }

    private void a() {
        this.setBackgroundDrawable(new ColorDrawable());
        this.setOutsideTouchable(true);
    }
}
