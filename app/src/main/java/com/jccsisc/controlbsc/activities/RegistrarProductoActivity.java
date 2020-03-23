package com.jccsisc.controlbsc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Producto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistrarProductoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String PRODUCTO_NODE = "DB_Bodega1";
    private static final String DB_NODE = "DB_Productos";

    private DatabaseReference myRef, databaseReference2;

    private Boolean miclick = false;
    private TextInputLayout tilProducto;
    private EditText tieRegistrarP;
    private Button btnCaja, btnPieza, btnSave;
    private String unit, camara, dateEntrada;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarctivity);

        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_Registrar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference(); //obtenemos la db el nodo raiz controlbsc-899b5
        databaseReference2 = FirebaseDatabase.getInstance().getReference(PRODUCTO_NODE).child(DB_NODE);

        btnCaja     = findViewById(R.id.btnCaja);
        btnPieza    = findViewById(R.id.btnPieza);
        btnSave     = findViewById(R.id.btnSave);
        tilProducto = findViewById(R.id.tilRegistrarP); //Aqui tienes un error
        tieRegistrarP = findViewById(R.id.tieRegistrarP);//a tecreasjaja




        tieRegistrarP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isProductEmpty(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnCaja.setOnClickListener(this);
        btnPieza.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCaja:
                unit    = "Caja";
                miclick=true;
                btnSelected("caja");
                if(unit.equals("Caja")) {
                    camara = "Congelación";
                }
                break;
            case R.id.btnPieza:
                unit    = "Pieza";
                miclick = true;
                btnSelected("pieza");
                    if(unit.equals("Pieza")) {
                        camara = "Conservación";
                    }
                break;
            case R.id.btnSave:
                final String nameProducto = tieRegistrarP.getText().toString().toUpperCase();
                if (isProductEmpty(nameProducto)) {
                        if (miclick) {
                            createProduct(nameProducto, unit);
                        }
                }else {
                    btnCaja.setBackground(getDrawable(R.drawable.btn_selecciona));
                            btnPieza.setBackground(getDrawable(R.drawable.btn_selecciona));
                            snackMessage(getString(R.string.unidad));
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    //btnPulsado
    private void btnSelected( String boton) {
        if(boton.equals("caja")){
            btnCaja.setBackground(getDrawable(R.drawable.btnselected));
            btnCaja.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnCaja.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnCaja.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));

            btnPieza.setBackground(getDrawable(R.drawable.btn_no_selected));
            btnPieza.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnPieza.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnPieza.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));
        }else{
            btnPieza.setBackground(getDrawable(R.drawable.btnselected));
            btnPieza.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnPieza.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnPieza.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));

            btnCaja.setBackground(getDrawable(R.drawable.btn_no_selected));
            btnCaja.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnCaja.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnCaja.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));
        }
    }

    //crear productos
    private void createProduct(final String nameProducto, String unidad) {

        getDatos(new CallbackDatos() {
            @Override
            public void onCallbackTeacher(boolean name) {

                if(name){
                    mtoast("EXISTE");
                }else {

                    String id = FirebaseDatabase.getInstance().getReference().push().getKey();
                    dateEntrada = dateFormat.format(date);
                    Producto producto = new Producto(nameProducto, unit, id);

                    myRef.child(PRODUCTO_NODE).child("DB_Productos").child(id).setValue(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mtoast(getString(R.string.registrado));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mtoast("Error");
                        }
                    });
                }

            }
        }, nameProducto, unidad );

    }//fin createProduct

    //validar si es pieza de la matanza
    private boolean isOfMatanza(String pieza) {
        String [] piezasMatanza = {"CAPOTES VENDIDOS", "CABEZA DE CERDO", "PIERNAS SIN HUESO", "ESPALDILLAS SIN HUESO",
        "MANITAS", "LONJA", "HUNTOS", "HUESOS", "RIÑON", "CHAMORRO", "COSTILLA", "ESPINAZO CON LOMO COMPLETO", "ESPINAZO FRESCO",
        "LOMO", "CABEZA DE LOMO", "CHULETA MARIPOSA", "DESPIQUE", "DENTROS"};

        for (int i=0; i<piezasMatanza.length; i++) {
            if(pieza.equals(piezasMatanza[i])) { return true; }
        }
        return false;
    }

    //validar si es producto de la matanza
    private boolean isProcesoChuleta(String pieza) {
        String [] piezaProcesoCh = {"CHULETA DE MARIPOZA","CABEZA DE LOMO","ESPINACITO"};
        for(int i=0; i<piezaProcesoCh.length; i++) {
            if(pieza.equals(piezaProcesoCh[i])) { return true;}
        }
        return false;
    }

    private boolean isProcesoEspinazo(String pieza) {
        String [] piezaProcesoE = {"ESPINAZO","CABEZA DE LOMO", "LOMOS"};
        for(int i=0; i<piezaProcesoE.length; i++) {
            if(pieza.equals(piezaProcesoE[i])) { return true;}
        }
        return false;
    }

    public interface CallbackDatos {
        void onCallbackTeacher(boolean name);
    }

    private void getDatos(final CallbackDatos myCallback, final String uid, final String unidad) {
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("snapcompleto", dataSnapshot.toString());

                        if(!dataSnapshot.exists()){

                            myCallback.onCallbackTeacher(false);
//                            return;
                        }else{
                            String namecito2 = null;
                            String unit = null;
                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                if(postSnapshot.child("name").getValue(String.class).equals(uid)){
                                    namecito2 = postSnapshot.child("name").getValue(String.class);
                                    unit = postSnapshot.child("unit").getValue(String.class);
                                    Log.e("snapcompleto", unit);
                                }

                            }

                            if(uid.equals(namecito2)){
                                myCallback.onCallbackTeacher(true);
                            }else{
                                myCallback.onCallbackTeacher(false);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }

    private boolean isProductEmpty(CharSequence producto) {
        if(TextUtils.isEmpty(producto)) {
            productoMessage(getString(R.string.empty_field));
            return false;
        }
        productoMessage();
        return true;
    }

    public void snackMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

    void productoMessage() {
        tilProducto.setError(null);
    }

    void productoMessage(String message) {
        tilProducto.setError(message);
    }

}
