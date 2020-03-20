package com.jccsisc.controlbsc.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.VariablesGLobales;
import com.jccsisc.controlbsc.model.Entradas;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistrarE_SActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1");
    private TextView nameProducto, pesoTSin;
    private EditText  cajaTres, cajaDosCuatro, cajaDos, cajaUnoSeis, cajaUnoCuatro, cantPiezas, pesoTCon;

    private String dateEntrada, dateSalida = "", idKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_matanza);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.entradas));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameProducto = findViewById(R.id.txtNameProductoR);
        cajaTres     = findViewById(R.id.etdTreskg);
        cajaDosCuatro= findViewById(R.id.etdDosCuatrokg);
        cajaDos      = findViewById(R.id.edtDoskg);
        cajaUnoSeis  = findViewById(R.id.edtUnoSeiskg);
        cajaUnoCuatro= findViewById(R.id.etdUnoCuatrokg);
        cantPiezas   = findViewById(R.id.edtCantPiezas);
        pesoTCon     = findViewById(R.id.edtPesoTCajas);
        pesoTSin     = findViewById(R.id.txtPesoTSinC);

        nameProducto.setText(VariablesGLobales.nombreProducto);
        idKey = VariablesGLobales.idKeyProducto;

    }

    @Override
    public void onClick(View v) {

    }


    //registrar las entradas de piezas
    private void registrarEntradasPieza(View v) {
        RegistrarProductoActivity r = new RegistrarProductoActivity();
        String nameProduct = nameProducto.getText().toString();
        float piezas = Float.parseFloat(cantPiezas.getText().toString());
        float pesoTc = Float.parseFloat(pesoTCon.getText().toString());
        float pesoTs = Float.parseFloat(pesoTSin.getText().toString());

        Entradas entradas= new Entradas(nameProduct, pesoTs, piezas, idKey, dateEntrada, dateSalida);
    }


    //obtener fecha
    private void getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);
    }


//    private boolean isProductEmpty(CharSequence producto) {
//        if(TextUtils.isEmpty(producto)) {
//            productoMessage(getString(R.string.empty_field));
//            return false;
//        }
//        productoMessage();
//        return true;
//    }

//    public void snackMessage(String message) {
//        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
//    }
//
//    public void mtoast(String msj) {
//        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
//        toast.show();
//    }
//
//    void productoMessage() {
//        tilProducto.setError(null);
//    }
//
//    void productoMessage(String message) {
//        tilProducto.setError(message);
//    }

}
