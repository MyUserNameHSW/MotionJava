package com.hsw.motionjava.demolist;

import android.content.ComponentName;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/24 11:39 AM
 * description:
 */
public class DemoBean {

    public final static String CATEGORY = "com.hsw.motionjava.intent.category.DEMO";
    public final static String META_DATA_DESCRIPTION = "com.hsw.motionjava.demolist.DESCRIPTION";
    public final static String META_DATA_APIS = "com.hsw.motionjava.demolist.APIS";

    String packageName;

    String name;

    String label;

    String description;

    List<String> apis;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getApis() {
        return apis;
    }

    public void setApis(List<String> apis) {
        this.apis = apis;
    }

    public Intent toIntent() {
        return new Intent(Intent.ACTION_MAIN)
                .addCategory(CATEGORY)
                .setComponent(new ComponentName(packageName, name));
    }

}
