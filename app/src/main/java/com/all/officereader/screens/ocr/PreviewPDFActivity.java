package com.all.officereader.screens.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.all.officereader.R;
import com.all.officereader.screens.activities.MainActivity;
import com.all.officereader.screens.ocr.popups.ExportDialog;
import com.tejpratapsingh.pdfcreator.utils.PDFUtil;
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PreviewPDFActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = PreviewPDFActivity.class.getName();
    private ArrayList<String> mListDocument = new ArrayList<>();
    private List<View> contentViews = new ArrayList<>();
    private RecyclerView rvListPDF;
    private PreviewAdapter previewAdapter;

    private TextView tvSave;
    private TextView tvPagePDF;
    private TextView tvNameFile;
    private ImageView imgBack;
    private LinearLayout lnLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_scanner);

        mListDocument = getIntent().getStringArrayListExtra("List_image_scanner");
        initViews();
        initEvent();
        initAdapter();
    }

    private void initViews() {
        rvListPDF = findViewById(R.id.rv_preview_scanner);
        tvSave = findViewById(R.id.tv_save_preview);
        tvNameFile = findViewById(R.id.tv_name_preview);
        imgBack = findViewById(R.id.iv_back);
        tvPagePDF = findViewById(R.id.footer_page_text);
        lnLoading = findViewById(R.id.loading_view);

        tvNameFile.setText("preview_" + System.currentTimeMillis() + ".pdf");
    }

    private void initEvent() {
        tvSave.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initAdapter() {
        previewAdapter = new PreviewAdapter(mListDocument, this);
        rvListPDF.setLayoutManager(new LinearLayoutManager(this));
        rvListPDF.setAdapter(previewAdapter);
        previewAdapter.notifyDataSetChanged();

        tvPagePDF.setText(1 + File.separator + mListDocument.size());

        rvListPDF.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPos = 0;

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int offset = rvListPDF.computeHorizontalScrollOffset();
                if (offset % rvListPDF.getHeight() == 0) {
                    int position = ((LinearLayoutManager) rvListPDF.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                    if (position != -1) {
                        lastPos = position + 1;
                    }

                    tvPagePDF.setText(lastPos + File.separator + mListDocument.size());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == tvSave) {
            ExportDialog exportDialog = new ExportDialog(this, tvNameFile.getText().toString());
            exportDialog.setOnExportListener(new ExportDialog.onExportListener() {
                @Override
                public void onExport() {
                    if (exportDialog.edtNameFile != null) {
                        funcCreateFilePDF(exportDialog.edtNameFile.getText().toString());
                    } else {
                        funcCreateFilePDF(tvNameFile.getText().toString());
                    }
                }
            });
            exportDialog.show();

        } else if (v == imgBack) {
            finish();
        }
    }

    private void funcCreateFilePDF(String nameFile) {
        lnLoading.setVisibility(View.VISIBLE);
        String path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + nameFile;

        } else {
            path = getExternalFilesDir(null).getPath() + "/" + nameFile;
        }
        for (int i = 0; i < mListDocument.size(); i++) {
            Bitmap bm = BitmapFactory.decodeFile(mListDocument.get(i));
            PDFImageView pdfImageView = new PDFImageView(PreviewPDFActivity.this);
            pdfImageView.setImageBitmap(bm);
            pdfImageView.getView().setPadding(20, 12, 20, 12);

            contentViews.add(pdfImageView.getView());
        }

        PDFUtil.getInstance().generatePDF(contentViews, path, new PDFUtil.PDFUtilListener() {
            @Override
            public void pdfGenerationSuccess(File savedPDFFile) {
                lnLoading.setVisibility(View.GONE);
                Toast.makeText(PreviewPDFActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PreviewPDFActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void pdfGenerationFailure(Exception exception) {
                lnLoading.setVisibility(View.GONE);
                Toast.makeText(PreviewPDFActivity.this, "Create PDF Fail!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PreviewPDFActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
