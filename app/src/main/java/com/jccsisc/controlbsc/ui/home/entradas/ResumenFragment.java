package com.jccsisc.controlbsc.ui.home.entradas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.adapters.ProductosAdapterResumen;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;

public class ResumenFragment extends Fragment {

    private ShimmerRecyclerViewX rvEntradasResumen;
    private ArrayList<Producto> productoArrayList;
    private ProductosAdapterResumen entradasAdapter;

    public ResumenFragment() { }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_resumen, container, false);
        MainActivity.edtAppBar.setVisibility(View.INVISIBLE);
        rvEntradasResumen = v.findViewById(R.id.recyclerViewEntradasResumen);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvEntradasResumen.setLayoutManager(linearLayoutManager);
        productoArrayList = new ArrayList<>();
        entradasAdapter   = new ProductosAdapterResumen(productoArrayList, getActivity(), "Entrada");
        rvEntradasResumen.setAdapter(entradasAdapter);
        rvEntradasResumen.showShimmerAdapter();

        NodosFirebase.myRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rvEntradasResumen.hideShimmerAdapter();
                productoArrayList.removeAll(productoArrayList);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.child("movimientos").getKey();
                    Producto producto = new Producto(
                            snapshot.child("name").getValue(String.class),
                            snapshot.child("unit").getValue(String.class),
                            snapshot.child("idKey").getValue(String.class));
                    for(DataSnapshot movimiento : snapshot.child("movimientos").getChildren()) {
                        Movimiento movimiento1;
//                        if(movimiento.child("date").getValue(String.class).equals("2020-05-05")){
                            movimiento1 = new Movimiento(
                                    movimiento.child("date").getValue(String.class),
                                    movimiento.child("type").getValue(String.class),
                                    movimiento.child("hour").getValue(String.class),
                                    movimiento.child("destiny").getValue(String.class),
                                    movimiento.child("status").getValue(String.class),
                                    movimiento.child("idKey").getValue(String.class),
                                    movimiento.child("weight").getValue(Double.class),
                                    movimiento.child("quantity").getValue(Integer.class));
                            for (DataSnapshot detalles : movimiento.child("detalles").getChildren()) {
                                Detalle detalle = new Detalle(
                                        detalles.child("idKey").getValue(String.class),
                                        detalles.child("peso").getValue(Double.class));
                                movimiento1.addDetalles(detalle);
                            }
                            producto.addMovimiento(movimiento1);
//                        }
                    }
                        ArrayList<Movimiento> movimientos = producto.getMovimientos();
                        for(int c = 0; c < movimientos.size(); c++){
                            if(movimientos.get(c).getDate().equals("2020-05-05")){
                                productoArrayList.add(producto);
                            }
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}
