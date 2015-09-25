package com.kaolafm.kaolaautoupdater;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 界面所需数据
 * Created by V on 2015/9/23.
 */
public class ViewDataManager {
    /**
     * 升级信息
     */
    private static List<UpdateInfo> sUpdateInfos = new ArrayList<UpdateInfo>();

    public void update(List<UpdateInfo> result) {
        sUpdateInfos.clear();
        sUpdateInfos.addAll(new ArrayList<UpdateInfo>(result));
    }

    public List<UpdateInfo> list() {
        return sUpdateInfos;
    }
}
