package com.jccsisc.controlbsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.model.CrearUser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearUsuarioActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private TextInputLayout tilUser, tilPassword, tilVerifyPsw;
    private TextInputEditText tietUser, tietPassword, tietVerifyPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        Toolbar toolbar = findViewById(R.id.toolbar);//para que funcione vete al manifest agregalo
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.registrate));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("DB_Users");

        tilUser       = findViewById(R.id.tilUser);
        tietUser      = findViewById(R.id.tieUser);
        tilPassword   = findViewById(R.id.tilPassword);
        tietPassword  = findViewById(R.id.tiePassword);
        tilVerifyPsw  = findViewById(R.id.tilVerifyPsw);
        tietVerifyPsw = findViewById(R.id.tieVerifyPsw);

        tietVerifyPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdIsValid2(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        registrarNuevoUser();
    }

    private void registrarNuevoUser() {
        String user     = tietUser.getEditableText().toString();
        String password = tietPassword.getEditableText().toString();
        String verifyPsw= tietVerifyPsw.getEditableText().toString();

        if(emailIsValid(user) & pwdIsValid(password) && isConnected()) {
                if(password.equals(verifyPsw)) {
                    mAuth.createUserWithEmailAndPassword(user, password)
                            .addOnCompleteListener(CrearUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    }else {
                                        Toast.makeText(CrearUsuarioActivity.this, "El usuario ya Existe!!", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });

                }else {
                    pwdIsValid2(verifyPsw);
//                    snackMessage("Las contraseñas no coinciden");
                }
        }

    }

    //detecta si ya inicio sesion
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser !=null) {

            String id = mAuth.getUid();
            String user = tietUser.getEditableText().toString();
            CrearUser crearUser = new CrearUser(id, user);

            myRef.child(id).setValue(crearUser);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }




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
            String pass = tietPassword.getEditableText().toString();
        if (validarContraSegura(pass).find()) {
            pwdMessage(getString(R.string.pwd_segura));
            return false;
        }

        pwdMessage();
        return true;
    }

    private boolean pwdIsValid2(CharSequence pwss) {

        if (TextUtils.isEmpty(pwss)) {
            pwdDMessage(getString(R.string.empty_field));
            return false;
        }

        pwdDMessage();
        return true;
    }


    //Validar Contraseña segura
    public Matcher validarContraSegura(String password)
    {
        Pattern patternClave = Pattern
                .compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\s{8,}$");
        Matcher matcherPassword = patternClave.matcher(password);
        return matcherPassword;
    }


    public void snackMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
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

    void pwdDMessage() {
        tilVerifyPsw.setError(null);
    }

    void pwdDMessage(String message) {
        tilVerifyPsw.setError(message);
    }
}
