package com.all.officereader.screens.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ads.control.AdmobHelp;
import com.ads.control.Rate;
import com.airbnb.lottie.LottieAnimationView;
import com.all.officereader.adapter.OfficePagerAdapter;
import com.github.ybq.android.spinkit.style.Wave;
import com.all.officereader.R;
import com.all.officereader.adapter.FileSearchAdapter;

import com.all.officereader.helpers.bases.animation.AlphaAnimator;
import com.all.officereader.helpers.callbacks.DialogSelectionListener;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.helpers.rating.RateStarDialog;
import com.all.officereader.helpers.utils.Constant;
import com.all.officereader.helpers.utils.FileUtility;
import com.all.officereader.helpers.utils.StorageUtils;
import com.all.officereader.helpers.utils.SystemUtil;
import com.all.officereader.model.FileListItem;
import com.all.officereader.screens.dialogs.FeedbackDialog;
import com.all.officereader.screens.fragments.BookmarkFragment;
import com.all.officereader.screens.fragments.FileManagerFragment;
import com.all.officereader.screens.fragments.FileReaderFragment;
import com.all.officereader.screens.fragments.RecentFragment;
import com.all.officereader.popup.CreateFilesPopup;
import com.all.officereader.screens.ocr.CameraActivity;
import com.realpacific.clickshrinkeffect.ClickShrinkEffectKt;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ItemFileClickListener,
        DialogSelectionListener, FileSearchAdapter.fileSearchAdapterListener, FileManagerFragment.fileManagerFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int CODE_RESULT_BOOKMARK = 2;
    private static final int REQUEST_CAMERA_PERMISSIONS = 0x111;
    public static MainActivity mMainActivity;

    private ImageView imvActivityMainHome;
    private File mFile;
    private StorageUtils storageUtil;
    private DrawerLayout drawerLayout;
    private EditText edtActivityMainSearch2;
    private LinearLayout tabMainActivityFileReader;
    private LinearLayout tabMainActivityRecent;
    private LinearLayout tabMainActivityBookmark;
    private LinearLayout tabMainActivityFileManager;
    private RelativeLayout rllRateApp;
    private RelativeLayout rllShareApp;
    private RelativeLayout rllFeedBackApp;
    private RelativeLayout rllMainActivitySearch;
    private RecyclerView rcvMainActivitySearch;
    private RelativeLayout rllMainActivityNoFile;
    private ProgressBar pgbLoadingSearch;
    private FileSearchAdapter mFileSearchAdapter;
    private LottieAnimationView imvActivityMainGiftAds;
    private ImageView lotViewActivityMainSearch;
    private ImageView imvActivityMainDonate;
    private ImageView imgOCR;

    private boolean isFirstChar = false;
    private long timeClick = 0;
    private ImageView floatingMainActivityFab;
    private RelativeLayout rllActivityMainPopupDim;
    private CreateFilesPopup mOCreateFilesPopup;
    private long timeClickNew = 0;
    private ImageView imvActivityMainPro;

    private ViewPager vpMain;
    private OfficePagerAdapter mOfficePagerAdapter;
    private ArrayList<Fragment> mFragmentArrayList;

    private ImageView imgHome;
    private TextView tvHome;

    private ImageView imgRecent;
    private TextView tvRecent;

    private ImageView imgBookmark;
    private TextView tvBookmark;

    private ImageView imgStorage;
    private TextView tvStorage;

    private FileReaderFragment fileReaderFragment;
    private RecentFragment recentFragment;
    private BookmarkFragment bookmarkFragment;
    private FileManagerFragment fileManagerFragment;

    //private HomeReceiver homeReceiver;
    private Disposable mDisposable;
    private ProgressBar mProgressBar;
    private LinearLayout lnNoFile;
    private boolean isLoadData = false;

    public static MainActivity getInstance() {
        return mMainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mMainActivity = this;
        initView();
        initData();
        //queryDataFile();
    }

    private void queryDataFile() {
        if (!isLoadData) {
            Observable.create((ObservableOnSubscribe<ArrayList<File>>) emitter -> {
                Log.e(TAG, "loadingFile: create: ");
                FileUtility.mAllFileOffice.clear();
                FileUtility.mWordFiles.clear();
                FileUtility.mPdfFiles.clear();
                FileUtility.mExcelFiles.clear();
                FileUtility.mPowerPointFiles.clear();
                FileUtility.mListTxtFile.clear();
                emitter.onNext(FileUtility.getAllFile(FileUtility.mDir));
                emitter.onComplete();
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<File>>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(@NotNull ArrayList<File> files) {
                            if (files != null) {
                                if (files.size() > 0) {
                                    isLoadData = true;
                                    mProgressBar.setVisibility(View.GONE);
                                    lnNoFile.setVisibility(View.GONE);
                                    //FileUtility.createFileNoMedia(MainActivity.this);

                                    Log.e(TAG, "loadingFile: onNext: " + files.size() + "...files: " + FileUtility.mAllFileOffice.size());
                                    updateData();
                                } else {
                                    lnNoFile.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e(TAG, "loadingFile: onError" + e.getMessage());
                            mProgressBar.setVisibility(View.GONE);
                            lnNoFile.setVisibility(View.VISIBLE);
                            isLoadData = false;
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initHomeWatcher();
        queryDataFile();
    }

    private void initHomeWatcher() {
        /*try {
            homeReceiver = new HomeReceiver(this);
            homeReceiver.setOnHomePressedListener(new HomeReceiver.OnHomePressedListener() {
                @Override
                public void onHomeLongPressed() {
                    if (!isShowOpen) {
                        Intent mIntent = new Intent(MainActivity.this, SplashActivity.class);
                        startActivity(mIntent);
                        finish();
                        isShowOpen = true;
                    }
                }

                @Override
                public void onHomePressed() {
                }
            });
            HomeReceiver.startWatch();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void updateData() {
        Log.e("AAAAAAAA", "loadingFile: updateData_main: " + fileReaderFragment);
        if (fileReaderFragment != null) {
            fileReaderFragment.updateData();
            if (fileReaderFragment.fileReaderAllFragment == null) {
                if (rllActivityMainPopupDim != null)
                    rllActivityMainPopupDim.setVisibility(View.VISIBLE);
                startActivity(new Intent(this, SplashActivity.class));
                finish();
            }
        }
    }

    private void initView() {
        storageUtil = new StorageUtils(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        imvActivityMainHome = findViewById(R.id.imv_activity_main__home);

        edtActivityMainSearch2 = findViewById(R.id.edt_activity_main__search2);

        tabMainActivityFileReader = findViewById(R.id.tab__main_activity__file_reader);
        tabMainActivityRecent = findViewById(R.id.tab__main_activity__recent);
        tabMainActivityBookmark = findViewById(R.id.tab__main_activity__bookmark);
        tabMainActivityFileManager = findViewById(R.id.tab__main_activity__file_manager);
        mProgressBar = findViewById(R.id.pb_loading_data);
        lnNoFile = findViewById(R.id.ln_no_file);

        vpMain = findViewById(R.id.vp_main);

        imvActivityMainDonate = findViewById(R.id.imv_activity_main__donate);
        imvActivityMainPro = findViewById(R.id.imv_activity_main__pro);
        imgOCR = findViewById(R.id.img_ocr);

        rllRateApp = findViewById(R.id.rll_rate_app);
        rllShareApp = findViewById(R.id.rll_share_app);
        rllFeedBackApp = findViewById(R.id.rll_feedback_app);

        rllMainActivitySearch = findViewById(R.id.rll_main_activity__search);
        rcvMainActivitySearch = findViewById(R.id.rcv_main_activity_search);
        rllMainActivityNoFile = findViewById(R.id.rll_main_activity_no_file);
        pgbLoadingSearch = findViewById(R.id.pgb_loading_search);
        imvActivityMainGiftAds = findViewById(R.id.imv_activity_main__gift_ads);
        lotViewActivityMainSearch = findViewById(R.id.lotView_activity_main__search);

        floatingMainActivityFab = findViewById(R.id.floating_main_activity__fab);
        rllActivityMainPopupDim = findViewById(R.id.rll_activity_main__popupDim);

        imgHome = findViewById(R.id.iv_home_act);
        tvHome = findViewById(R.id.tv_home_act);
        imgRecent = findViewById(R.id.im_recent);
        tvRecent = findViewById(R.id.tv_recent);
        imgBookmark = findViewById(R.id.iv_bookmark);
        tvBookmark = findViewById(R.id.tv_bookmark);
        imgStorage = findViewById(R.id.iv_storage);
        tvStorage = findViewById(R.id.tv_storage);

        floatingMainActivityFab.setOnClickListener(this);
        imvActivityMainPro.setOnClickListener(this);
        imgOCR.setOnClickListener(this);

        Wave doubleBounce = new Wave();
        doubleBounce.setColor(getResources().getColor(R.color.colorWord_2392FF));
        pgbLoadingSearch.setIndeterminateDrawable(doubleBounce);

        SystemUtil.hideKeyboard(edtActivityMainSearch2);
        onListener();
        onAddTextChange();

        imvActivityMainGiftAds.setAnimation("newGift.json");
        imvActivityMainGiftAds.playAnimation();
        try {
            imvActivityMainGiftAds.loop(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lotViewActivityMainSearch.setOnClickListener(this);
        imvActivityMainDonate.setOnClickListener(this);
    }

    private void onAddTextChange() {
        edtActivityMainSearch2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged: aaa");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged: bbb");
                if ("".equals(edtActivityMainSearch2.getText().toString().trim())) {
                    isFirstChar = false;

                } else {
                    if (!isFirstChar) {
                        isFirstChar = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ("".equals(edtActivityMainSearch2.getText().toString().trim())) {
                    if (rllMainActivitySearch.getVisibility() == View.VISIBLE) {
                        rllMainActivitySearch.setVisibility(View.GONE);
                        vpMain.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (rllMainActivitySearch.getVisibility() == View.GONE) {
                        rllMainActivitySearch.setVisibility(View.VISIBLE);
                        vpMain.setVisibility(View.GONE);
                    }
                }

                rllMainActivityNoFile.setVisibility(View.GONE);
                pgbLoadingSearch.setVisibility(View.VISIBLE);

                try {
                    FileUtility.searchFile(FileUtility.mDirCache, editable.toString()).subscribe(new Observer<ArrayList<File>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(ArrayList<File> fileList) {
                            setAdapter(fileList);
                            pgbLoadingSearch.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            pgbLoadingSearch.setVisibility(View.GONE);
                            rllMainActivityNoFile.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
                } catch (Exception ignored) {
                    Log.e(TAG, "afterTextChanged_Exception: " + ignored.getMessage());
                    ignored.printStackTrace();
                }
            }
        });

        edtActivityMainSearch2.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                        if (!edtActivityMainSearch2.getText().toString().trim().equals("")) {
                            FileUtility.closeKeyboard(MainActivity.this);
                        } else
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.plz_enter_content), Toast.LENGTH_SHORT).show();

                        return true;
                    default:
                        return true;
                }
            }
            return false;
        });
    }

    private void setAdapter(ArrayList<File> fileList) {
        if (fileList != null && fileList.size() == 0) {
            rllMainActivityNoFile.setVisibility(View.VISIBLE);

        } else if (fileList != null) {
            rllMainActivityNoFile.setVisibility(View.GONE);
            rcvMainActivitySearch.setVisibility(View.VISIBLE);
            mFileSearchAdapter = new FileSearchAdapter(fileList, 1, this, "", this);
            rcvMainActivitySearch.setAdapter(mFileSearchAdapter);
            mFileSearchAdapter.notifyDataSetChanged();
        }
    }

    private void onListener() {
        imvActivityMainHome.setOnClickListener(this);
        rllRateApp.setOnClickListener(this);
        rllShareApp.setOnClickListener(this);
        rllFeedBackApp.setOnClickListener(this);
        imvActivityMainGiftAds.setOnClickListener(this);

        tabMainActivityFileReader.setOnClickListener(this);
        tabMainActivityRecent.setOnClickListener(this);
        tabMainActivityBookmark.setOnClickListener(this);
        tabMainActivityFileManager.setOnClickListener(this);

        ClickShrinkEffectKt.applyClickShrink(rllRateApp);
        ClickShrinkEffectKt.applyClickShrink(rllShareApp);
        ClickShrinkEffectKt.applyClickShrink(rllFeedBackApp);
    }

    private void initData() {
        rcvMainActivitySearch.setLayoutManager(new LinearLayoutManager(this));

        fileReaderFragment = new FileReaderFragment();
        recentFragment = new RecentFragment();
        bookmarkFragment = new BookmarkFragment();
        fileManagerFragment = new FileManagerFragment();

        SystemUtil.hideKeyboard(edtActivityMainSearch2);

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(fileReaderFragment);
        mFragmentArrayList.add(recentFragment);
        mFragmentArrayList.add(bookmarkFragment);
        mFragmentArrayList.add(fileManagerFragment);

        mOfficePagerAdapter = new OfficePagerAdapter(MainActivity.this, getSupportFragmentManager(), mFragmentArrayList);
        vpMain.setAdapter(mOfficePagerAdapter);
        vpMain.setCurrentItem(0);
        vpMain.setOffscreenPageLimit(4);
        OverScrollDecoratorHelper.setUpOverScroll(vpMain);

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                switch (i) {
                    case 0:
                        funcClickTabReader();
                        break;

                    case 1:
                        funcClickTabRecent();
                        break;

                    case 2:
                        funcClickTabBookmark();
                        break;
                    case 3:
                        funcClickTabStorage();
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == tabMainActivityFileReader) {
            vpMain.setCurrentItem(0);
            funcClickTabReader();

        } else if (view == tabMainActivityRecent) {
            vpMain.setCurrentItem(1);
            funcClickTabRecent();

        } else if (view == tabMainActivityBookmark) {
            vpMain.setCurrentItem(2);
            funcClickTabBookmark();

        } else if (view == tabMainActivityFileManager) {
            vpMain.setCurrentItem(3);
            funcClickTabStorage();

        } else if (view == imvActivityMainHome) {
            drawerLayout.openDrawer(GravityCompat.START);

        } else if (view == rllRateApp) {
            drawerLayout.closeDrawers();
            RateStarDialog rateStarDialog = new RateStarDialog(this);
            //Utility.setUpDialog(rateStarDialog, this);
            rateStarDialog.show();

        } else if (view == rllShareApp) {
            drawerLayout.closeDrawers();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Please share app with everyone!";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (view == rllFeedBackApp) {
            drawerLayout.closeDrawers();
            FeedbackDialog rateFeedBackDialogs = new FeedbackDialog(this);
            rateFeedBackDialogs.show();

        } else if (view == lotViewActivityMainSearch) {
            edtActivityMainSearch2.setText("");
            SystemUtil.showKeyboard(edtActivityMainSearch2);

        } else if (view == imgOCR) {
            onAddCameraClicked();

        } else if (view == floatingMainActivityFab) {
            if (System.currentTimeMillis() - timeClickNew > 500) {
                timeClickNew = System.currentTimeMillis();
                if (mOCreateFilesPopup == null) {
                    mOCreateFilesPopup = new CreateFilesPopup(MainActivity.this);
                }
                AlphaAnimator.visibleAnimation(rllActivityMainPopupDim, 300);

                mOCreateFilesPopup.setOnDismissListener(() -> AlphaAnimator.goneAnimation(rllActivityMainPopupDim, 300));
                mOCreateFilesPopup.showAtLocation(rllActivityMainPopupDim, Gravity.BOTTOM, 0, 0);
            }
        }
    }

    private void funcClickTabReader() {
        tvHome.setTextColor(getResources().getColor(R.color.color_enable_tab));
        tvRecent.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvBookmark.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvStorage.setTextColor(getResources().getColor(R.color.color_disable_tab));

        imgHome.setColorFilter(getResources().getColor(R.color.color_enable_tab));
        imgRecent.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgBookmark.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgStorage.setColorFilter(getResources().getColor(R.color.color_disable_tab));
    }

    private void funcClickTabRecent() {
        tvHome.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvRecent.setTextColor(getResources().getColor(R.color.color_enable_tab));
        tvBookmark.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvStorage.setTextColor(getResources().getColor(R.color.color_disable_tab));

        imgHome.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgRecent.setColorFilter(getResources().getColor(R.color.color_enable_tab));
        imgBookmark.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgStorage.setColorFilter(getResources().getColor(R.color.color_disable_tab));
    }

    private void funcClickTabBookmark() {
        tvHome.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvRecent.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvBookmark.setTextColor(getResources().getColor(R.color.color_enable_tab));
        tvStorage.setTextColor(getResources().getColor(R.color.color_disable_tab));

        imgHome.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgRecent.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgBookmark.setColorFilter(getResources().getColor(R.color.color_enable_tab));
        imgStorage.setColorFilter(getResources().getColor(R.color.color_disable_tab));
    }

    private void funcClickTabStorage() {
        tvHome.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvRecent.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvBookmark.setTextColor(getResources().getColor(R.color.color_disable_tab));
        tvStorage.setTextColor(getResources().getColor(R.color.color_enable_tab));

        imgHome.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgRecent.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgBookmark.setColorFilter(getResources().getColor(R.color.color_disable_tab));
        imgStorage.setColorFilter(getResources().getColor(R.color.color_enable_tab));
    }

    public void onAddCameraClicked() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.CAMERA};

            List<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
            } else addCamera();
        } else {
            addCamera();
        }
    }

    private void addCamera() {
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSIONS) {
            if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.CAMERA)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addCamera();
                } else {
                    onAddCameraClicked();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onItemClick(File file) {
        mFile = file;
        AdmobHelp.getInstance().showInterstitialAd(this, new AdmobHelp.AdCloseListener() {
            @Override
            public void onAdClosed() {
                FileUtility.openFile(MainActivity.this, file, 0);
                if (recentFragment != null) {
                    recentFragment.updateData();
                }
            }
        });
    }

    @Override
    public void onAddToBookmark(File file) {
        mFile = file;
        storageUtil.addBookmark(MainActivity.this, mFile);
        if (bookmarkFragment != null) {
            bookmarkFragment.onUpdateData(this);
        }
    }

    @Override
    public void onCreateShortCut(File file) {
        mFile = file;
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(this)) {
            Intent intent = new Intent(new Intent(this, SplashActivity.class));
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.SHORT_CUT_FILE_NAME, mFile.getAbsolutePath());
            intent.putExtra(Constant.SHORT_CUT_PAGE_NUM, 0);

            Log.e("XTXXXXXXXX", "shortcutFile: " + mFile.getName());
            if (mFile.isFile()) {
                if (mFile.getName().endsWith(".pdf")) {
                    Log.e("XTXXXXXXXX", "111111:pdf " + mFile.getName());

                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_pdf))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);


                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();
                } else if (mFile.getName().endsWith(".xls") || mFile.getName().endsWith(".xlsx")) {
                    Log.e("XTXXXXXXXX", "22222:excel " + mFile.getName());
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_excel))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();
                } else if (mFile.getName().endsWith(".ppt") || mFile.getName().endsWith(".pptx")) {
                    Log.e("XTXXXXXXXX", "333:ppt " + mFile.getName());
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_ppt))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();
                } else if (mFile.getName().endsWith(".doc") || mFile.getName().endsWith(".docx") || mFile.getName().endsWith(".docb")) {
                    Log.e("XTXXXXXXXX", "4444:doc " + mFile.getName());
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_word))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("XTXXXXXXXX", "555:#### " + mFile.getName());
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_folder_item))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                }
            }
        }
    }

    @Override
    public void onShareFile(File file) {
        mFile = file;
        try {
            Uri uri;
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("file/*");
            try {
                uri = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", mFile);
            } catch (Exception e) {
                uri = Uri.fromFile(mFile);
            }
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(sharingIntent, "Share Document!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemoveBookmark(File file) {
        mFile = file;
        storageUtil.removeBookmark(MainActivity.this, mFile);
        if (bookmarkFragment != null) {
            bookmarkFragment.updateData();
        }
    }

    @Override
    public void onSelectedFilePaths(String[] files) {
        mFile = new File(files[0]);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.CODE_RESULT_BOOKMARK) {
            if (resultCode == RESULT_OK) {
                queryDataFile();
                if (recentFragment != null) {
                    recentFragment.onUpdateData(this);
                }

                if (bookmarkFragment != null) {
                    bookmarkFragment.onUpdateData(this);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            Rate.Show(MainActivity.this, 1, new Rate.onDismissDialog() {
                @Override
                public void onDismissed() {
                    finish();
                    finishAffinity();
                }
            });
        }
    }

    @Override
    public void onItemSearchClick(File file) {
        mFile = file;
        edtActivityMainSearch2.setText("");
        if (rllMainActivitySearch.getVisibility() == View.VISIBLE) {
            rllMainActivitySearch.setVisibility(View.GONE);
        }
        FileUtility.openFile(this, file, 0);
    }

    @Override
    public void onBackFile(FileListItem fileListItem) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (homeReceiver != null) {
            HomeReceiver.stopWatch();
        }*/
        mMainActivity = null;
        /*FileUtility.mAllFileOffice.clear();
        FileUtility.mWordFiles.clear();
        FileUtility.mExcelFiles.clear();
        FileUtility.mPdfFiles.clear();
        FileUtility.mPowerPoinFiles.clear();*/
    }
}
