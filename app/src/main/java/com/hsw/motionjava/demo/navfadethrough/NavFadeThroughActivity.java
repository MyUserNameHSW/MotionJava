package com.hsw.motionjava.demo.navfadethrough;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.hsw.motionjava.R;
import com.hsw.motionjava.edge.EdgeToEdge;

/**
 * @author heshuai
 * created on: 2020/6/28 5:11 PM
 * description:
 */
public class NavFadeThroughActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_fade_through_activity);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.nav_host));
    }
}
