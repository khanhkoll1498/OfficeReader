package com.artifex.sonui.editor.docx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.artifex.solib.k;
import com.artifex.sonui.editor.NUIDocView;
import com.artifex.sonui.editor.NUIDocViewDoc;
import com.artifex.sonui.editor.NUIDocViewMuPdf;
import com.artifex.sonui.editor.NUIDocViewOther;
import com.artifex.sonui.editor.NUIDocViewPdf;
import com.artifex.sonui.editor.NUIDocViewPpt;
import com.artifex.sonui.editor.NUIDocViewXls;
import com.artifex.sonui.editor.NUIView;
import com.artifex.sonui.editor.SOFileState;
import com.artifex.sonui.interfaces.SaveAndAdsListener;

public class NUIView2 extends NUIView {

    private NUIDocView a;
    private SaveAndAdsListener listener;
    private String authority;
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setListener(SaveAndAdsListener listener) {
        this.listener = listener;
    }

    public NUIDocView getNUIDocView() {
        return a;
    }

    private OnDoneListener b = null;

    public NUIView2(Context var1) {
        super(var1);
    }

    public NUIView2(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public NUIView2(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }


    private void a(Context var1) {
    }

    private void a(Uri var1, String var2) {
        String var3 = com.artifex.solib.a.a(this.getContext(), var1, var2);
        StringBuilder var4 = new StringBuilder();
        var4.append("SomeFileName.");
        var4.append(var3);
        this.a(var4.toString());
    }

    private void a(String var1) {
        Object var2;
        if (com.artifex.solib.a.c(var1, "pdf")) {
            var2 = new NUIDocViewPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "svg")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "epub")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "xps")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "fb2")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "xhtml")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (com.artifex.solib.a.c(var1, "cbz")) {
            var2 = new NUIDocViewMuPdf(this.getContext());
        } else if (k.c((Activity)this.getContext(), var1)) {
            var2 = new NUIDocViewXls(this.getContext());
        } else if (k.d((Activity)this.getContext(), var1)) {
            var2 = new NUIDocViewPpt(this.getContext());
        } else if (k.e((Activity)this.getContext(), var1)) {
            var2 = new NUIDocViewPdf(this.getContext());
        } else if (k.f((Activity)this.getContext(), var1)) {
            var2 = new NUIDocViewDoc(this.getContext());
        } else {
            var2 = new NUIDocViewOther(this.getContext());
        }

        this.a = (NUIDocView)var2;
        this.a.setSaveAndAdsListener(listener);
        this.a.setAuthority(authority);
        this.a.setFilePath(filePath);
    }

    public boolean doKeyDown(int var1, KeyEvent var2) {
        NUIDocView var3 = this.a;
        return var3 != null ? var3.doKeyDown(var1, var2) : false;
    }

    public void endDocSession(boolean var1) {
        NUIDocView var2 = this.a;
        if (var2 != null) {
            var2.endDocSession(var1);
        }

    }

    public boolean isDocModified() {
        NUIDocView var1 = this.a;
        return var1 != null ? var1.documentHasBeenModified() : false;
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        NUIDocView var4 = this.a;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        }

    }

    public void onBackPressed() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.onBackPressed();
        }

    }

    public void onConfigurationChange(Configuration var1) {
        this.a.onConfigurationChange(var1);
    }

    public void onDestroy() {
        this.a.onDestroy();
    }

    public void onPause() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.onPause();
        }

        Utilities2.hideKeyboard(this.getContext());
    }

    public void onResume() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.onResume();
        }

    }

    public void releaseBitmaps() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.releaseBitmaps();
        }

    }

    public void setConfigurableButtons() {
        NUIDocView var1 = this.a;
        if (var1 != null) {
            var1.setConfigurableButtons();
        }

    }

    public void setOnDoneListener(OnDoneListener var1) {
        this.b = var1;
    }

    public void start(Uri var1, boolean var2, int var3, String var4, String var5) {
        this.a(var1, var5);
        this.addView(this.a);
        this.a.start(var1, var2, var3, var4, this.b);
    }

    public void start(SODocSession2 var1, int var2, String var3) {
        this.a(var1.getUserPath());
        this.addView(this.a);
        this.a.start(var1, var2, var3, this.b);
    }

    public void start(SOFileState var1, int var2) {
        this.a(var1.getOpenedPath());
        this.addView(this.a);
        this.a.start(var1, var2, this.b);
    }

    public interface OnDoneListener extends NUIView.OnDoneListener {
        void done();
    }
}
