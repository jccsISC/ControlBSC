package com.jccsisc.controlbsc.ui.productos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosFragment extends Fragment {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1");
    FirebaseAuth mAuth;

    private RecyclerView rvProductos;
    private ArrayList<Producto> productoArrayList;
    private ProductosAdapter productosAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_productos, container, false);
        rvProductos = v.findViewById(R.id.recyclerProductos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProductos.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        productosAdapter = new ProductosAdapter(productoArrayList, getActivity());

        rvProductos.setAdapter(productosAdapter);

        mAuth = FirebaseAuth.getInstance(); //obtenemos al usuario actual

        myRef.child("DB_Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("LOG",dataSnapshot.toString());
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

                productosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}
