package biz.agbo.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import biz.agbo.baccus.activity.FragmentControllerActivity;
import biz.agbo.baccus.controller.fragment.WineFragment;


public class WineActivity extends FragmentControllerActivity {
    public static final String EXTRA_WINE = "biz.agbo.baccus.activity.WINE";

    @Override
    public Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE, getIntent().getSerializableExtra(EXTRA_WINE));

        WineFragment fragment = new WineFragment();
        fragment.setArguments(arguments);

        return fragment;
    }
}
