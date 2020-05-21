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
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

public class ForgetPasswordFragment extends DialogFragment implements View.OnClickListener {

    private String email;
    private Button btnSend;
    private Activity activity;
    private ImageButton btnExit;
    private TextInputLayout tilEmail;
    private TextInputEditText tieEmail;
    private TashieLoader tashieLoader;

    public ForgetPasswordFragment() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return dialogForget();
    }

    private AlertDialog dialogForget() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_forget_password, null);
        builder.setView(v);

        btnExit  = v.findViewById(R.id.btnExit);
        tilEmail = v.findViewById(R.id.tilEmail);
        tieEmail = v.findViewById(R.id.tieEmail);
        btnSend  = v.findViewById(R.id.btnSend);

        NodosFirebase.mAuth    = FirebaseAuth.getInstance();

        tashieLoader = v.findViewById(R.id.tashieLoader);
        showTashieLoader();

        tieEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { emailIsValid(s); }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnExit.setOnClickListener(this);
        btnSend.setOnClickListener(this);

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
            case R.id.btnSend:
                email = tieEmail.getEditableText().toString();
                if(emailIsValid(email)) {
                    tashieLoader.setVisibility(View.VISIBLE);
                    resetPassword();
                }
                break;
        }
    }

    private void resetPassword() {
        NodosFirebase.mAuth.setLanguageCode("es");//idioma en que recibira el correo
        NodosFirebase.mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    mToast(getString(R.string.restablece_text));
                }else {
                    mToast(getString(R.string.error_restablecer));
                }
                dismiss(); //cerramos el dialog
            }
        });
    }


    private void mToast(String msj) {
        Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
    }

    private boolean emailIsValid(CharSequence email) {

        if (TextUtils.isEmpty(email)) {
            mailMessage(getString(R.string.empty_field));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailMessage(getString(R.string.email_invalid));
            return false;
        }

        mailMessage();
        return true;
    }

    private void showTashieLoader() {
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

    void mailMessage() {
        tilEmail.setError(null);
    }

    void mailMessage(String message) {
        tilEmail.setError(message);
    }
}
