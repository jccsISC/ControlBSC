package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.MovimientoSalidasAdapter;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.MovimientoPiezas;
import com.jccsisc.controlbsc.model.Producto;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;

public class MovimientoSalidasActivity extends AppCompatActivity {

    Intent extras;
    MovimientoSalidasAdapter adapter;
    ArrayList<Movimiento> myList = new ArrayList<>();
    ArrayList<Movimiento> myListFiltered = new ArrayList<>();
    private ShimmerRecyclerViewX rvMovimientos;
    private String unit, name, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_movimiento);
        myToolbar("Modificar movimiento");
        extras = getIntent();
        Bundle bundle = extras.getExtras();
        myList = (ArrayList<Movimiento>)bundle.getSerializable("movimientos");
        unit   = extras.getStringExtra("unit");
        name   = extras.getStringExtra("name");
        key    = extras.getStringExtra("key");

        Log.e("size", myList.size() +"");
        rvMovimientos = findViewById(R.id.recyclerViewModificarMovimiento);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMovimientos.setLayoutManager(linearLayoutManager);

        adapter = new MovimientoSalidasAdapter(myListFiltered, getApplicationContext(), unit, name);
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
        MovimientoPiezas nuevoMov = new MovimientoPiezas();
        for(int i = 0; i < myList.size(); i++){
            if(myList.get(i).getType().equals("positive")){
               myListFiltered.add(myList.get(i));
            }
        }
//        for(int g = 0; g < myListFiltered.size(); g++){
//            for(int h = 0; h < myListFiltered){
//
//
//
//            }
//
//        }
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
