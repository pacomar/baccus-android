package biz.agbo.baccus.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.activity.SettingsActivity;
import biz.agbo.baccus.controller.activity.WebActivity;
import biz.agbo.baccus.model.Wine;

/**
 * Created by Paco on 14/12/14.
 */
public class WineFragment extends Fragment{
    // Finals
    public static final int SETTINGS_REQUEST = 1;
    public static final String ARG_WINE = "biz.agbo.baccus.controller.WINE";
    private static final String STATE_WINE_IMAGE_SCALE_TYPE = "ImageState";

    // Modelos
    private Wine mWine = null;

    // Vistas
    private ImageView mWineImage = null;
    private TextView mWineNameText = null;
    private TextView mWineTypeText = null;
    private TextView mWineOriginText = null;
    private RatingBar mWineRatingBar = null;
    private TextView mWineCompanyText = null;
    private TextView mWineNotesText = null;
    private ViewGroup mWineGrapeContainer = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_wine, container, false);

        mWine = (Wine)getArguments().getSerializable(ARG_WINE);

        // Accedemos a las vistas desde este controlador
        mWineImage = (ImageView)root.findViewById(R.id.wine_image);
        mWineNameText = (TextView)root.findViewById(R.id.wine_name);
        mWineTypeText = (TextView)root.findViewById(R.id.wine_type);
        mWineOriginText = (TextView)root.findViewById(R.id.wine_origin);
        mWineRatingBar = (RatingBar)root.findViewById(R.id.wine_rating);
        mWineCompanyText = (TextView)root.findViewById(R.id.wine_company);
        mWineNotesText = (TextView)root.findViewById(R.id.wine_notes);
        mWineGrapeContainer = (ViewGroup)root.findViewById(R.id.wine_grapes_container);

        ImageButton goToWebButton = (ImageButton)root.findViewById(R.id.go_to_web_button);
        goToWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(getActivity(), WebActivity.class);
                webIntent.putExtra(WebActivity.EXTRA_WINE, mWine);
                startActivity(webIntent);
            }
        });

        // Damos valor a las vistas con el modelo
        mWineImage.setImageBitmap(mWine.getPhoto(getActivity()));
        mWineNameText.setText(mWine.getName());
        mWineTypeText.setText(mWine.getType());
        mWineOriginText.setText(mWine.getOrigin());
        mWineRatingBar.setRating(mWine.getRating());
        mWineCompanyText.setText(mWine.getWineCompanyName());
        mWineNotesText.setText(mWine.getNotes());

        //Creamos la lista de uvas
        for (int i = 0; i < 1; i++){
            TextView grapeText = new TextView(getActivity());
            grapeText.setText(mWine.getGrape(i));
            grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mWineGrapeContainer.addView(grapeText);
        }

        if (savedInstanceState!= null && savedInstanceState.containsKey(STATE_WINE_IMAGE_SCALE_TYPE)){
            mWineImage.setScaleType((ImageView.ScaleType)savedInstanceState.getSerializable(STATE_WINE_IMAGE_SCALE_TYPE));
        }

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wine, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings){
            SettingsFragment settings = new SettingsFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
            settings.setArguments(arguments);
            settings.setTargetFragment(this, SETTINGS_REQUEST);
            settings.show(getFragmentManager(), null);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST && resultCode == Activity.RESULT_OK){
            ImageView.ScaleType scaleType = (ImageView.ScaleType) data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
            mWineImage.setScaleType(scaleType);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).contains(SettingsFragment.PREF_IMAGE_SCALE_TYPE)){
            mWineImage.setScaleType(ImageView.ScaleType.valueOf(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(SettingsFragment.PREF_IMAGE_SCALE_TYPE, ImageView.ScaleType.FIT_XY.toString())));
        }
    }
}
