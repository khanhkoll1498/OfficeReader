package com.artifex.sonui.editor;

import android.content.Context;
import android.supportv1.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.sonui.editor.R.color;
import com.artifex.sonui.editor.R.drawable;
import com.artifex.sonui.editor.R.id;
import com.artifex.sonui.editor.R.layout;
import com.artifex.sonui.editor.R.string;
import java.util.ArrayList;

public class NUIDocViewDoc extends NUIDocView {
    private ToolbarButton b;
    private ToolbarButton c;
    private ToolbarButton d;
    private ToolbarButton e;
    private ToolbarButton f;
    private ToolbarButton g;
    private ToolbarButton h;
    private ToolbarButton i;
    private ToolbarButton j;
    private TabData[] D = null;

    public NUIDocViewDoc(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIDocViewDoc(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIDocViewDoc(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private void a(Context var1) {
    }

    private void a(boolean var1) {
        DocView var2 = this.getDocView();
        SODoc var3 = var2.getDoc();
        var2.preNextPrevTrackedChange();
        SOSelectionLimits var4 = var2.getSelectionLimits();
        boolean var5;
        if (var4 != null) {
            var5 = var4.getIsActive();
        } else {
            var5 = false;
        }

        if (var1 != var5) {
            if (var1) {
                var2.selectTopLeft();
            } else {
                var3.clearSelection();
            }
        }

        if (var3.nextTrackedChange()) {
            var2.onNextPrevTrackedChange();
        } else {
            if (!var5) {
                var3.clearSelection();
            }

            Utilities.yesNoMessage(this.activity(), this.activity().getString(string.sodk_editor_no_more_found), this.activity().getString(string.sodk_editor_keep_searching), this.activity().getString(string.sodk_editor_str_continue), this.activity().getString(string.sodk_editor_stop), new Runnable() {
                public void run() {
                    NUIDocViewDoc.this.a(false);
                }
            }, (Runnable)null);
        }

    }

    private void b(boolean var1) {
        DocView var2 = this.getDocView();
        SODoc var3 = var2.getDoc();
        var2.preNextPrevTrackedChange();
        SOSelectionLimits var4 = var2.getSelectionLimits();
        boolean var5;
        if (var4 != null) {
            var5 = var4.getIsActive();
        } else {
            var5 = false;
        }

        if (var1 != var5) {
            if (var1) {
                var2.selectTopLeft();
            } else {
                var3.clearSelection();
            }
        }

        if (var3.previousTrackedChange()) {
            var2.onNextPrevTrackedChange();
        } else {
            if (!var5) {
                var3.clearSelection();
            }

            Utilities.yesNoMessage(this.activity(), this.activity().getString(string.sodk_editor_no_more_found), this.activity().getString(string.sodk_editor_keep_searching), this.activity().getString(string.sodk_editor_str_continue), this.activity().getString(string.sodk_editor_stop), new Runnable() {
                public void run() {
                    NUIDocViewDoc.this.b(false);
                }
            }, (Runnable)null);
        }

    }

//    public NUIDocView.TabData[] getTabData() {
//        if (this.D == null) {
//            this.D = new NUIDocView.TabData[5];
//            if (this.mConfigOptions.c()) {
//                NUIDocView.TabData[] tabDataArr = this.D;
//                NUIDocView.TabData tabData = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_left, 0);
//                tabDataArr[0] = tabData;
//                NUIDocView.TabData[] tabDataArr2 = this.D;
//                NUIDocView.TabData tabData2 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_edit), R.id.editTab, R.layout.sodk_editor_tab, 0);
//                tabDataArr2[1] = tabData2;
//                NUIDocView.TabData[] tabDataArr3 = this.D;
//                NUIDocView.TabData tabData3 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_insert), R.id.insertTab, R.layout.sodk_editor_tab, 0);
//                tabDataArr3[2] = tabData3;
//                NUIDocView.TabData[] tabDataArr4 = this.D;
//                NUIDocView.TabData tabData4 = new NUIDocView.TabData(getContext().getString(string.sodk_editor_tab_pages), id.pagesTab, R.layout.sodk_editor_tab, 0);
//                tabDataArr4[3] = tabData4;
//                NUIDocView.TabData[] tabDataArr5 = this.D;
//                NUIDocView.TabData tabData5 = new NUIDocView.TabData(getContext().getString(string.sodk_editor_tab_review), id.reviewTab, R.layout.sodk_editor_tab_right, 0);
//                tabDataArr5[4] = tabData5;
//            } else {
//                NUIDocView.TabData[] tabDataArr6 = this.D;
//                NUIDocView.TabData tabData6 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_one, 0);
//                tabDataArr6[0] = tabData6;
//                NUIDocView.TabData[] tabDataArr7 = this.D;
//                NUIDocView.TabData tabData7 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_edit), R.id.editTab, R.layout.sodk_editor_tab, 8);
//                tabDataArr7[1] = tabData7;
//                NUIDocView.TabData[] tabDataArr8 = this.D;
//                NUIDocView.TabData tabData8 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_insert), R.id.insertTab, R.layout.sodk_editor_tab, 8);
//                tabDataArr8[2] = tabData8;
//                NUIDocView.TabData[] tabDataArr9 = this.D;
//                NUIDocView.TabData tabData9 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_pages), R.id.pagesTab, R.layout.sodk_editor_tab, 8);
//                tabDataArr9[3] = tabData9;
//                NUIDocView.TabData[] tabDataArr10 = this.D;
//                NUIDocView.TabData tabData10 = new NUIDocView.TabData(getContext().getString(R.string.sodk_editor_tab_review), R.id.reviewTab, R.layout.sodk_editor_tab_right, 8);
//                tabDataArr10[4] = tabData10;
//            }
//        }
//        return this.D;
//    }

    protected void createReviewButtons() {
        if (this.mConfigOptions.c()) {
            this.b = (ToolbarButton)this.createToolbarButton(id.show_changes_button);
            this.c = (ToolbarButton)this.createToolbarButton(id.track_changes_button);
            this.d = (ToolbarButton)this.createToolbarButton(id.comment_button);
            this.e = (ToolbarButton)this.createToolbarButton(id.delete_comment_button);
            this.f = (ToolbarButton)this.createToolbarButton(id.accept_button);
            this.g = (ToolbarButton)this.createToolbarButton(id.reject_button);
            this.h = (ToolbarButton)this.createToolbarButton(id.next_button);
            this.i = (ToolbarButton)this.createToolbarButton(id.previous_button);
            this.j = (ToolbarButton)this.createToolbarButton(id.author_button);
            ArrayList var1 = new ArrayList();
            var1.add(this.b);
            var1.add(this.c);
            var1.add(this.d);
            var1.add(this.e);
            var1.add(this.f);
            var1.add(this.g);
            var1.add(this.h);
            var1.add(this.i);
            if (this.mConfigOptions.a != null && !this.mConfigOptions.w()) {
                this.mConfigOptions.a.a(this.b);
                this.mConfigOptions.a.a(this.c);
                this.mConfigOptions.a.a(this.j);
            }

            if (this.mConfigOptions.t()) {
                var1.add(this.j);
            } else {
                this.findViewById(id.author_button_divider).setVisibility(GONE);
                this.j.setVisibility(GONE);
            }

            ToolbarButton.setAllSameSize((ToolbarButton[])var1.toArray(new ToolbarButton[var1.size()]));
        }

        findViewById(com.all.officereader.R.id.bt_tool).setOnClickListener(this::onTapTool);
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), color.sodk_editor_header_doc_color);
    }

    protected int getLayoutId() {
        return layout.sodk_editor_document;
    }

    public void onAcceptButton(View var1) {
        this.getDocView().getDoc().acceptTrackedChange();
    }

    public void onClick(View var1) {
        super.onClick(var1);
        if (var1 == this.b) {
            this.onShowChangesButton(var1);
        }

        if (var1 == this.c) {
            this.onTrackChangesButton(var1);
        }

        if (var1 == this.d) {
            this.onCommentButton(var1);
        }

        if (var1 == this.e) {
            this.onDeleteCommentButton(var1);
        }

        if (var1 == this.f) {
            this.onAcceptButton(var1);
        }

        if (var1 == this.g) {
            this.onRejectButton(var1);
        }

        if (var1 == this.h) {
            this.onNextButton(var1);
        }

        if (var1 == this.i) {
            this.onPreviousButton(var1);
        }

        if (var1 == this.j) {
            this.onAuthorButton(var1);
        }

    }

    public void onCommentButton(View var1) {
        this.getDocView().addComment();
    }

    public void onDeleteCommentButton(View var1) {
        this.getDocView().getDoc().deleteHighlightAnnotation();
    }

    public void onNextButton(View var1) {
        this.a(true);
    }

    public void onPreviousButton(View var1) {
        this.b(true);
    }

    public void onRejectButton(View var1) {
        this.getDocView().getDoc().rejectTrackedChange();
    }

    public void onShowChangesButton(View var1) {
        SODoc var2 = this.getDocView().getDoc();
        var2.setShowingTrackedChanges(var2.getShowingTrackedChanges() ^ true);
        this.onSelectionChanged();
    }

    public void onTrackChangesButton(View var1) {
        SODoc var2 = this.getDocView().getDoc();
        var2.setTrackingChanges(var2.getTrackingChanges() ^ true);
        this.onSelectionChanged();
    }

    protected void prepareToGoBack() {
        DocView var1 = this.getDocView();
        if (var1 != null) {
            var1.preNextPrevTrackedChange();
        }

    }

    protected void updateReviewUIAppearance() {
        SODoc var1 = this.getDocView().getDoc();
        boolean var2 = var1.getShowingTrackedChanges();
        boolean var3 = var1.getTrackingChanges();
        ToolbarButton var4 = this.b;
        int var5;
        if (var2) {
            var5 = drawable.sodk_editor_icon_toggle_on;
        } else {
            var5 = drawable.sodk_editor_icon_toggle_off;
        }

        var4.setImageResource(var5);
        var4 = this.c;
        if (var3) {
            var5 = drawable.sodk_editor_icon_toggle_on;
        } else {
            var5 = drawable.sodk_editor_icon_toggle_off;
        }

        var4.setImageResource(var5);
        SOSelectionLimits var9 = this.getDocView().getSelectionLimits();
        boolean var6 = false;
        boolean var7;
        if (var9 != null) {
            var7 = var9.getIsActive();
        } else {
            var7 = false;
        }

        boolean var10;
        if (var7 && var1.getSelectionHasAssociatedPopup()) {
            var10 = true;
        } else {
            var10 = false;
        }

        if (var7 && !var10 && var2) {
            var7 = true;
        } else {
            var7 = false;
        }

        if (var10) {
            this.e.setVisibility(VISIBLE);
            this.d.setVisibility(GONE);
            this.e.setEnabled(true);
        } else {
            this.e.setVisibility(GONE);
            this.d.setVisibility(VISIBLE);
            this.d.setEnabled(var7);
        }

        this.i.setEnabled(var2);
        this.h.setEnabled(var2);
        boolean var8 = var1.selectionIsReviewable();
        var7 = var6;
        if (var2) {
            var7 = var6;
            if (var3) {
                var7 = var6;
                if (var8) {
                    var7 = true;
                }
            }
        }

        this.f.setEnabled(var7);
        this.g.setEnabled(var7);
    }
}
