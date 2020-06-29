package com.hsw.motionjava.demo.sharedelement;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hsw.motionjava.R;
import com.hsw.motionjava.edge.EdgeToEdge;

/**
 * @author heshuai
 * created on: 2020/6/29 9:46 AM
 * description:
 */
public class SharedElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_element_activity);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.nav_host));
    }

}
