package biz.agbo.baccus.controller.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.activity.SettingsActivity;

/**
 * Created by Paco on 14/12/14.
 */
public class SettingsFragment extends DialogFragment implements View.OnClickListener {
    public static final String ARG_WINE_IMAGE_SCALE_TYPE = "biz.agbo.baccus.fragment.WINE_IMAGE_SCALE_TYPE";
    public static final String PREF_IMAGE_SCALE_TYPE = "imageScaleType";
    private RadioGroup mOptions = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        mOptions = (RadioGroup)root.findViewById(R.id.scale_type_radios);

        if (getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_XY)){
            mOptions.check(R.id.fit_radio);
        }else{
            mOptions.check(R.id.center_radio);
        }

        Button saveButton = (Button)root.findViewById(R.id.save_button);
        Button cancelButton = (Button)root.findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.settings);
        return dialog;
    }

    public void save(View v){
        ImageView.ScaleType selectedScaleType = null;

        Intent config = new Intent();

        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();

        if(mOptions.getCheckedRadioButtonId() == R.id.fit_radio){
            selectedScaleType = ImageView.ScaleType.FIT_XY;
        }else{
            selectedScaleType = ImageView.ScaleType.FIT_CENTER;
        }

        config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, selectedScaleType);
        preferences.putString(PREF_IMAGE_SCALE_TYPE, selectedScaleType.toString());

        preferences.commit();

        Intent result = new Intent();
        result.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, selectedScaleType);

        if (getTargetFragment() != null){
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
            dismiss();
        }else{
            getActivity().setResult(Activity.RESULT_OK, config);

            getActivity().finish();
        }
    }

    public void cancel(View v){
        if (getTargetFragment() != null){
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
            dismiss();
        }else{
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()){
            case R.id.save_button:
                save(button);
                break;
            case R.id.cancel_button:
                cancel(button);
                break;
        }
    }
}
