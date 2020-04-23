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
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jccsisc.controlbsc.R;

public class ModifyCajaFragment extends DialogFragment implements View.OnClickListener {

    private Activity activity;
    private ImageButton btnExit;
    private Button btnModificar, btnEliminar;
    private TextInputLayout tilPesoCaja;
    private TextInputEditText tiePesoCaja;
    private String pesoCaja, position;
    private TextView txtPesoCaja;
    private TashieLoader tashieLoader;

    public ModifyCajaFragment() { }

//    public ModifyCajaFragment(String position, String pesoCaja) {
//        this.position = position;
//        this.pesoCaja = pesoCaja;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return dialogModifyCaja();
    }

    private AlertDialog dialogModifyCaja() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_modify_caja, null);
        builder.setView(v);

        btnExit      = v.findViewById(R.id.btnExit);
        tilPesoCaja  = v.findViewById(R.id.tilPesoCaja);
        tiePesoCaja  = v.findViewById(R.id.tiePesoCaja);
//        txtPesoCaja  = v.findViewById(R.id.txtPesoCaja);
        btnModificar = v.findViewById(R.id.btnModifyCaja);
        btnEliminar  = v.findViewById(R.id.btnEliminar);
        tashieLoader = v.findViewById(R.id.tashieLoader);

        thasieLoader();

//        txtPesoCaja.setText("25.5");

        tiePesoCaja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pesoValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnExit.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnModificar.setOnClickListener(this);

        return builder.create();
    }

    private void thasieLoader() {
        //mostrar la animacion de cargando
        TashieLoader tashie;
        tashie = new TashieLoader(
                getActivity(), 5,
                30, 10,
                ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());
        tashieLoader.addView(tashie);
    }

    private boolean pesoValid(CharSequence peso) {
        if (TextUtils.isEmpty(peso)) {
            pesoMessage(getString(R.string.empty_field));
            return false;
        }

        pesoMessage();
        return true;
    }

    void pesoMessage() { tilPesoCaja.setError(null);}
    void pesoMessage(String message) { tilPesoCaja.setError(message);}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                dismiss();
                break;
            case R.id.btnModifyCaja:
                Toast.makeText(getActivity(), "Modificado",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnEliminar:
                Toast.makeText(getActivity(), "Eliminado",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
