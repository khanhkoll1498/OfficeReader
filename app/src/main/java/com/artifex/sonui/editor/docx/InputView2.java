//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui.editor.docx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.artifex.solib.SODoc;
import com.artifex.sonui.editor.InputView;
import com.artifex.sonui.editor.NUIDocView;

public class InputView2 extends InputView {
    private InputView2.a a;
    private SODoc b;
    private NUIDocView c;

    @SuppressLint("WrongConstant")
    public InputView2(Context var1, SODoc var2, NUIDocView var3) {
        super(var1, var2, var3);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.b = var2;
        this.c = var3;
        this.a = new a(this, true, (InputMethodManager)var1.getSystemService("input_method"));
    }
    public InputConnection onCreateInputConnection(EditorInfo var1) {
        Configuration var2 = this.getResources().getConfiguration();
        var1.imeOptions = 0;
        if (var2.keyboard != 3) {
            var1.imeOptions |= 268435456;
        }

        var1.inputType = 1;
        var1.initialCapsMode = this.a.getCursorCapsMode(var1.inputType);
        var1.privateImeOptions = null;
        var1.initialSelStart = Selection.getSelectionStart(this.a.a);
        var1.initialSelEnd = Selection.getSelectionEnd(this.a.a);
        var1.actionLabel = null;
        var1.actionId = 0;
        var1.extras = null;
        var1.hintText = null;
        return this.a;
    }

    public void resetEditable() {
        this.a.a();
    }

    public void setFocus() {
        this.requestFocus();
    }

    private class a extends BaseInputConnection {
        public SpannableStringBuilder a;
        private ExtractedTextRequest c = null;
        private InputMethodManager d;
        private View e;
        private String f = "";
        private int g = 0;
        private int h = 0;
        private String i = "";
        private int j = 0;

        public a(View var2, boolean var3, InputMethodManager var4) {
            super(var2, var3);
            SpannableStringBuilder var5 = new SpannableStringBuilder();
            this.a = var5;
            var5.clear();
            this.a.clearSpans();
            Selection.setSelection(this.a, 0);
            this.g = 0;
            this.h = 0;
            this.j = 0;
            this.i = "";
            this.d = var4;
            this.e = var2;
        }

        private void a(CharSequence var1, String var2) {
            InputView2.this.c.onTyping();
            InputView2.this.c.setIsComposing(false);
            StringBuilder var3 = new StringBuilder();
            var3.append(var1.toString());
            var3.append(this.i);
            var3.append(var2);
            String var5 = var3.toString();
            InputView2.this.b.setSelectionText(var5, this.g, true);
            int var4 = this.h + var5.length() + this.g;
            this.h = var4;
            this.j = var4;
            this.g = 0;
            this.i = "";
            this.d();
            this.b();
        }

        private void b() {
            this.f = "";
            InputView2.this.c.setIsComposing(false);
        }

        private void c() {
            this.a.clear();
            this.a.clearSpans();
            this.i = "";
            this.h = 0;
            this.g = 0;
            this.j = 0;
        }

        private void d() {
            ExtractedTextRequest var1 = this.c;
            if (var1 != null) {
                ExtractedText var2 = new ExtractedText();
                SpannableStringBuilder var3 = this.a;
                if (var3 != null) {
                    int var4 = var3.length();
                    var2.partialEndOffset = -1;
                    var2.partialStartOffset = -1;
                    Object var5;
                    if ((var1.flags & 1) != 0) {
                        var5 = this.a.subSequence(0, var4);
                    } else {
                        var5 = TextUtils.substring(this.a, 0, var4);
                    }

                    var2.text = (CharSequence)var5;
                    var2.flags = 0;
                    var2.startOffset = 0;
                    var2.selectionStart = Selection.getSelectionStart(this.a);
                    var2.selectionEnd = Selection.getSelectionEnd(this.a);
                } else {
                    var2.flags = 0;
                    var2.startOffset = 0;
                    var2.selectionStart = 0;
                    var2.selectionEnd = 0;
                }

                this.d.updateExtractedText(this.e, var1.token, var2);
            }

        }

        public void a() {
            this.b();
            this.c();
            this.d.restartInput(this.e);
        }

        public void closeConnection() {
            super.closeConnection();
            this.b();
        }

        public boolean commitText(CharSequence var1, int var2) {
            this.a(var1, "");
            return super.commitText(var1, var2);
        }

        public boolean deleteSurroundingText(int var1, int var2) {
            InputView2.this.c.onTyping();
            int var3 = Selection.getSelectionStart(this.a);
            int var4 = Selection.getSelectionEnd(this.a);
            if (var3 != -1 && var4 != -1) {
                int var5 = var3;
                if (var3 > var4) {
                    var5 = var4;
                }

                var5 = Math.min(var1, var5);
                var1 = Math.min(var2, this.a.length() - var4);

                boolean var6;
                try {
                    var6 = super.deleteSurroundingText(var5, var1);
                } catch (ArrayIndexOutOfBoundsException var8) {
                    this.a();
                    return true;
                }

                if (var1 == 0) {
                    for(var1 = 0; var1 < var5; ++var1) {
                        --this.h;
                        --this.j;
                        InputView2.this.b.N();
                    }
                }

                this.d();
                return var6;
            } else {
                if (VERSION.SDK_INT < 26) {
                    InputView2.this.b.deleteChar();
                }

                return true;
            }
        }

        public boolean finishComposingText() {
            boolean var1 = super.finishComposingText();
            StringBuilder var2 = new StringBuilder();
            var2.append(this.f);
            var2.append(this.i);
            String var5 = var2.toString();
            InputView2.this.c.setIsComposing(false);
            int var3 = var5.length();
            boolean var4 = true;
            if (var3 > 0) {
                InputView2.this.b.setSelectionText(var5, 0, true);
                this.h += var5.length();
                this.i = "";
            } else {
                var4 = false;
            }

            this.d();
            this.g = 0;
            this.b();
            if (var4) {
                InputView2.this.c.onTyping();
            }

            return var1;
        }

        public Editable getEditable() {
            return this.a;
        }

        public ExtractedText getExtractedText(ExtractedTextRequest var1, int var2) {
            ExtractedText var3 = new ExtractedText();
            this.c = var1;
            SpannableStringBuilder var4 = this.a;
            if (var4 != null) {
                var2 = var4.length();
                var3.partialEndOffset = -1;
                var3.partialStartOffset = -1;
                Object var5;
                if ((var1.flags & 1) != 0) {
                    var5 = this.a.subSequence(0, var2);
                } else {
                    var5 = TextUtils.substring(this.a, 0, var2);
                }

                var3.text = (CharSequence)var5;
                var3.flags = 0;
                var3.startOffset = 0;
                var3.selectionStart = Selection.getSelectionStart(this.a);
                var3.selectionEnd = Selection.getSelectionEnd(this.a);
                return var3;
            } else {
                return null;
            }
        }

        public boolean sendKeyEvent(KeyEvent var1) {
            int var2 = var1.getKeyCode();
            if (var1.getAction() == 1) {
                switch(var2) {
                case 19:
                case 20:
                case 21:
                case 22:
                    InputView2.this.c.onTyping();
                    this.b();
                    this.c();
                    break;
                default:
                    switch(var2) {
                    case 66:
                        this.a(this.f, "\n");
                        this.a();
                        break;
                    case 67:
                        var2 = this.h;
                        if (var2 > 0) {
                            this.h = var2 - 1;
                            --this.j;
                        }
                    }
                }
            }

            return super.sendKeyEvent(var1);
        }

        public boolean setComposingRegion(int var1, int var2) {
            if (var1 >= this.a.length() && var2 >= this.a.length()) {
                this.g = this.a.length() - this.h;
            } else {
                int var3 = Math.min(Math.max(var1, 0), this.a.length() - 1);
                int var4 = Math.min(Math.max(var2, 0), this.a.length() - 1);
                this.g = Math.min(var3, var4) - this.h;
                var1 = Math.min(this.j, this.a.length());
                if (var1 != this.j) {
                    this.j = var1;
                }

                this.i = "";
                var1 = var3;
                var2 = var4;
                if (Math.max(var3, var4) < this.j - 1) {
                    this.i = this.a.subSequence(Math.max(var3, var4), this.j).toString();
                    var2 = var4;
                    var1 = var3;
                }
            }

            return super.setComposingRegion(var1, var2);
        }

        public boolean setComposingText(CharSequence var1, int var2) {
            boolean var3 = super.setComposingText(var1, var2);
            InputView2.this.c.onTyping();
            InputView2.this.c.setIsComposing(true);
            this.f = var1.toString();
            SODoc var4 = InputView2.this.b;
            StringBuilder var5 = new StringBuilder();
            var5.append(this.f);
            var5.append(this.i);
            var4.setSelectionText(var5.toString(), this.g, false);
            this.h += this.g;
            this.g = 0;
            this.j = this.f.length() + this.i.length() + this.h;
            this.d();
            return var3;
        }
    }
}
