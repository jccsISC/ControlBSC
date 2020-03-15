package com.jccsisc.controlbsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.R.id;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth; // para conectarnos con el usuario de la db

    private TextInputLayout tilUser, tilPassword;
    private EditText tieUser, tiePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        tilUser     = findViewById(R.id.tilUser);
        tieUser     = findViewById(R.id.tieUser);
        tilPassword = findViewById(R.id.tilPassword);
        tiePassword = findViewById(R.id.tiePassword);

        tieUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailIsValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tiePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdIsValid(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login_user();
                break;
            case R.id.btnSignIn:
                login_Create();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //comprobamos si el usuario ha iniciado sesion y actualizamos la IU
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //detecta si ya inicio sesion
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser !=null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login_user() {
        String email = tieUser.getEditableText().toString().trim(); //trim para eliminar espacios al inicio o al final de la caja
        String password  = tiePassword.getEditableText().toString().trim();


        if(emailIsValid(email) & pwdIsValid(password) && isConnected()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(LoginActivity.this, "Verifica tu internet o usuario", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }else {
            snackMessage("Algo sucedió");
        }
    }

    private void login_Create() {
        startActivity(new Intent(LoginActivity.this, CrearUsuarioActivity.class));
    }

    //para saber si esta conectado al wifi
    boolean isConnected() {
        boolean wifiConnected = false, mobileConnected = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(manager).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            //Aqui podemos ver que hacer con cada tipo de conexion
        }else {
            snackMessage(getString(R.string.noInternet));
        }
        return wifiConnected || mobileConnected;
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

    private boolean pwdIsValid(CharSequence pwd) {

        if (TextUtils.isEmpty(pwd)) {
            pwdMessage(getString(R.string.empty_field));
            return false;
        }

        if (pwd.length() < 6) {
            pwdMessage(getString(R.string.pwd_length));
            return false;
        }

        pwdMessage();
        return true;
    }

    public void snackMessage(String message) {
        Snackbar.make(findViewById(id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    void mailMessage() {
        tilUser.setError(null);
    }

    void mailMessage(String message) {
        tilUser.setError(message);
    }

    void pwdMessage() {
        tilPassword.setError(null);
    }

    void pwdMessage(String message) {
        tilPassword.setError(message);
    }

}
