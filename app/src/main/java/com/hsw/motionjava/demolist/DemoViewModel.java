package com.hsw.motionjava.demolist;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/24 1:43 PM
 * description:
 */
public class DemoViewModel extends AndroidViewModel {
    private MutableLiveData _demos = new MutableLiveData<List<DemoBean>>();
    public LiveData<List<DemoBean>> demos = _demos;

    public DemoViewModel(@NonNull Application application) {
        super(application);
        PackageManager packageManager = getApplication().getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(
                new Intent(Intent.ACTION_MAIN).addCategory(DemoBean.CATEGORY),
                PackageManager.GET_META_DATA
        );
        Resources resources = application.getResources();

        List<DemoBean> demoBeanList = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfoList) {
            DemoBean demoBean = new DemoBean();
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Bundle metaData = activityInfo.metaData;
            int apisId = metaData.getInt(DemoBean.META_DATA_APIS, 0);
            demoBean.setPackageName(activityInfo.packageName);
            demoBean.setName(activityInfo.name);
            demoBean.setLabel(activityInfo.loadLabel(packageManager).toString());
            demoBean.setDescription(metaData.getString(DemoBean.META_DATA_DESCRIPTION));
            demoBean.setApis(Arrays.asList(resources.getStringArray(apisId)));
            demoBeanList.add(demoBean);
        }
        _demos.setValue(demoBeanList);
    }
}
