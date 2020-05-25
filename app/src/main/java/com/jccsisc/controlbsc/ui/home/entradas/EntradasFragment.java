package com.jccsisc.controlbsc.ui.home.entradas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;
import com.jccsisc.controlbsc.activities.ModificarMovimientoActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_SActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_S_C_Activity;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EntradasFragment extends Fragment {

    private ShimmerRecyclerViewX rvEntradas;
    private ArrayList<Producto> productoArrayList, productoArrayList2;
    private ProductosAdapter entradasAdapter, entradasAdapter2;
    private EditText edtBuscador;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entradas, container, false);

        edtBuscador = v.findViewById(R.id.edtBuscador);
        rvEntradas = v.findViewById(R.id.recyclerViewEntradas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvEntradas.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        entradasAdapter = new ProductosAdapter(productoArrayList, getActivity(), "Entrada");

        productoArrayList2 = new ArrayList<>(); //este lo ocupams para refrezcar el segundo adapter para el buscador
        entradasAdapter2 = new ProductosAdapter(productoArrayList2, getActivity(), "Entrada");
        rvEntradas.setAdapter(entradasAdapter);

        rvEntradas.showShimmerAdapter();

        NodosFirebase.myRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rvEntradas.hideShimmerAdapter();
//                Log.e("LOG",dataSnapshot.toString());
                productoArrayList.removeAll(productoArrayList);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = new Producto(
                            snapshot.child("name").getValue(String.class),
                            snapshot.child("unit").getValue(String.class),
                            snapshot.child("idKey").getValue(String.class));

                    for(DataSnapshot movimiento : snapshot.child("movimientos").getChildren()){
                        Movimiento movimiento1 = new Movimiento(
                                movimiento.child("date").getValue(String.class),
                                movimiento.child("type").getValue(String.class),
                                movimiento.child("hour").getValue(String.class),
                                movimiento.child("destiny").getValue(String.class),
                                movimiento.child("status").getValue(String.class),
                                movimiento.child("idKey").getValue(String.class),
                                movimiento.child("idMovimiento").getValue(String.class),
                                movimiento.child("weight").getValue(Double.class),
                                movimiento.child("quantity").getValue(Integer.class));

                        for(DataSnapshot detalles : movimiento.child("detalles").getChildren()){

                            Detalle detalle = new Detalle(
                                    detalles.child("idKey").getValue(String.class),
                                    detalles.child("peso").getValue(Double.class));

                            movimiento1.addDetalles(detalle);
                        }
                        producto.addMovimiento(movimiento1);
                    }
                    productoArrayList.add(producto);
                }

                if(!edtBuscador.getText().toString().toUpperCase().equals("")){
                    metodoBuscar(edtBuscador.getText().toString().toUpperCase());
                }else{
                    entradasAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        entradasAdapter.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick( int pos) {
                mostraraActivity(pos, productoArrayList);
            }

            @Override
            public void onLongItemClick(int pos) {
                ArrayList<Movimiento> movimientos = new ArrayList<>();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date = new Date();
                String dateToday = dateFormat.format(date);
                for (int i = 0; i < productoArrayList.get(pos).getMovimientos().size(); i++) {
                    if (productoArrayList.get(pos).getMovimientos().get(i).getDate().equals(dateToday)) {
                        movimientos.add(productoArrayList.get(pos).getMovimientos().get(i));
                    }
                }

                if (movimientos.size() != 0) {
                    Intent i = new Intent(getContext(), ModificarMovimientoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movimientos", movimientos);
                    i.putExtras(bundle);
                    i.putExtra("unit", productoArrayList.get(pos).getUnit());
                    i.putExtra("name", productoArrayList.get(pos).getName());
                    i.putExtra("key", productoArrayList.get(pos).getIdKey());
                    startActivity(i);
                } else {
                    mtoast("No hay movimientos");
                }
            }
        });

        entradasAdapter2.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick( int pos) {
                mostraraActivity(pos, productoArrayList2);
            }

            @Override
            public void onLongItemClick(int pos) {
                ArrayList<Movimiento> movimientos = new ArrayList<>();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date = new Date();
                String dateToday = dateFormat.format(date);
                for (int i = 0; i < productoArrayList2.get(pos).getMovimientos().size(); i++) {
                    if (productoArrayList2.get(pos).getMovimientos().get(i).getDate().equals(dateToday)) {
                        movimientos.add(productoArrayList2.get(pos).getMovimientos().get(i));
                    }
                }

                if (movimientos.size() != 0) {
                    Intent i = new Intent(getContext(), ModificarMovimientoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movimientos", movimientos);
                    i.putExtras(bundle);
                    i.putExtra("unit", productoArrayList2.get(pos).getUnit());
                    i.putExtra("name", productoArrayList2.get(pos).getName());
                    i.putExtra("key", productoArrayList2.get(pos).getIdKey());
                    startActivity(i);
                } else {
                    mtoast("No hay movimientos");
                }
            }
        });

        edtBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    metodoBuscar(s);
                }else{
                    rvEntradas.setAdapter(entradasAdapter);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }


    private void mostraraActivity(int pos, ArrayList<Producto> arrayList) {
        if(arrayList.get(pos).getUnit().equals("Caja")) {
            Intent i = new Intent(getContext(), RegistrarE_S_C_Activity.class);
            i.putExtra("type", "create");
            i.putExtra("nameProducto", arrayList.get(pos).getName());
            i.putExtra("idKey", arrayList.get(pos).getIdKey());
            startActivity(i);
        }else if(arrayList.get(pos).getUnit().equals("Pieza")) {
            Intent i = new Intent(getContext(), RegistrarE_SActivity.class);
            i.putExtra("type", "create");
            i.putExtra("nameProducto", arrayList.get(pos).getName());
            i.putExtra("idKey", arrayList.get(pos).getIdKey());
            startActivity(i);
        }
    }

    private void metodoBuscar(CharSequence s){
        productoArrayList2.clear();
        for (int i = 0; i < productoArrayList.size(); i++) {
            if (productoArrayList.get(i).getName().contains(s.toString().toUpperCase())) {
                productoArrayList2.add(productoArrayList.get(i));
            }
        }
        rvEntradas.setAdapter(entradasAdapter2);
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }
}
