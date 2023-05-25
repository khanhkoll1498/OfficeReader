package com.all.officereader.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.nativeAds.NativeAdsView;
import com.ads.control.nativeAds.NativeLoadListener;
import com.ads.control.nativeAds.NativeViewHolder;
import com.all.officereader.helpers.utils.WebUtil;
import com.bumptech.glide.Glide;
import com.all.officereader.R;
import com.all.officereader.helpers.callbacks.ItemFileClickListener;
import com.all.officereader.helpers.utils.FileUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileAdsFragmentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ItemFileClickListener mItemFileClickListener;
    private int CONTENT = 0;
    private int AD = 1;
    private List<File> mListFileUtil;
    private fileAdsFragmentAdapterListener mFileAdsFragmentAdapterListener;
    private NativeAdsView mNativeAdsView;

    public FileAdsFragmentAdapter(ArrayList<File> filePaths, Context _context, ItemFileClickListener mItemFileClickListener, fileAdsFragmentAdapterListener fileAdsFragmentAdapterListener) {
        mListFileUtil = filePaths;

        this.mContext = _context;
        this.mItemFileClickListener = mItemFileClickListener;
        this.mFileAdsFragmentAdapterListener = fileAdsFragmentAdapterListener;

        mNativeAdsView = new NativeAdsView(_context, new NativeLoadListener() {
            @Override
            public void onErro() {
                try {
                    notifyItemRemoved(AD);
                    mListFileUtil.remove(AD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadSuccess() {
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CONTENT) {
            View dataLayoutView = LayoutInflater.from(mContext).inflate(R.layout.item_file, parent, false);
            return new FileViewHolder(dataLayoutView);

        } else {
            return new NativeViewHolder(mNativeAdsView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == CONTENT) {
            bindDataTypeView(viewHolder, position);
        }
    }

    private void bindDataTypeView(RecyclerView.ViewHolder viewHolder, final int position) {
        final FileViewHolder holder = (FileViewHolder) viewHolder;
        final File file;
        if (mListFileUtil.size() > 0) {
            file = mListFileUtil.get(position);

            if (file != null) {
                switch (FileUtility.fileType(file)) {
                    case 6:
                        Glide.with(mContext).load(file.getAbsolutePath())
                                .dontAnimate()
                                .placeholder(R.drawable.ic_folder_item)
                                .into(holder.imvItemFileFile);
                        break;

                    case 7:
                        holder.imvItemFileFile.setImageResource(R.drawable.menu_pdf);
                        break;

                    case 8:
                        holder.imvItemFileFile.setImageResource(R.drawable.menu_ppt);
                        break;

                    case 9:
                        holder.imvItemFileFile.setImageResource(R.drawable.ic_other_item);
                        break;

                    case 11:
                        holder.imvItemFileFile.setImageResource(R.drawable.menu_word);
                        break;

                    case 5:
                        holder.imvItemFileFile.setImageResource(R.drawable.menu_excel);
                        break;

                    default:
                        holder.imvItemFileFile.setImageResource(R.drawable.ic_folder_item);
                        break;

                }
                holder.txvItemItemFileTitle.setContentDescription(file.getName());
                holder.txvItemItemFileTitle.setText(file.getName());
                SimpleDateFormat sdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = new Date(file.lastModified());
                holder.txtItemItemFileDate.setText(sdate.format(date));
                holder.txvItemItemFileDetail.setText(FileUtility.convertFileSize(file.length()) + "");
            }
        }

        holder.imvItemItemFileMoreChoose.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(mContext, holder.itemView, Gravity.RIGHT);
            popup.getMenuInflater().inflate(R.menu.menu_popup_bookmark, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.mn_open:
                        if (mItemFileClickListener != null) {
                            mItemFileClickListener.onItemClick(mListFileUtil.get(position));
                        }
                        break;

                    case R.id.mn_share:
                        if (mItemFileClickListener != null) {
                            mItemFileClickListener.onShareFile(mListFileUtil.get(position));
                        }
                        break;
                    case R.id.mn_create_short_cut:
                        if (mItemFileClickListener != null) {
                            mItemFileClickListener.onCreateShortCut(mListFileUtil.get(position));
                        }
                        break;

                }
                return true;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        if (mListFileUtil.size() > 0) {
            return mListFileUtil.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!WebUtil.isOnline(mContext)) {
            return CONTENT;
        } else {
            if (position == AD) {
                return AD;
            } else {
                return CONTENT;
            }
        }
    }

    public void updateData(Activity activity, ArrayList<File> listFile) {
        mListFileUtil.clear();
        mListFileUtil = listFile;
        notifyDataSetChanged();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvItemFileFile;
        private TextView txvItemItemFileTitle;
        private TextView txtItemItemFileDate;
        private TextView txvItemItemFileDetail;
        private ImageView imvItemItemFileMoreChoose;

        FileViewHolder(View itemView) {
            super(itemView);
            imvItemFileFile = itemView.findViewById(R.id.imv_item_file__file);
            txvItemItemFileTitle = itemView.findViewById(R.id.txv_item_item_file__title);
            txtItemItemFileDate = itemView.findViewById(R.id.txt_item_item_file___date);
            txvItemItemFileDetail = itemView.findViewById(R.id.txv_item_item_file__detail);
            imvItemItemFileMoreChoose = itemView.findViewById(R.id.imv_item_item_file__more__choose);


            itemView.setOnClickListener(view -> {
                int position = getLayoutPosition();
                if (mItemFileClickListener != null) {
                    mItemFileClickListener.onItemClick(mListFileUtil.get(position));
                }
            });
        }
    }

    public interface fileAdsFragmentAdapterListener {
        void onShowNativeBannerSuccess(boolean b);
    }
}
