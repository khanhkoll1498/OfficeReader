//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui.editor.docx;

import com.artifex.sonui.editor.DocView;
import com.artifex.sonui.editor.DocViewHost;

public interface DocViewHost2 extends DocViewHost {
    void clickSheetButton(int var1, boolean var2);

    int getBorderColor();

    DocView getDocView();

    int getKeyboardHeight();

    void layoutNow();

    void onShowKeyboard(boolean var1);

    void prefinish();

    void selectionupdated();

    void setCurrentPage(int var1);

    boolean showKeyboard();
}
