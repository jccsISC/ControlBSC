package com.jccsisc.controlbsc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.dialogs.ChargingFragment;
import com.jccsisc.controlbsc.model.CrearUser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearUsuarioActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private int sonido; //valor paara el sonido
    private SoundPool sp; //para reproducir el sonido
    private ChargingFragment chargingFragment = new ChargingFragment();
    private TextInputLayout tilName, tilLastName, tilEmail, tilPassword, tilVerifyPsw;
    private TextInputEditText tieName, tieLastName, tietEmail, tietPassword, tietVerifyPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        myToolbar(getString(R.string.registrate));

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonido = sp.load(this, R.raw.cuchillo_dos, 1);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.db_User));

        tilName       = findViewById(R.id.tilName);
        tieName       = findViewById(R.id.tieName);
        tilLastName   = findViewById(R.id.tilLastName);
        tieLastName   = findViewById(R.id.tieLastName);
        tilEmail = findViewById(R.id.tilEmail);
        tietEmail = findViewById(R.id.tieEmail);
        tilPassword   = findViewById(R.id.tilPassword);
        tietPassword  = findViewById(R.id.tiePassword);
        tilVerifyPsw  = findViewById(R.id.tilVerifyPsw);
        tietVerifyPsw = findViewById(R.id.tieVerifyPsw);

        tieName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tieLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastNameValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tietEmail.addTextChangedListener(new TextWatcher() {
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

        tietPassword.addTextChangedListener(new TextWatcher() {
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
        final String name = tieName.getEditableText().toString();
        String lastName   = tieLastName.getEditableText().toString();
        String email      = tietEmail.getEditableText().toString();
        String password   = tietPassword.getEditableText().toString();
        String verifyPsw  = tietVerifyPsw.getEditableText().toString();

        if(nameValid(name) & lastNameValid(lastName) & emailIsValid(email) & pwdIsValid(password) & pwdIsValid2(verifyPsw) && isConnected()) {
            if(verifyPsw.equals(password)) {
                chargingFragment.show(getSupportFragmentManager(), "dialogCharging");
                sp.play(sonido, 3, 3, 1, 0, 0); //reproducimos el sonido
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CrearUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                    Toast.makeText(getApplicationContext(),"Bienvenido " + name,Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(CrearUsuarioActivity.this, getString(R.string.exist), Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }else {
                snackMessage(getString(R.string.no_equals));
            }
        }
    }

    //detecta si ya inicio sesion
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser !=null) {
            String id = mAuth.getUid();
            String name      = tieName.getEditableText().toString().toUpperCase();
            String lastName  = tieLastName.getEditableText().toString().toUpperCase();
            String email = tietEmail.getEditableText().toString();
            CrearUser crearUser = new CrearUser(id, name, lastName, email);

            myRef.child(id).setValue(crearUser);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Animatoo.animateZoom(CrearUsuarioActivity.this);
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

    private boolean nameValid(CharSequence name) {

        if (TextUtils.isEmpty(name)) {
            nameMessage(getString(R.string.empty_field));
            return false;
        }

        if (name.length()<3) {
            nameMessage(getString(R.string.hint_name));
            return false;
        }

        nameMessage();
        return true;
    }

    private boolean lastNameValid(CharSequence lastName) {

        if (TextUtils.isEmpty(lastName)) {
            lastNameMessage(getString(R.string.empty_field));
            return false;
        }

        if (lastName.length()<5) {
            lastNameMessage(getString(R.string.insert_last_name));
            return false;
        }

        lastNameMessage();
        return true;
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


        String pass = tietPassword.getEditableText().toString();
        if (validarContraSegura(pass).find()) {
            pwdMessage(getString(R.string.pwd_segura));
            return false;
        }

        pwdMessage();
        return true;
    }

    private boolean pwdIsValid2(CharSequence pwssTwo) {

        String pwsOne = tietPassword.getEditableText().toString();

        if (TextUtils.isEmpty(pwssTwo)) {
            pwdDMessage(getString(R.string.empty_field));
            return false;
        }

        if(pwssTwo.length() < pwsOne.length()) {
            pwdDMessage(getString(R.string.no_equals));
            return false;
        }

//        if(!pwssTwo.equals(pwsOne)) {
//            pwdDMessage(getString(R.string.no_equals));
//            return false;
//        }

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

    void nameMessage() {
        tilName.setError(null);
    }

    void nameMessage(String message) {
        tilName.setError(message);
    }

    void lastNameMessage() {
        tilLastName.setError(null);
    }

    void lastNameMessage(String message) {
        tilLastName.setError(message);
    }

    void mailMessage() {
        tilEmail.setError(null);
    }

    void mailMessage(String message) {
        tilEmail.setError(message);
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

    public void myToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);//para que funcione vete al manifest agregalo
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
