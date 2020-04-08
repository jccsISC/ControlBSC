package com.jccsisc.controlbsc.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.jccsisc.controlbsc.R;

public class ChargingFragment extends DialogFragment {

    private TashieLoader tashieLoader;
    private Activity activity;

    public ChargingFragment() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return dialogFragment();
    }

    private AlertDialog dialogFragment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_charging, null);
        builder.setView(v);


        tashieLoader = v.findViewById(R.id.tashieLoader);

        //mostramos la animacion TashieLoader mientras carga
        TashieLoader tashie;
        tashie = new TashieLoader(
                getActivity(), 5,
                30, 10,
                ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity) {
            this.activity = (Activity) context;
        }else {
            throw new RuntimeException(context.toString());
        }
    }
}
