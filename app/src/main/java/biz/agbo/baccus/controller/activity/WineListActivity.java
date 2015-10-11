package biz.agbo.baccus.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;

import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.fragment.WineListFragment;
import biz.agbo.baccus.controller.fragment.WineryFragment;

/**
 * Created by Paco on 14/12/14.
 */
public class WineListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);
        FragmentManager fm = getSupportFragmentManager();

        if (findViewById(R.id.list) != null){
            Fragment listFragment = fm.findFragmentById(R.id.list);
            if (listFragment == null){
                listFragment = new WineListFragment();
                fm.beginTransaction().add(R.id.list, listFragment).commit();
            }
        }

        if (findViewById(R.id.winery) != null){
            WineryFragment wineryFragment = (WineryFragment)fm.findFragmentById(R.id.winery);
            if (wineryFragment == null){
                Bundle arguments = new Bundle();
                arguments.putInt(WineryFragment.ARG_WINE_INDEX, PreferenceManager.getDefaultSharedPreferences(this).getInt(WineryFragment.PREF_LAST_WINE, 0));
                arguments.putBoolean(WineryFragment.ARG_SHOW_TABS, false);
                wineryFragment = new WineryFragment();
                wineryFragment.setArguments(arguments);
                fm.beginTransaction().add(R.id.winery, wineryFragment).commit();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        WineryFragment wineryFragment = (WineryFragment) getSupportFragmentManager().findFragmentById(R.id.winery);
        if (wineryFragment != null){
            wineryFragment.selectwine(index);
        }else {
            Intent wineryIntent = new Intent(this, WineryActivity.class);
            wineryIntent.putExtra(WineryActivity.EXTRA_WINE_INDEX, index);
            startActivity(wineryIntent);
        }
    }
}
