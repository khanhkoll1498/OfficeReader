package com.all.officereader.screens.ocr;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.all.officereader.R;
import com.all.officereader.screens.ocr.view.StickerView;

public class PreviewFragment extends Fragment {

    private static final String TAG = PreviewFragment.class.getName();
    public FrameLayout flItem;
    public CropImageView cropImageView;
    private Bitmap modifiedBitmap;
    private String mPathFile;
    private Bitmap origBitmap;
    private RectF actualCrop;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_image, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        flItem = view.findViewById(R.id.fr_preview_item);
        cropImageView = view.findViewById(R.id.cropImageView);

        cropImageView.setCropEnabled(false);
        //cropImageView.setImageBitmap(modifiedBitmap);
        Glide.with(getActivity())
                .asBitmap()
                .load(mPathFile)
                .error(R.color.gray)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        cropImageView.setImageBitmap(resource);
                        modifiedBitmap = resource;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        cropImageView.setInitialFrameScale(1.0F);
        cropImageView.setHandleShadowEnabled(false);
        cropImageView.setFrameColor(Color.TRANSPARENT);
        cropImageView.setHandleColor(Color.TRANSPARENT);

        actualCrop = cropImageView.getActualCropRect();
    }

    public void setBitmapCrop(String pathFile) {
        mPathFile = pathFile;
    }

    public void funcRotate(Bitmap bitmap, float flip) {
        modifiedBitmap = BitmapExtKt.rotate(bitmap, flip);
        origBitmap = modifiedBitmap;
        if (cropImageView != null) {
            cropImageView.setImageBitmap(modifiedBitmap);
        }
    }

    public void funcFlip(Bitmap bitmap, float flip) {
        modifiedBitmap = BitmapExtKt.rotate(bitmap, flip);
        origBitmap = modifiedBitmap;
        if (cropImageView != null) {
            cropImageView.setImageBitmap(modifiedBitmap);
        }
    }

    public final void startCrop() {
        if (cropImageView != null) {
            cropImageView.setCropEnabled(true);
            cropImageView.setOverlayColor(Color.parseColor("#60FFFFFF"));
            cropImageView.setFrameColor(Color.parseColor("#00FFF0"));
            cropImageView.setHandleColor(Color.parseColor("#00FFF0"));
        }
    }

    public final void stopCrop() {
        try {
            if (cropImageView != null) {
                cropImageView.setCropEnabled(false);
                cropImageView.cropAsync(new CropCallback() {
                    @Override
                    public void onSuccess(Bitmap cropped) {
                        Log.e(TAG, "onSuccess_stopCrop: " + cropped);
                        modifiedBitmap = cropped;
                        cropImageView.setImageBitmap(cropped);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError_stopCrop: " + e.getMessage());
                        e.printStackTrace();
                    }
                });

                cropImageView.setInitialFrameScale(1.0F);
                cropImageView.setHandleShadowEnabled(false);
                cropImageView.setFrameColor(Color.TRANSPARENT);
                cropImageView.setHandleColor(Color.TRANSPARENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void cancelCrop() {
        cropImageView.setFrameColor(Color.TRANSPARENT);
        cropImageView.setOverlayColor(Color.TRANSPARENT);
        cropImageView.setHandleColor(Color.TRANSPARENT);

        cropImageView.setCropEnabled(false);
    }

    public void funcSignature(Bitmap bitmap) {
        modifiedBitmap = bitmap;
        origBitmap = modifiedBitmap;
        StickerView stickerView = new StickerView(EditScannerActivity.getInstance());
        stickerView.setBitmap(bitmap);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                if (flItem != null)
                    flItem.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
            }

            @Override
            public void onTop(StickerView stickerView) {
            }
        });
        if (flItem != null)
            flItem.addView(stickerView);
    }

    public Bitmap getSavedBitmap() {
        return modifiedBitmap;
    }
}
