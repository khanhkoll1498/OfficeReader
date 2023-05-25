package com.all.officereader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.all.officereader.R;

public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.MoreApp> {
    private Context mContext;
    private moreAppAdapterListener mMoreAppAdapterListener;
    //private ArrayList<MoreAppModel> mMoreAppModels;

    public MoreAppAdapter(/*ArrayList<MoreAppModel> moreAppModelArrayList, */Context _context, moreAppAdapterListener moreAppAdapterListener) {

        //mMoreAppModels = new ArrayList<>();
        //mMoreAppModels = moreAppModelArrayList;
        this.mContext = _context;
        this.mMoreAppAdapterListener = moreAppAdapterListener;
    }

    @NonNull
    @Override
    public MoreApp onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View dataLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_more_app, viewGroup, false);

        return new MoreApp(dataLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreApp holder, final int position) {

        /*final MoreAppModel moreAppModel = mMoreAppModels.get(position);

        if (!AppUtil.isAppInstalled(mContext, moreAppModel.getPkm())) {
            Glide.with(mContext).load(moreAppModel.getLinkIcon())
                    .dontAnimate()
                    .placeholder(R.drawable.ic_folder_item)
                    .into(holder.imvItemMoreAppIcon);

            holder.txvItemMoreAppName.setText(moreAppModel.getName());
            holder.txvItemMoreAppRate.setText(moreAppModel.getmNumberRate());
            holder.txvItemMoreAppDownloaded.setText(moreAppModel.getmTotalDownload());
        }


        holder.btnItemMoreAppInstall.setOnClickListener(v -> {
            mMoreAppAdapterListener.onClickMoreApp(moreAppModel);
        });*/
    }

    @Override
    public int getItemCount() {
        return  0;
    }

    public class MoreApp extends RecyclerView.ViewHolder {
        private ImageView imvItemMoreAppIcon;
        private TextView txvItemMoreAppName;
        private TextView txvItemMoreAppRate;
        private TextView txvItemMoreAppDownloaded;
        private TextView btnItemMoreAppInstall;

        MoreApp(View itemView) {
            super(itemView);
            imvItemMoreAppIcon = itemView.findViewById( R.id.imv_item_more_app__icon );
            txvItemMoreAppName =itemView.findViewById( R.id.txv_item_more_app__name );
            txvItemMoreAppRate = itemView.findViewById( R.id.txv_item_more_app__rate );
            txvItemMoreAppDownloaded = itemView.findViewById( R.id.txv_item_more_app__downloaded );
            btnItemMoreAppInstall =itemView.findViewById( R.id.btn_item_more_app__install );

            //ClickShrinkEffectKt.applyClickShrink(itemView);
            itemView.setOnClickListener(view -> {
                int position = getLayoutPosition();
                /*if (mMoreAppModels != null) {
                    mMoreAppAdapterListener.onClickMoreApp(mMoreAppModels.get(position));
                }*/
            });
        }
    }

    public interface moreAppAdapterListener{
        //void onClickMoreApp(MoreAppModel moreAppModel);
    }
}
