package biz.agbo.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.fragment.WineryFragment;

/**
 * Created by Paco on 14/12/14.
 */
public class WineryActivity extends ActionBarActivity {
    public static final String EXTRA_WINE_INDEX = "biz.agbo.baccus.controller.activity.WINE_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = new WineryFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(WineryFragment.ARG_WINE_INDEX, getIntent().getIntExtra(EXTRA_WINE_INDEX, 0));
            arguments.putBoolean(WineryFragment.ARG_SHOW_TABS, true);
            fragment.setArguments(arguments);
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
