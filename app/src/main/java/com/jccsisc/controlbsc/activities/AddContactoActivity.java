package com.jccsisc.controlbsc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.dialogs.ChargingFragment;
import com.jccsisc.controlbsc.model.Proveedor;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

public class AddContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private ChargingFragment chargingFragment = new ChargingFragment();
    private ImageView imgvProveedor;
    private TextInputLayout tilNameContac, tilLastNameContac, tilNameCompany, tilPhoneCompany;
    private TextInputEditText tieNameContac, tieLastNameContac, tieNameCompany, tiePhoneCompany;

    Intent extras;
    Proveedor modelito;
    String type;
    String id = "";
    String imgCompany = "imagen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacto);
        extras = getIntent();
        type = extras.getStringExtra("type");

        myToolbar(getString(R.string.crear_contacto));
        imgvProveedor     = findViewById(R.id.imgvProveedor);
        tilNameContac     = findViewById(R.id.tilNameContact);
        tieNameContac     = findViewById(R.id.tieNameContact);
        tilLastNameContac = findViewById(R.id.tilLastNameContact);
        tieLastNameContac = findViewById(R.id.tieLastNameContact);
        tilNameCompany    = findViewById(R.id.tilNameCompany);
        tieNameCompany    = findViewById(R.id.tieNameCompany);
        tilPhoneCompany   = findViewById(R.id.tilPhoneContact);
        tiePhoneCompany   = findViewById(R.id.tiePhoneContact);

        if(type.equals("edit")) {
            Bundle bundle = extras.getExtras();
            modelito = (Proveedor) bundle.getSerializable("modelo");
            tieNameContac.setText(modelito.getName());
            tieLastNameContac.setText(modelito.getLastName());
            tieNameCompany.setText(modelito.getNameCompany());
            tiePhoneCompany.setText(modelito.getNumberPhone());
            id = extras.getStringExtra("idKey");
            Glide.with(this)
                    .load(modelito.getImgCompany())
                    .into(imgvProveedor);
            imgCompany = modelito.getImgCompany();
        }else if(type.equals("new")){
            id = FirebaseDatabase.getInstance().getReference().push().getKey();
        }

        tieNameContac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tieLastNameContac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastNameValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tieNameCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameCompanyValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tiePhoneCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumberValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        registarContacto();
    }

    private void registarContacto() {
        String name        = tieNameContac.getEditableText().toString();
        String lastName    = tieLastNameContac.getEditableText().toString();
        String nameCompany = tieNameCompany.getEditableText().toString();
        String phoneNumber    = tiePhoneCompany.getEditableText().toString();


        if (nameValid(name) & lastNameValid(lastName) & nameCompanyValid(nameCompany) & phoneNumberValid(phoneNumber)) {
            chargingFragment.show(getSupportFragmentManager(), "dialogChargin");
//            Log.e("idkey", id);

            Proveedor proveedor = new Proveedor(name, lastName, nameCompany, imgCompany, phoneNumber, id);
            NodosFirebase.myRefProveedor.child(id).setValue(proveedor).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    snackMessage(getString(R.string.registrado));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    snackMessage("ha ocurrido un error");
                }
            });
        }
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

    private boolean nameCompanyValid(CharSequence nameCompany) {

        if (TextUtils.isEmpty(nameCompany)) {
            nameCompanyMessage(getString(R.string.empty_field));
            return false;
        }

        if (nameCompany.length()<5) {
            nameCompanyMessage(getString(R.string.hint_name_company));
            return false;
        }

        nameCompanyMessage();
        return true;
    }

    private boolean phoneNumberValid(CharSequence phone) {

        if (TextUtils.isEmpty(phone)) {
            phoneMessage(getString(R.string.empty_field));
            return false;
        }

        if (phone.length() < 10) {
            phoneMessage(getString(R.string.hint_number_contact));
            return false;
        }

        phoneMessage();
        return true;
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

    void nameMessage() {
        tilNameContac.setError(null);
    }

    void nameMessage(String message) {
        tilNameContac.setError(message);
    }

    void lastNameMessage() {
        tilLastNameContac.setError(null);
    }

    void lastNameMessage(String message) {
        tilLastNameContac.setError(message);
    }

    void nameCompanyMessage() {
        tilNameCompany.setError(null);
    }

    void nameCompanyMessage(String message) {
        tilNameCompany.setError(message);
    }

    void phoneMessage() {
        tilPhoneCompany.setError(null);
    }

    void phoneMessage(String message) {
        tilPhoneCompany.setError(message);
    }


    public void snackMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
