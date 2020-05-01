package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.CajasAdapter;
import com.jccsisc.controlbsc.dialogs.ChargingFragment;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RegistrarE_S_C_Activity extends AppCompatActivity implements View.OnClickListener {

    Intent extras;
    private AlertDialog dialog;
    double sumatotal = 0.0;
    private Button btnCargar;
    private ImageButton btnExit;
    private EditText edtPesoC;
    private ImageButton  btnSumar;
    private CajasAdapter cajasAdapter;
    public static String horaE_S, dateEntrada;
    public static int hora, minutos, segundos;
    private RecyclerView recyclerPesadasC;
    private TextView nameProducto, txtCT, txtPesoT;
    private String  dateSalida = "", idKey, name;
    public static Button btnModificar, btnEliminar;
    private TextInputLayout tilPesoCaja;
    public TextInputEditText tiePesoCaja;
    public ArrayList<Detalle> detallesArrayList = new ArrayList<>();
    private int position;
    private TextView txtPesoCaja;
    private ChargingFragment chargingFragment = new ChargingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_productos_caja);

        toolbar();
        obtenerFecha();
        obtenerHora();

        extras = getIntent();
        name  = extras.getStringExtra("nameProducto");
        idKey = extras.getStringExtra("idKey");

        recyclerPesadasC = findViewById(R.id.recyclerPesadasC);
        nameProducto     = findViewById(R.id.txtNameProductoR);
        txtCT            = findViewById(R.id.txtCT);
        txtPesoT         = findViewById(R.id.txtPesoT);
        btnCargar        = findViewById(R.id.btnCargar);
        btnSumar         = findViewById(R.id.btnSumar);
        edtPesoC         = findViewById(R.id.edtPesoC);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerPesadasC.setLayoutManager(linearLayoutManager);
        cajasAdapter = new CajasAdapter(detallesArrayList, this);
        recyclerPesadasC.setAdapter(cajasAdapter);

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPesoC.getText().toString().trim().equals("")){
                    mtoast("Ingrese una cantidad");
                }else{
                    double peso = Double.parseDouble(edtPesoC.getText().toString().trim());

                    String id =  FirebaseDatabase.getInstance().getReference().push().getKey();
                    Detalle modelitoDetalle =  new Detalle(id, peso);
                    modelitoDetalle.setIdKey(id);

                    detallesArrayList.add(modelitoDetalle);
                    cajasAdapter.notifyDataSetChanged();
                    edtPesoC.setText("");
                    sumatoriaPeso();
                }
            }
        });

        nameProducto.setText(name);

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detallesArrayList.size() == 0){
                    mtoast("No hay elementos que sumar");
                }else{
                    String id = FirebaseDatabase.getInstance().getReference().push().getKey();

                    chargingFragment.show(getSupportFragmentManager(), "dialogCharging");

                    Movimiento movimiento = new Movimiento(dateEntrada,"positive",horaE_S, "Congelacion",
                            "normal", id, sumatotal, detallesArrayList.size());

                    movimiento.setDetalles(detallesArrayList);

                    NodosFirebase.myRef.child(idKey).child("movimientos").child(id).setValue(movimiento)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mtoast("Se guardó correctamente");
                            finish();
                        }
                    });
                    }
            }
        });

        cajasAdapter.setOnClickListener(new CajasAdapter.OnClickListener() {
            @Override
            public void onItemClick(final int pos) {
                position = pos;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistrarE_S_C_Activity.this, R.style.AppCompatDialogStyle);
                View view = getLayoutInflater().inflate(R.layout.fragment_modify_caja, null);
                alertDialog.setView(view);
                dialog = alertDialog.create();
                instancias(view);
                dialog.show();
            }
        });

    }//onCreate


    private void sumatoriaPeso() {
        sumatotal = 0;
        for (int i = 0; i < detallesArrayList.size(); i++) {
            sumatotal += detallesArrayList.get(i).getPeso();
        }

        sumatotal = Math.rint(sumatotal * 100)/100;
        txtCT.setText(String.valueOf(detallesArrayList.size()));
        txtPesoT.setText(String.valueOf(sumatotal));
    }

    private void instancias(View v) {
        btnExit      = v.findViewById(R.id.btnExit);
        tilPesoCaja  = v.findViewById(R.id.tilPesoCaja);
        tiePesoCaja  = v.findViewById(R.id.tiePesoCaja);
        txtPesoCaja  = v.findViewById(R.id.txtModificarCaja);
        btnModificar = v.findViewById(R.id.btnModifyCaja);
        btnEliminar  = v.findViewById(R.id.btnEliminar);

        txtPesoCaja.setText(String.valueOf(detallesArrayList.get(position).getPeso()));

        tiePesoCaja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pesoValid(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnExit.setOnClickListener((View.OnClickListener) this);
        btnEliminar.setOnClickListener((View.OnClickListener) this);
        btnModificar.setOnClickListener((View.OnClickListener) this);
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.registrar_entradas));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void obtenerFecha() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);
    }

    public static void obtenerHora() {
        Calendar calendario = new GregorianCalendar();
        hora    = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos= calendario.get(Calendar.SECOND);
        horaE_S = hora + ":" + minutos + ":" + segundos;
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

    private boolean pesoValid(CharSequence peso) {
        if (TextUtils.isEmpty(peso)) {
            pesoMessage(getString(R.string.empty_field));
            return false;
        }

        pesoMessage();
        return true;
    }

    void pesoMessage() { tilPesoCaja.setError(null);}
    void pesoMessage(String message) { tilPesoCaja.setError(message);}

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                dialog.dismiss();
                break;
            case R.id.btnModifyCaja:
                String pesoCaja2 = tiePesoCaja.getEditableText().toString();
                if (pesoValid(pesoCaja2)) {
                    double pesoAnt = detallesArrayList.get(position).getPeso();
                    Detalle detalle = new Detalle(detallesArrayList.get(position).getIdKey(), Double.parseDouble(pesoCaja2));
                    detallesArrayList.remove(position);
                    detallesArrayList.add(position, detalle);
                    cajasAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Se modificó: " + pesoAnt + " por: ( " + pesoCaja2 + " )",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    sumatoriaPeso();
                }

                break;
            case R.id.btnEliminar:
                detallesArrayList.remove(position);
                cajasAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Eliminado",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                sumatoriaPeso();
                break;
        }
    }
}
