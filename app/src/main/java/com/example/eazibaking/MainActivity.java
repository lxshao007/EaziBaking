package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.example.eazibaking.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding databinding;
    private boolean mTwoPane;
    private static SimpleIdlingResource idlingResource;

    @Nullable
    @VisibleForTesting
    public static IdlingResource getIdleResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIdleResource();
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mTwoPane = databinding.fragmentHomePlaceholderGrid == null ? false : true;
        if (savedInstanceState == null) {
            HomeFragment homeFragment = HomeFragment.newInstance(mTwoPane);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mTwoPane ? R.id.fragment_home_placeholder_grid : R.id.fragment_home_placeholder, homeFragment)
                    .commit();
        }
    }

}
