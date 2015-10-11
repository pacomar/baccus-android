package biz.agbo.baccus.controller.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;
import biz.agbo.baccus.model.Winery;

/**
 * Created by Paco on 14/12/14.
 */
public class WineListFragment extends Fragment {
    private ProgressDialog mProgressDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_wine_list, container, false);

        AsyncTask<Object, Integer, Winery> wineryDownloader = new AsyncTask<Object, Integer, Winery>() {
            @Override
            protected Winery doInBackground(Object... objects) {
                return Winery.getInstance();
            }

            @Override
            protected void onPostExecute(Winery winery) {
                ListView listView = (ListView)getView().findViewById(android.R.id.list);
                ArrayAdapter<Wine> adapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());
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
}
