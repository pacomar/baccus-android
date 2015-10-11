package biz.agbo.baccus.controller.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import biz.agbo.baccus.R;
import biz.agbo.baccus.adapter.WineryPageAdapter;
import biz.agbo.baccus.model.Wine;
import biz.agbo.baccus.model.Winery;

/**
 * Created by Paco on 14/12/14.
 */
public class WineryFragment extends Fragment implements ViewPager.OnPageChangeListener, ActionBar.TabListener {
    public static final String ARG_WINE_INDEX = "bix.agbo.baccus.controller.fragment.WINE_INDEX";
    public static final String ARG_SHOW_TABS = "bix.agbo.baccus.controller.fragment.SHOW_TABS";
    public static final String PREF_LAST_WINE = "lastWine";

    private static final int MENU_NEXT = 1;
    private static final int MENU_PREV = 2;

    private ViewPager mPager = null;
    private ActionBar mActionBar = null;
    private Winery mWinery = null;
    private ProgressDialog mProgressDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_winery, container, false);

        AsyncTask<Object, Integer, Winery> wineryDownloader = new AsyncTask<Object, Integer, Winery>() {
            @Override
            protected Winery doInBackground(Object... objects) {
                return Winery.getInstance();
            }

            @Override
            protected void onPostExecute(Winery winery) {
                loadUI(winery);
                mProgressDialog.dismiss();
            }
        };

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(R.string.download);
        if (!Winery.isInstanceAvaible()){
            mProgressDialog.show();
        }

        wineryDownloader.execute();

        return root;
    }

    private void loadUI(Winery winery){
        mWinery = winery;
        mPager = (ViewPager)getView().findViewById(R.id.pager);
        mPager.setAdapter(new WineryPageAdapter(getFragmentManager()));
        mPager.setOnPageChangeListener(this);


        mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();

        if (getArguments().getBoolean(ARG_SHOW_TABS)){
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            for (int i = 0; i < mWinery.getWineCount(); i++){
                mActionBar.addTab(mActionBar.newTab()
                        .setText(mWinery.getWine(i).getName())
                        .setTabListener(this));
            }
        }

        int wineIndex = getArguments().getInt(ARG_WINE_INDEX);
        mPager.setCurrentItem(wineIndex);
        updateActionBar(wineIndex);
    }

    private void updateActionBar(int index){
        Wine currentWine = mWinery.getWine(index);
        mActionBar.setTitle(currentWine.getName());
        mActionBar.setIcon(new BitmapDrawable(getResources(), currentWine.getPhoto(getActivity())));
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        updateActionBar(i);
        if (mActionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS){
            mActionBar.setSelectedNavigationItem(i);
        }

        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putInt(PREF_LAST_WINE, i)
                .commit();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mPager != null) {
            super.onCreateOptionsMenu(menu, inflater);
            MenuItem nextMenuItem = menu.add(Menu.NONE, MENU_NEXT, 1, R.string.next);
            MenuItemCompat.setShowAsAction(nextMenuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

            MenuItem prevMenuItem = menu.add(Menu.NONE, MENU_PREV, 0, R.string.previus);
            MenuItemCompat.setShowAsAction(prevMenuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

            int index = mPager.getCurrentItem();
            nextMenuItem.setEnabled(index < mWinery.getWineCount() - 1);
            prevMenuItem.setEnabled(index > 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_NEXT){
            if (mPager.getCurrentItem() < mWinery.getWineCount() - 1){
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            }
            return true;
        }else if (item.getItemId() == MENU_PREV){
            if (mPager.getCurrentItem() > 0){
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
            return true;
        }else if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void selectwine(int index){
        mPager.setCurrentItem(index);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getArguments().putInt(ARG_WINE_INDEX, mPager.getCurrentItem());
    }
}
