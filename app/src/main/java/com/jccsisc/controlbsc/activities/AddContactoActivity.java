package com.jccsisc.controlbsc.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.dialogs.ChargingFragment;
import com.jccsisc.controlbsc.model.Proveedor;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class AddContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout tilNameContac, tilLastNameContac, tilNameCompany, tilPhoneCompany;
    private TextInputEditText tieNameContac, tieLastNameContac, tieNameCompany, tiePhoneCompany;
    private ChargingFragment chargingFragment = new ChargingFragment();
    private Button btnSubirImg, btnCrearContact;
    private CircleImageView imgvProveedor;
    private Bitmap thumb_bitmp = null; //comprimir img
    private Intent extras;
    private Proveedor modelito;
    private String type;
    private String id = "";
    private String imgCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacto);
        extras = getIntent();
        type = extras.getStringExtra("type");

        myToolbar(getString(R.string.crear_contacto));
        btnSubirImg       = findViewById(R.id.btnSubirImg);
        btnCrearContact   = findViewById(R.id.btnCrearContact);
        imgvProveedor     = findViewById(R.id.imgvProveedor);
        tilNameContac     = findViewById(R.id.tilNameContact);
        tieNameContac     = findViewById(R.id.tieNameContact);
        tilLastNameContac = findViewById(R.id.tilLastNameContact);
        tieLastNameContac = findViewById(R.id.tieLastNameContact);
        tilNameCompany    = findViewById(R.id.tilNameCompany);
        tieNameCompany    = findViewById(R.id.tieNameCompany);
        tilPhoneCompany   = findViewById(R.id.tilPhoneContact);
        tiePhoneCompany   = findViewById(R.id.tiePhoneContact);

        imgvProveedor.setOnClickListener(this);
        btnCrearContact.setOnClickListener(this);

        if(type.equals("edit")) {

            myToolbar("Actualizar Contacto");
            Bundle bundle = extras.getExtras();
            modelito = (Proveedor) bundle.getSerializable("modelo");
            tieNameContac.setText(modelito.getName());
            tieLastNameContac.setText(modelito.getLastName());
            tieNameCompany.setText(modelito.getNameCompany());
            tiePhoneCompany.setText(modelito.getNumberPhone());
            id = extras.getStringExtra("idKey");

            btnSubirImg.setText("Actualizar Foto");
            btnCrearContact.setText("Guardar cambios");

//            imgvProveedor.getLayoutParams().width = 100;
//            imgvProveedor.getLayoutParams().height = 100;
//            imgvProveedor.setAdjustViewBounds(true);

            Glide.with(this)
                    .load(modelito.getImgCompany())
                    .into(imgvProveedor);
            imgCompany = modelito.getImgCompany();
        }else if(type.equals("new")){
            id = FirebaseDatabase.getInstance().getReference().push().getKey();
        }

        tieNameContac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { nameValid(s); }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tieLastNameContac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { lastNameValid(s); }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tieNameCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { nameCompanyValid(s); }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tiePhoneCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { phoneNumberValid(s); }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrearContact:
                registarContacto();
                break;
            case R.id.imgvProveedor:
                CropImage.startPickImageActivity(AddContactoActivity.this);
                break;
        }
    }

    private void registarContacto() {
        String name           = tieNameContac.getEditableText().toString().toUpperCase();
        String lastName       = tieLastNameContac.getEditableText().toString().toUpperCase();
        String nameCompany    = tieNameCompany.getEditableText().toString().toUpperCase();
        String phoneNumber    = tiePhoneCompany.getEditableText().toString();

        if (nameValid(name) & nameCompanyValid(nameCompany) & phoneNumberValid(phoneNumber)) {
            chargingFragment.show(getSupportFragmentManager(), "dialogChargin");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imgUri = CropImage.getPickImageResultUri(this, data);
            //recortar imagen
            CropImage.activity(imgUri).setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640, 480)
                    .setAspectRatio(2, 1).start(AddContactoActivity.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File url = new File(resultUri.getPath());

                Picasso.get().load(url).into(imgvProveedor);

                //comprimiendo imagen
                try {
                    thumb_bitmp = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(url);
                }catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmp.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte [] thumb_byte = byteArrayOutputStream.toByteArray();

                //fin del compresor

                //para generr un nombre aleatorio en cada imagen que se suba
                int p = (int) (Math.random() *25 + 1); int s = (int) (Math.random() *25 +1);
                int t = (int) (Math.random() *25 + 1); int c = (int) (Math.random() *25 +1);
                int num1 = (int) (Math.random() * 1012 + 2111);
                int num2 = (int) (Math.random() * 1012 + 2111);

                String [] elements = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
                final String randome = elements[p] + elements[s] + num1 + elements[t] + elements[c] + num2 + "comprimido.jpg";

                btnSubirImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chargingFragment.show(getSupportFragmentManager(), "dialogCharging");
                        final StorageReference ref = NodosFirebase.storageReference.child(randome);
                        UploadTask uploadTask = ref.putBytes(thumb_byte);

                        //subir imagen al storage
                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw Objects.requireNonNull(task.getException()); //evitamos que muestre error
                                }else{

                                }
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                //tenemos la imagen cargada al storage
                                Uri downLoadUri = task.getResult();
                                chargingFragment.dismiss();
                                imgCompany = downLoadUri.toString();
                                snackMessage("Imagen cargada");
                            }
                        });
                    }
                });
            }
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
