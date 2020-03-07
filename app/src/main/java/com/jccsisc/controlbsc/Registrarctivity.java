package com.jccsisc.controlbsc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Registrarctivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarctivity);

//        getSupportActionBar().setTitle("Registrar nuevo Producto");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
    }

    @Override
    public void onClick(View v) {

    }
}
