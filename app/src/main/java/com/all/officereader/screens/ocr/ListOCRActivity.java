package com.all.officereader.screens.ocr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.all.officereader.BuildConfig;
import com.all.officereader.R;
import com.all.officereader.adapter.FileAdsFragmentAdapter;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.helpers.utils.Constant;
import com.all.officereader.helpers.utils.FileUtility;
import com.all.officereader.helpers.utils.StorageUtils;
import com.all.officereader.screens.activities.SplashActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListOCRActivity extends FragmentActivity implements View.OnClickListener, ItemFileClickListener, FileAdsFragmentAdapter.fileAdsFragmentAdapterListener {

    private RecyclerView rcvFragmentFileList;
    private FileAdsFragmentAdapter mFileFragmentAdapter;
    private TextView tvEmpty;
    private ArrayList<File> mFileArrayList = new ArrayList<>();
    private ImageView imgBack;
    private StorageUtils storageUtil;
    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ocr);

        initViews();
        initEvent();
        initAdapter();
    }

    private void initViews() {
        rcvFragmentFileList = findViewById(R.id.rv_ocr_item);
        imgBack = findViewById(R.id.iv_back);
        tvEmpty = findViewById(R.id.tv_empty);

        storageUtil = new StorageUtils(this);
    }

    private void initEvent() {
        imgBack.setOnClickListener(this);
    }

    private void initAdapter() {
        Observable.create(new ObservableOnSubscribe<ArrayList<File>>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<ArrayList<File>> emitter) {
                if (QueryAllStorage.queryAllOCR(ListOCRActivity.this) != null)
                    mFileArrayList = QueryAllStorage.queryAllOCR(ListOCRActivity.this);
                emitter.onNext(mFileArrayList);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<File>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NotNull ArrayList<File> files) {
                        if (mFileArrayList.size() > 0) {
                            tvEmpty.setVisibility(View.GONE);
                        } else {
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                        mFileFragmentAdapter = new FileAdsFragmentAdapter(mFileArrayList, ListOCRActivity.this, ListOCRActivity.this, ListOCRActivity.this);
                        rcvFragmentFileList.setLayoutManager(new LinearLayoutManager(ListOCRActivity.this));
                        rcvFragmentFileList.setAdapter(mFileFragmentAdapter);
                        mFileFragmentAdapter.notifyDataSetChanged();
                        //mDisposable.dispose();
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // TODO
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == imgBack) {
            finish();
        }
    }

    @Override
    public void onItemClick(File file) {
        FileUtility.openFile(this, file, 0);
    }

    @Override
    public void onAddToBookmark(File file) {
        storageUtil.addBookmark(ListOCRActivity.this, file);
    }

    @Override
    public void onCreateShortCut(File mFile) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(this)) {
            Intent intent = new Intent(new Intent(this, SplashActivity.class));
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.SHORT_CUT_FILE_NAME, mFile.getAbsolutePath());
            intent.putExtra(Constant.SHORT_CUT_PAGE_NUM, 0);

            if (mFile.isFile()) {
                if (mFile.getName().endsWith(".pdf")) {
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_pdf))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();

                } else if (mFile.getName().endsWith(".xls") || mFile.getName().endsWith(".xlsx")) {
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_excel))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();

                } else if (mFile.getName().endsWith(".ppt") || mFile.getName().endsWith(".pptx")) {
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_ppt))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();

                } else if (mFile.getName().endsWith(".doc") || mFile.getName().endsWith(".docx") || mFile.getName().endsWith(".docb")) {
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(this, UUID.randomUUID().toString())
                            .setIntent(intent) // !!! intent's action must be set on oreo
                            .setShortLabel(mFile.getName())
                            .setIcon(IconCompat.createWithResource(this, R.drawable.menu_word))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null);
                    Toast.makeText(this, "Create shortcut success!", Toast.LENGTH_SHORT).show();

                } else {
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
    public void onShareFile(File mFile) {
        try {
            Uri uri;
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("file/*");
            try {
                uri = FileProvider.getUriForFile(ListOCRActivity.this, BuildConfig.APPLICATION_ID + ".provider", mFile);
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
    }

    @Override
    public void onShowNativeBannerSuccess(boolean b) {
    }
}
