package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.MovimientoSalidasAdapter;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;

public class MovimientoSalidasActivity extends AppCompatActivity {

    Intent extras;
    private TextView txtMensaje;
    MovimientoSalidasAdapter adapter;
    ArrayList<Movimiento> myList = new ArrayList<>();
    ArrayList<Movimiento> myListPositive = new ArrayList<>();
    ArrayList<Movimiento> myListNegative = new ArrayList<>();
    ArrayList<Movimiento> myListFilteredFinal = new ArrayList<>();
    private ShimmerRecyclerViewX rvMovimientos;
    private String unit, name, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_movimiento);
        myToolbar("Dar Salida al producto");
        extras = getIntent();
        Bundle bundle = extras.getExtras();
        myList = (ArrayList<Movimiento>)bundle.getSerializable("movimientos");
        unit   = extras.getStringExtra("unit");
        name   = extras.getStringExtra("name");
        key    = extras.getStringExtra("key");
        txtMensaje = findViewById(R.id.txtMensaje);
        txtMensaje.setText("Seleccione de qué fecha quiere darle salida al producto");
        Log.e("size", myList.size() +"");
        rvMovimientos = findViewById(R.id.recyclerViewModificarMovimiento);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMovimientos.setLayoutManager(linearLayoutManager);

        adapter = new MovimientoSalidasAdapter(myListFilteredFinal, getApplicationContext(), unit, name);
        filterData();

        adapter.setOnClickListener(new MovimientoSalidasAdapter.OnClickListener() {
            @Override
            public void onItemClick(int pos) {
                if(unit.equals("Caja")) {
                    Intent i = new Intent(getApplicationContext(), RegistrarE_S_C_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movimiento", myList.get(pos));
                    i.putExtras(bundle);
                    i.putExtra("nameProducto", name);
                    i.putExtra("idKey", key);
                    i.putExtra("type", "modify");
                    startActivity(i);
                }else if(unit.equals("Pieza")) {
                    Intent i = new Intent(getApplicationContext(), RegistrarE_SActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movimiento", myList.get(pos));
                    i.putExtras(bundle);
                    i.putExtra("nameProducto", name);
                    i.putExtra("idKey", key);
                    i.putExtra("type", "modify");
                    startActivity(i);
                }
            }
        });
    }

    private void filterData() {
        //Paso 1 -> Obtener los mov type positive
        //Paso 2 -> Restar todos los type negative con idMovimiento equals
        //Paso 3 -> Ponerlo dentro de l recyclerView

        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getType().equals("positive")) {
               myListPositive.add(myList.get(i));
            }
        }

        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getType().equals("negative")) {
                myListNegative.add(myList.get(i));
            }
        }

        for (int g = 0; g < myListPositive.size(); g++) {//3
            boolean found = false;
            int quantity = 0;
            double weight = 0;
            for (int h = 0; h < myListNegative.size(); h++) {//3
                if (myListPositive.get(g).getIdKey().equals(myListNegative.get(h).getIdMovimiento())) {
                    found = true;
                    quantity += myListNegative.get(h).getQuantity();
                    weight   += myListNegative.get(h).getWeight();
                }
            }

            if (found) {
                if ((myListPositive.get(g).getWeight() - weight) != 0) {
                    Movimiento nuevoMov = new Movimiento(myListPositive.get(g).getDate(), myListPositive.get(g).getType(),
                            myListPositive.get(g).getHour(), myListPositive.get(g).getDestiny(), myListPositive.get(g).getStatus(),
                            myListPositive.get(g).getIdKey(), myListPositive.get(g).getIdMovimiento(),
                            (myListPositive.get(g).getWeight() - weight), (myListPositive.get(g).getQuantity() - quantity));
                    myListFilteredFinal.add(nuevoMov);
                }
            }else{
                myListFilteredFinal.add(myListPositive.get(g));
            }
        }
        rvMovimientos.setAdapter(adapter);
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

    private void mostraraActivity(int pos, ArrayList<Producto> arrayList) {
        if(arrayList.get(pos).getUnit().equals("Caja")) {
            Intent i = new Intent(this, RegistrarE_S_C_Activity.class);
            i.putExtra("nameProducto", arrayList.get(pos).getName());
            i.putExtra("idKey", arrayList.get(pos).getIdKey());
            startActivity(i);
        }else if(arrayList.get(pos).getUnit().equals("Pieza")) {
            Intent i = new Intent(this, RegistrarE_SActivity.class);
            i.putExtra("nameProducto", arrayList.get(pos).getName());
            i.putExtra("idKey", arrayList.get(pos).getIdKey());
            startActivity(i);
        }
    }
}
