package biz.agbo.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.fragment.WebFragment;

/**
 * Created by Paco on 1/12/14.
 */
public class WebActivity extends ActionBarActivity {
    public static final String EXTRA_WINE = "biz.agbo.baccus.extra.activity.WINE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        FragmentManager fm = getSupportFragmentManager();

        WebFragment webFragment = (WebFragment)fm.findFragmentById(R.id.fragment_container);
        if (webFragment == null){
            Bundle arguments = new Bundle();
            arguments.putSerializable(WebFragment.ARG_WINE, getIntent().getSerializableExtra(EXTRA_WINE));

            webFragment = new WebFragment();
            webFragment.setArguments(arguments);

            fm.beginTransaction().add(R.id.fragment_container, webFragment).commit();
        }
    }

}
