package com.jccsisc.controlbsc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.ModifyMovimientoAdapter;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.toolbarShow;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ModificarMovimientoActivity extends AppCompatActivity {

    Intent extras;
    ModifyMovimientoAdapter adapter;
    ArrayList<Movimiento> myList = new ArrayList<>();;
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
        unit = extras.getStringExtra("unit");
        name = extras.getStringExtra("name");
        key = extras.getStringExtra("key");

        Log.e("size", myList.size() +"");
        rvMovimientos = findViewById(R.id.recyclerViewModificarMovimiento);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMovimientos.setLayoutManager(linearLayoutManager);


        adapter = new ModifyMovimientoAdapter(myList, getApplicationContext(), unit, name);
        rvMovimientos.setAdapter(adapter);
        adapter.setOnClickListener(new ModifyMovimientoAdapter.OnClickListener() {
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
