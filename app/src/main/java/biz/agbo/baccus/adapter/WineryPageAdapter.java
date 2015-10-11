package biz.agbo.baccus.adapter;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import biz.agbo.baccus.controller.fragment.WineFragment;
import biz.agbo.baccus.model.Winery;

/**
 * Created by Paco on 14/12/14.
 */
public class WineryPageAdapter extends FragmentPagerAdapter{
    private Winery mWinery = null;

    public WineryPageAdapter(FragmentManager manager){
        super(manager);
        mWinery = Winery.getInstance();
    }

    @Override
    public Fragment getItem(int index) {
        WineFragment fragment = new WineFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE, mWinery.getWine(index));
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public int getCount() {
        return mWinery.getWineCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return mWinery.getWine(position).getName();
    }
}
