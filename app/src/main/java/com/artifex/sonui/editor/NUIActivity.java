package com.artifex.sonui.editor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import com.artifex.solib.ConfigOptions;
import com.artifex.sonui.editor.NUIView.OnDoneListener;
import com.artifex.sonui.editor.R.string;
import com.artifex.sonui.editor.SODocSession.SODocSessionLoadListenerCustom;

public class NUIActivity extends BaseActivity {
    private static SODocSession a;
    private Intent b = null;
    private long c = 0L;
    private int d = -1;
    protected NUIView mNUIView;

    public NUIActivity() {
    }

    private void a() {
        Intent var1 = this.getIntent();
        Bundle var2 = var1.getExtras();
        boolean var3;
        if (var2 != null) {
            var3 = var2.getBoolean("SESSION", false);
            this.a(var1);
        } else {
            var3 = false;
        }

        if (var3 && a == null) {
            super.finish();
        } else {
            this.a(var1, false);
        }
    }

    private void a(Intent var1) {
        ConfigOptions var2 = ConfigOptions.a();
        Bundle var3 = var1.getExtras();
        if (var1.hasExtra("ENABLE_SAVE")) {
            var2.n(var3.getBoolean("ENABLE_SAVE", true));
        }

        if (var1.hasExtra("ENABLE_SAVEAS")) {
            var2.b(var3.getBoolean("ENABLE_SAVEAS", true));
        }

        if (var1.hasExtra("ENABLE_SAVEAS_PDF")) {
            var2.c(var3.getBoolean("ENABLE_SAVEAS_PDF", true));
        }

        if (var1.hasExtra("ENABLE_CUSTOM_SAVE")) {
            var2.o(var3.getBoolean("ENABLE_CUSTOM_SAVE", true));
        }

        if (var1.hasExtra("ALLOW_AUTO_OPEN")) {
            var2.q(var3.getBoolean("ALLOW_AUTO_OPEN", true));
        }
    }

    private void a(Intent intent, boolean z) {
        SOFileState fromString;
        String string;
        String str;
        boolean z2;
        int i;
        NUIActivity nUIActivity = this;
        Bundle extras = intent.getExtras();
        boolean z3 = false;
        boolean z4 = extras != null ? extras.getBoolean("SESSION", false) : false;
        SODocSession sODocSession = a;
        setContentView(R.layout.sodk_editor_doc_view_activity);
        NUIView nUIView = (NUIView) findViewById(R.id.doc_view);
        nUIActivity.mNUIView = nUIView;
        nUIView.setOnDoneListener(new OnDoneListener() {
            public void done() {
                NUIActivity.super.finish();
            }
        });
        if (extras != null) {
            int i2 = extras.getInt("START_PAGE");
            fromString = SOFileState.fromString(extras.getString("STATE"), SOFileDatabase.getDatabase());
            string = extras.getString("FOREIGN_DATA", null);
            boolean z5 = extras.getBoolean("IS_TEMPLATE", true);
            String string2 = extras.getString("CUSTOM_DOC_DATA");
            if (fromString == null && !z) {
                fromString = SOFileState.getAutoOpen(this);
                if (fromString != null) {
                    str = string2;
                    z4 = z3;
                    z2 = z5;
                    i = i2;
                }
            }
            z3 = z4;
            str = string2;
            z4 = z3;
            z2 = z5;
            i = i2;
        } else {
            fromString = null;
            string = null;
            str = string;
            z2 = true;
            i = 1;
        }
        if (str == null) {
            Utilities.setSessionLoadListener(null);
        }
        if (z4) {
            nUIActivity.mNUIView.start(sODocSession, i, string);
        } else if (fromString != null) {
            nUIActivity.mNUIView.start(fromString, i);
        } else {
            nUIActivity.mNUIView.start(intent.getData(), z2, i, str, intent.getType());
        }
    }


    /*private void a(Intent var1, boolean var2) {
        Bundle var3 = var1.getExtras();
        boolean var4 = false;
        boolean var5;
        if (var3 != null) {
            var5 = var3.getBoolean("SESSION", false);
        } else {
            var5 = false;
        }

        SODocSession var6 = a;
        this.setContentView(layout.sodk_editor_doc_view_activity);
        NUIView var7 = (NUIView)this.findViewById(id.doc_view);
        this.mNUIView = var7;
        var7.setOnDoneListener(new OnDoneListener() {
            public void done() {
                NUIActivity.super.finish();
            }
        });
        int var8;
        String var9;
        boolean var10;
        String var11;
        SOFileState var13;
        String var16;
        if (var3 != null) {
            label33: {
                var8 = var3.getInt("START_PAGE");
                SOFileState var15 = SOFileState.fromString(var3.getString("STATE"), SOFileDatabase.getDatabase());
                var9 = var3.getString("FOREIGN_DATA", (String)null);
                var10 = var3.getBoolean("IS_TEMPLATE", true);
                var11 = var3.getString("CUSTOM_DOC_DATA");
                var13 = var15;
                if (var15 == null) {
                    var13 = var15;
                    if (!var2) {
                        var15 = SOFileState.getAutoOpen(this);
                        var13 = var15;
                        if (var15 != null) {
                            var2 = var4;
                            var13 = var15;
                            break label33;
                        }
                    }
                }

                var2 = var5;
            }

            var16 = var11;
            var5 = var10;
            var10 = var2;
            var2 = var5;
        } else {
            var11 = null;
            var16 = var11;
            var2 = true;
            var8 = 1;
            var9 = var11;
            var13 = var11;
            var10 = var5;
        }

        if (var16 == null) {
            Utilities.setSessionLoadListener((SODocSessionLoadListenerCustom)null);
        }

        if (var10) {
            this.mNUIView.start(var6, var8, var9);
        } else if (var13 != null) {
            this.mNUIView.start(var13, var8);
        } else {
            Uri var14 = var1.getData();
            String var12 = var1.getType();
            this.mNUIView.start(var14, var2, var8, var16, var12);
        }

    }*/

    public static void setSession(SODocSession var0) {
        a = var0;
    }

    public Intent childIntent() {
        return this.b;
    }

    protected void doResumeActions() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onResume();
        }

    }

    public void finish() {
        if (this.mNUIView == null) {
            super.finish();
        } else {
            Utilities.dismissCurrentAlert();
            this.onBackPressed();
        }
    }

    protected void initialise() {
        this.a();
    }

    public boolean isDocModified() {
        NUIView var1 = this.mNUIView;
        return var1 != null ? var1.isDocModified() : false;
    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        NUIView var4 = this.mNUIView;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        }

    }

    public void onBackPressed() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onBackPressed();
        }

    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        this.mNUIView.onConfigurationChange(var1);
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.initialise();
    }

    protected void onDestroy() {
        super.onDestroy();
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onDestroy();
        }

    }

    public boolean onKeyDown(int var1, KeyEvent var2) {
        long var3 = var2.getEventTime();
        if (this.d == var1 && var3 - this.c <= 100L) {
            return true;
        } else {
            this.c = var3;
            this.d = var1;
            return this.mNUIView.doKeyDown(var1, var2);
        }
    }

    public void onNewIntent(final Intent var1) {
        ConfigOptions.a();
        if (this.isDocModified()) {
            Utilities.yesNoMessage(this, this.getString(string.sodk_editor_new_intent_title), this.getString(string.sodk_editor_new_intent_body), this.getString(string.sodk_editor_new_intent_yes_button), this.getString(string.sodk_editor_new_intent_no_button), new Runnable() {
                public void run() {
                    if (NUIActivity.this.mNUIView != null) {
                        NUIActivity.this.mNUIView.endDocSession(true);
                    }

                    NUIActivity.this.a(var1);
                    NUIActivity.this.a(var1, true);
                }
            }, new Runnable() {
                public void run() {
                    SODocSessionLoadListenerCustom var1 = Utilities.getSessionLoadListener();
                    if (var1 != null) {
                        var1.onSessionReject();
                    }

                    Utilities.setSessionLoadListener((SODocSessionLoadListenerCustom)null);
                }
            });
        } else {
            NUIView var2 = this.mNUIView;
            if (var2 != null) {
                var2.endDocSession(true);
            }

            this.a(var1);
            this.a(var1, true);
        }

    }

    public void onPause() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onPause();
            this.mNUIView.releaseBitmaps();
        }

        if (Utilities.isChromebook(this)) {
            PrintHelperPdf.setPrinting(false);
        }

        super.onPause();
    }

    protected void onResume() {
        this.b = null;
        super.onResume();
        this.doResumeActions();
    }

    protected void setConfigurableButtons() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.setConfigurableButtons();
        }

    }

    public void startActivity(Intent var1) {
        this.b = var1;
        super.startActivity(var1, (Bundle)null);
    }

    public void startActivityForResult(Intent var1, int var2) {
        this.b = var1;
        super.startActivityForResult(var1, var2);
    }

    public void startActivityForResult(Intent var1, int var2, Bundle var3) {
        this.b = var1;
        super.startActivityForResult(var1, var2, var3);
    }
}
