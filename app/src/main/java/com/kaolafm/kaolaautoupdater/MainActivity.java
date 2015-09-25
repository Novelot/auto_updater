package com.kaolafm.kaolaautoupdater;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * 主界面
 *
 * @author V
 */
public class MainActivity extends BaseActivity {
    /* 列表*/
    private RecyclerView recyclerView;
    /*更新全部*/
    private TextView tvUpdateAll;
    /*列表为空时*/
    private TextView empty;
    private UpdateInfoAdapter mAdapter;

    private void assignViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvUpdateAll = (TextView) findViewById(R.id.tvUpdataAll);
        empty = (TextView) findViewById(R.id.empty);
        //
        tvUpdateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updateAllApps();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        initListView();
        new Thread() {
            @Override
            public void run() {
                mPresenter.getAllUpdateInfos();
            }
        }.start();

    }

    private void initListView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);//如果为true,会自动滚动到末尾
        mAdapter = new UpdateInfoAdapter(this);
        mAdapter.setListener(new UpdateInfoAdapter.ItemListener() {
            @Override
            public void onItemClicked(View v, UpdateInfo data, int i) {
                mPresenter.updateThisApp(i,data);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void updateListView(List<UpdateInfo> result) {
        if (result == null) {
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            mAdapter.update(null);
        } else {
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.update(result);
        }
    }

    @Override
    public void updateItemProgress(int downloadId, long progress, long totalSize) {
        mAdapter.updateItem(downloadId, progress, totalSize);
    }

    /**************************************************************************/


    /**************************************************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
//        refWatcher.watch(mPresenter);
    }
}
