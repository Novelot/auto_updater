package com.kaolafm.kaolaautoupdater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;
import com.kaolafm.kaolaautoupdater.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by V on 2015/9/23.
 */
public class UpdateInfoAdapter extends RecyclerView.Adapter<UpdateInfoAdapter.ViewHolder> {

    private Context context;
    private static List<UpdateInfo> datas = new ArrayList<UpdateInfo>();

    public UpdateInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(context, R.layout.item_adapter_update_infos, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final UpdateInfo item = datas.get(i);
        viewHolder.ivIcon.setImageResource(item.icon);
        viewHolder.tvAppName.setText(item.appName);
        viewHolder.tvAppVersion.setText(item.version);
        if (item.progress > 0.0f && item.progress < 1.0f) {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.btnUpdate.setVisibility(View.GONE);
            //
            viewHolder.progressBar.setMax(100);
            viewHolder.progressBar.setProgress((int) (100 * item.progress));
        } else {
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.btnUpdate.setVisibility(View.VISIBLE);
            //
            viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClicked(v, item,i);
                    }
                }
            });
        }
    }

    /**
     * 更新数据
     *
     * @param infos
     */
    public void update(List<UpdateInfo> infos) {
        if (infos == null) {
            datas.clear();
        } else {
            datas.clear();
            datas.addAll(infos);
        }

        notifyDataSetChanged();
    }

    public void updateItem(int downloadId, long progress, long totalSize) {
        datas.get(downloadId).progress = (float) progress / totalSize;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vRoot;
        ImageView ivIcon;
        TextView tvAppName;
        TextView tvAppVersion;
        ProgressBar progressBar;
        Button btnUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            vRoot = itemView;
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvAppName = (TextView) itemView.findViewById(R.id.tvAppName);
            tvAppVersion = (TextView) itemView.findViewById(R.id.tvAppVersion);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            btnUpdate = (Button) itemView.findViewById(R.id.btnUpdate);
        }
    }

    private ItemListener mListener;

    public void setListener(ItemListener l) {
        mListener = l;
    }

    public interface ItemListener {

        void onItemClicked(View v,UpdateInfo data, int i);
    }
}
