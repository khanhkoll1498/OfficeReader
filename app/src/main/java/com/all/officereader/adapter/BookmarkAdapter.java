package com.all.officereader.adapter;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

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

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.FileViewHolder> {
    private long timeClick=0;
    private Context mContext;
    private String extension;
    private ItemFileClickListener mItemFileClickListener;
    private static final int CONTENT = 0;
    private static final int AD = 1;
    private List<File> mListFileUtil;

    public BookmarkAdapter(ArrayList<File> filePaths, Context _context, ItemFileClickListener mItemFileClickListener) {
        mListFileUtil = new ArrayList<>();
        mListFileUtil = filePaths;

        this.mContext = _context;
        this.mItemFileClickListener = mItemFileClickListener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View dataLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_file, viewGroup, false);

        return new FileViewHolder(dataLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, final int position) {

        final File file = mListFileUtil.get(position);
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

        holder.txtItemFileSearchName.setContentDescription(file.getName());
        holder.txtItemFileSearchName.setText(file.getName());
        SimpleDateFormat sdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat stime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        Date date = new Date(file.lastModified());
        holder.txtItemFileSearchDate.setText(mContext.getString(R.string.last_edit) + sdate.format(date) + ", " + stime.format(date));
        holder.txvItemFileSearchDetail.setText(FileUtility.convertFileSize(file.length()) + "");

       
        holder.imvItemFileSearchMoreChoose.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(mContext, holder.itemView);
            popup.getMenuInflater().inflate(R.menu.menu_popup_bookmark, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.mn_open:
                        if (mItemFileClickListener != null) {
                            mItemFileClickListener.onItemClick(mListFileUtil.get(position));
                        }
                        break;

//                    case R.id.mn_add_bookmark:
//                        if (mItemFileClickListener != null) {
//                            mItemFileClickListener.onRemoveBookmark(mListFileUtil.get(position));
//                        }
//                        break;

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popup.setGravity(Gravity.END);
            }
            popup.show();
        });
    }

  /*  private void setMarker(FileViewHolder holder, Drawable drawableResId, int colorFilter, int position) {
        holder.timeline.setMarker(drawableResId);
        holder.timeline.setStartLineColor(colorFilter, TimelineView.getTimeLineViewType(position, getItemCount()));
        holder.timeline.setEndLineColor(colorFilter, TimelineView.getTimeLineViewType(position, getItemCount()));
        // holder.cvBgFile.setCardBackgroundColor(ColorCustom.parseColor("#e7edf0"));
    }*/

    @Override
    public int getItemCount() {
        return mListFileUtil != null ? mListFileUtil.size() : 0;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvItemFileSearchIcon;
        private TextView txtItemFileSearchName;
        private TextView txtItemFileSearchDate;
        private TextView txvItemFileSearchDetail;
        private ImageView imvItemFileSearchMoreChoose;

        FileViewHolder(View itemView) {
            super(itemView);
            imvItemFileSearchIcon = itemView.findViewById(R.id.imv_item_file__file);
            txtItemFileSearchName = itemView.findViewById(R.id.txv_item_item_file__title);
            txtItemFileSearchDate = itemView.findViewById(R.id.txt_item_item_file___date);
            txvItemFileSearchDetail = itemView.findViewById(R.id.txv_item_item_file__detail);
            imvItemFileSearchMoreChoose = itemView.findViewById(R.id.imv_item_item_file__more__choose);


                itemView.setOnClickListener(view -> {

                    int position = getLayoutPosition();
                    if (mItemFileClickListener != null) {
                        mItemFileClickListener.onItemClick(mListFileUtil.get(position));
                    }
                });


            itemView.setOnLongClickListener(view -> {
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

//                        case R.id.mn_add_bookmark:
//                            if (mItemFileClickListener != null) {
//                                mItemFileClickListener.onAddToBookmark(mListFileUtil.get(position));
//                            }
//                            break;

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.setGravity(Gravity.END);
                }
                popup.show();
                return false;
            });
        }
    }
}
