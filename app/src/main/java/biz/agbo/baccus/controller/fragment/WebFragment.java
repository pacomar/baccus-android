package biz.agbo.baccus.controller.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;

/**
 * Created by Paco on 14/12/14.
 */
public class WebFragment extends Fragment {
    private static final String STATE_URL = "url";
    public static final String ARG_WINE = "biz.agbo.baccus.extra.WINE";
    private WebView mBrowser = null;
    private ProgressBar mLoading = null;
    private ActionBar mActionBar = null;
    private Wine mWine = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_web, container, false);
        mWine = (Wine) getArguments().getSerializable(ARG_WINE);

        //accedo a las vistas
        mBrowser = (WebView)root.findViewById(R.id.browser);
        mLoading = (ProgressBar)root.findViewById(R.id.loading);

        // Recibimos el modelo


        //Configurar el browser
        mBrowser.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLoading.setVisibility(View.GONE);
            }
        });

        mBrowser.getSettings().setBuiltInZoomControls(true);
        mBrowser.loadUrl(mWine.getWineCompanyWeb());

        mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(mWine.getName());
        mActionBar.setIcon(new BitmapDrawable(getResources(), mWine.getPhoto(getActivity())));

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_URL, mBrowser.getUrl());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.web, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_reload){
            mBrowser.reload();
            return true;
        }else if (item.getItemId() == android.R.id.home){
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
