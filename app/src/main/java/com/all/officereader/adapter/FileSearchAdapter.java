package com.all.officereader.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.all.officereader.R;
import com.all.officereader.helpers.utils.FileUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class FileSearchAdapter extends RecyclerView.Adapter {
    private int imagePath;
    private Context mContext;
    private String extension;
    private fileSearchAdapterListener mFileSearchAdapterListener;
    private static final int CONTENT = 0;
    private static final int AD = 1;
    private List<File> mListFileUtil;

    public FileSearchAdapter(ArrayList<File> filePaths, int _imagePath, Context _context, String _extension, fileSearchAdapterListener fileSearchAdapterListener) {
        mListFileUtil = new ArrayList<>();
        mListFileUtil = filePaths;
        this.imagePath = _imagePath;
        this.mContext = _context;
        this.extension = _extension;
        this.mFileSearchAdapterListener = fileSearchAdapterListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dataLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_file_search, parent, false);
        return new MyViewHolder(dataLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        bindDataTypeView(viewHolder, i);
    }

    private void bindDataTypeView(RecyclerView.ViewHolder viewHolder, final int position) {
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        File file = mListFileUtil.get(position);
        if (file != null) {
            switch (FileUtility.fileType(file)) {

                case 6:
                    Glide.with(mContext).load(file.getAbsolutePath())
                            .dontAnimate()
                            .placeholder(R.drawable.ic_folder_item)
                            .into(holder.imvItemFileSearchIcon);
                    // setMarker(holder, mContext.getResources().getDrawable(R.drawable.circle_blue), Color.parseColor("#49b8fc"), position);
                    break;

                case 7:
                    holder.imvItemFileSearchIcon.setImageResource(R.drawable.menu_pdf);
                    // setMarker(holder, mContext.getResources().getDrawable(R.drawable.circle_red), Color.parseColor("#fd7b87"), position);
                    break;

                case 8:
                    holder.imvItemFileSearchIcon.setImageResource(R.drawable.menu_ppt);
                    // setMarker(holder, mContext.getResources().getDrawable(R.drawable.circle_yellow), Color.parseColor("#f89e01"), position);
                    break;

                case 11:
                    holder.imvItemFileSearchIcon.setImageResource(R.drawable.menu_word);
                    //setMarker(holder, mContext.getResources().getDrawable(R.drawable.circle_blue), Color.parseColor("#49b8fc"), position);
                    break;

                case 5:
                    holder.imvItemFileSearchIcon.setImageResource(R.drawable.menu_excel);
                    //setMarker(holder, mContext.getResources().getDrawable(R.drawable.circle_green), Color.parseColor("#107c11"), position);
                    break;

                default:
                    holder.imvItemFileSearchIcon.setImageResource(R.drawable.ic_folder_item);
                    //setMarker(holder, mContext.getResources().getDrawable(R.drawable.circle_blue), Color.parseColor("#49b8fc"), position);
                    break;

            }
        }

        holder.imvItemFileSearchIcon.setContentDescription(file.getName());
        holder.txtItemFileSearchName.setText(file.getName());
        SimpleDateFormat sdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat stime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        Date date = new Date(file.lastModified());
        holder.txtItemFileSearchDate.setText(mContext.getString(R.string.last_edit) + sdate.format(date) + ", " + stime.format(date));
        holder.txvItemFileSearchDetail.setText(FileUtility.formatFileSize(file.length(), false) + "");

    }

    @Override
    public int getItemViewType(int position) {
        if (mListFileUtil.get(position) == null)
            return AD;
        return CONTENT;
    }

    @Override
    public int getItemCount() {
        if (mListFileUtil != null) {
            return mListFileUtil.size();
        } else {
            return 1;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvItemFileSearchIcon;
        private TextView txtItemFileSearchName;
        private TextView txtItemFileSearchDate;
        private TextView txvItemFileSearchDetail;
        private ImageView imvItemFileSearchMoreChoose;

        MyViewHolder(View itemView) {
            super(itemView);
            imvItemFileSearchIcon = itemView.findViewById(R.id.imv_item_file_search__icon);
            txtItemFileSearchName = itemView.findViewById(R.id.txt_item_file_search__name);
            txtItemFileSearchDate = itemView.findViewById(R.id.txt_item_file_search__date);
            txvItemFileSearchDetail = itemView.findViewById(R.id.txv_item_item_file__detail);
            imvItemFileSearchMoreChoose = itemView.findViewById(R.id.imv_item_file_search_more__choose);

            itemView.setOnClickListener(view -> {
                int position = getLayoutPosition();
                if (mFileSearchAdapterListener != null) {
                     mFileSearchAdapterListener.onItemSearchClick(mListFileUtil.get(position));
                }

            });

            /*itemView.setOnLongClickListener(view -> {
                PopupMenu popup = new PopupMenu(mContext, itemView);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
                int position = getLayoutPosition();
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.mn_open:
                            if (mItemFileClickListener != null) {
                                mItemFileClickListener.onItemClick(mListFileUtil.get(position));
                            }
                            break;

                        case R.id.mn_add_bookmark:
                            if (mItemFileClickListener != null) {
                                mItemFileClickListener.onAddToBookmark(mListFileUtil.get(position));
                            }
                            break;

                        case R.id.mn_share:
                            if (mItemFileClickListener != null) {
                                mItemFileClickListener.onShareFile(mListFileUtil.get(position));
                            }
                            break;

                    }
                    return true;
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.setGravity(Gravity.END);
                }
                popup.show();
                return false;
            });*/
        }


    }

    public interface fileSearchAdapterListener{
        void onItemSearchClick(File file);
    }
}

