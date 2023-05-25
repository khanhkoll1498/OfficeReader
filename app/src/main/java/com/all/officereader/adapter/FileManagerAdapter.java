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
import com.all.officereader.model.FileListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FileManagerAdapter extends RecyclerView.Adapter<FileManagerAdapter.VideoHolder> {
    public static final String TAG = FileManagerAdapter.class.getName();
    private ArrayList<FileListItem> arrData = new ArrayList<>();
    private Context mContext;
    private onFileManagerAdapterInterface mOnFileManagerAdapterInterface;

    public FileManagerAdapter(ArrayList<FileListItem> arrData, Context context, onFileManagerAdapterInterface onFileManagerAdapterInterface) {
        this.arrData.clear();
        this.arrData.addAll(arrData);
        mContext = context;
        mOnFileManagerAdapterInterface = onFileManagerAdapterInterface;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_file_manager_list, viewGroup, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder videoHolder, int i) {
        final FileListItem fileListItem = arrData.get(i);

        if (fileListItem.isDirectory()) {
            videoHolder.imvItemFileManagerFile.setImageResource(R.drawable.ic_folder_item);
        } else {
            switch (FileUtility.fileType(fileListItem)) {

                case 6:
                    Glide.with(mContext).load(fileListItem.getLocation())
                            .placeholder(R.drawable.ic_folder_item)
                            .into(videoHolder.imvItemFileManagerFile);
                    break;

                case 7:
                    videoHolder.imvItemFileManagerFile.setImageResource(R.drawable.menu_pdf);
                    break;

                case 8:
                    videoHolder.imvItemFileManagerFile.setImageResource(R.drawable.menu_ppt);
                    break;

                case 11:
                    videoHolder.imvItemFileManagerFile.setImageResource(R.drawable.menu_word);
                    break;

                case 5:
                    videoHolder.imvItemFileManagerFile.setImageResource(R.drawable.menu_excel);
                    break;

                default:
                    videoHolder.imvItemFileManagerFile.setImageResource(R.drawable.ic_folder_item);
                    break;
            }
        }

        videoHolder.imvItemFileManagerFile.setContentDescription(fileListItem.getFilename());
        videoHolder.txvItemFileManagerTitle.setText(fileListItem.getFilename());
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        Date date = new Date(fileListItem.getTime());
        if (i == 0 && fileListItem.getFilename().startsWith(mContext.getString(R.string.label_parent_dir))) {
            videoHolder.txvItemFileManagerDetail.setText(R.string.label_parent_directory);
        } else {
            videoHolder.txtItemFileManagerDate.setText(mContext.getString(R.string.last_edit) + simpleDate.format(date) + ", " + simpleTime.format(date));
            if (fileListItem.isDirectory()) {
                videoHolder.txvItemFileManagerDetail.setText(FileUtility.formatFileSize(FileUtility.getAllFileSize(fileListItem.getLocation()), false) + "");
            } else {
                videoHolder.txvItemFileManagerDetail.setText(FileUtility.convertFileSize(fileListItem.getLocation().length()) + "");
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public void updateData(ArrayList<FileListItem> fileListItems) {
        arrData.clear();
        arrData.addAll(fileListItems);
        notifyDataSetChanged();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView imvItemFileManagerFile;
        private TextView txvItemFileManagerTitle;
        private TextView txtItemFileManagerDate;
        private TextView txvItemFileManagerDetail;

        VideoHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                int position = getLayoutPosition();
                mOnFileManagerAdapterInterface.onClickItem(arrData.get(position));

            });

            imvItemFileManagerFile = itemView.findViewById(R.id.imv_item_file_manager_file);
            txvItemFileManagerTitle = itemView.findViewById(R.id.txv_item_file_manager_title);
            txtItemFileManagerDate = itemView.findViewById(R.id.txt_item_file_manager__date);
            txvItemFileManagerDetail = itemView.findViewById(R.id.txv_item_file_manager__detail);
        }

    }

    public interface onFileManagerAdapterInterface {
        void onClickItem(FileListItem fileListItem);
    }
}
