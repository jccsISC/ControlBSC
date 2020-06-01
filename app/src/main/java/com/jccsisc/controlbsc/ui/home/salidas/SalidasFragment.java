package com.jccsisc.controlbsc.ui.home.salidas;

import android.content.Intent;
import android.os.Bundle;
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
import com.jccsisc.controlbsc.activities.ModificarMovimientoActivity;
import com.jccsisc.controlbsc.activities.MovimientoSalidasActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_SActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_S_C_Activity;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;

public class SalidasFragment extends Fragment {

    private EditText edtBuscador;
    private ShimmerRecyclerViewX rvSalidas;
    private ProductosAdapter salidasAdapter, salidasAdapter2;
    private ArrayList<Producto> productoArrayList, productoArrayList2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_salidas, container, false);

        edtBuscador = v.findViewById(R.id.edtBuscador);
        rvSalidas = v.findViewById(R.id.recyclerViewSalidas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvSalidas.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        salidasAdapter = new ProductosAdapter(productoArrayList, getActivity(), "Producto");

        productoArrayList2 = new ArrayList<>(); //este lo ocupams para refrezcar el segundo adapter para el buscador
        salidasAdapter2 = new ProductosAdapter(productoArrayList2, getActivity(), "Producto");
        rvSalidas.setAdapter(salidasAdapter);

        rvSalidas.showShimmerAdapter();

        NodosFirebase.myRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rvSalidas.hideShimmerAdapter();
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
                    salidasAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        salidasAdapter.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick( int pos) {
                ArrayList<Movimiento> movimientos = new ArrayList<>();

                for (int i = 0; i < productoArrayList.get(pos).getMovimientos().size(); i++) {
                    movimientos.add(productoArrayList.get(pos).getMovimientos().get(i));
                }

                if (movimientos.size() != 0) {
                    Intent i = new Intent(getContext(), MovimientoSalidasActivity.class);
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

            @Override
            public void onLongItemClick(int pos) {

            }
        });

        salidasAdapter2.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick( int pos) {
                ArrayList<Movimiento> movimientos = new ArrayList<>();

                for (int i = 0; i < productoArrayList2.get(pos).getMovimientos().size(); i++) {
                    movimientos.add(productoArrayList2.get(pos).getMovimientos().get(i));
                }

                if (movimientos.size() != 0) {
                    Intent i = new Intent(getContext(), MovimientoSalidasActivity.class);
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

            @Override
            public void onLongItemClick(int pos) {

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
                    rvSalidas.setAdapter(salidasAdapter2);
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
            i.putExtra("nameProducto", arrayList.get(pos).getName());
            i.putExtra("idKey", arrayList.get(pos).getIdKey());
            startActivity(i);
        }else if(arrayList.get(pos).getUnit().equals("Pieza")) {
            Intent i = new Intent(getContext(), RegistrarE_SActivity.class);
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
        rvSalidas.setAdapter(salidasAdapter2);
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }
}
