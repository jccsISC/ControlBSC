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

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;


public class ModifyProductFragment extends DialogFragment implements View.OnClickListener {

    private Activity activity;
    private ImageButton btnExit;
    private Button btnModificar, btnEliminar;
    private TextInputLayout tilName;
    private TextInputEditText tieName;
    private FirebaseAuth mAuth;
    private String name, idKey, nameE;
    private TextView txtNameModify;
    private TashieLoader tashieLoader;

    public ModifyProductFragment() { }

    public ModifyProductFragment(String idKey, String name) {
        this.idKey = idKey;
        this.name  = name;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return dialogModifyProduct();
    }

    private AlertDialog dialogModifyProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_modify_product, null);
        builder.setView(v);

        btnExit       = v.findViewById(R.id.btnExit);
        tilName       = v.findViewById(R.id.tilNameProduct);
        tieName       = v.findViewById(R.id.tieNameProduct);
        txtNameModify = v.findViewById(R.id.txtNameProductoModificar);
        btnModificar  = v.findViewById(R.id.btnModify);
        btnEliminar   = v.findViewById(R.id.btnEliminar);

        txtNameModify.setText(name);
        nameE = name;

        mAuth    = FirebaseAuth.getInstance();

        tashieLoader = v.findViewById(R.id.tashieLoader);

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

        tieName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameIsValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnExit.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                dismiss();
                break;
            case R.id.btnModify:
                name = tieName.getEditableText().toString();
                if(nameIsValid(name)) {
                    tashieLoader.setVisibility(View.VISIBLE);
                    modificarProducto(idKey, name);
                }
                break;
            case R.id.btnEliminar:
                eliminarProducto(idKey);
                break;
        }
    }

    public void modificarProducto(String idKey, final String name) {
        NodosFirebase.myRef.child(idKey).child("name")
                .setValue(name.toUpperCase())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mtoast("Se modificó: "+nameE+" por "+name.toUpperCase()+" correctamente");
                        dismiss();
                    }
                });
    }

    private void eliminarProducto(String idKey) {
        NodosFirebase.myRef.child(idKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid) {
                mtoast("Se eliminó ( "+ name+ " ) correctamente");
                dismiss();
            }
        });
    }


    public void mtoast(String msj) {
        Toast toast = Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

    private boolean nameIsValid(CharSequence name) {

        if (TextUtils.isEmpty(name)) {
            mailMessage(getString(R.string.empty_field));
            return false;
        }

        mailMessage();
        return true;
    }

    void mailMessage() {
        tilName.setError(null);
    }

    void mailMessage(String message) {
        tilName.setError(message);
    }
}
