package com.hsw.motionjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.demolist.DemoListFragment;
import com.hsw.motionjava.edge.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configure edge-to-edge display.
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.main));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);

        // Set up the fragment.
        if (savedInstanceState == null) {
            addFragment(new DemoListFragment());
        }
    }

    protected void addFragment(Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
