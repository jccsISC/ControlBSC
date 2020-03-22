package com.jccsisc.controlbsc.ui.home.entradas;

import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.jccsisc.controlbsc.activities.RegistrarE_SActivity;
import com.jccsisc.controlbsc.adapters.EntradasAdapter;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;

public class EntradasFragment extends Fragment {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
    FirebaseAuth mAuth;

    private RecyclerView rvEntradas;
    private ArrayList<Producto> productoArrayList;
    private ProductosAdapter entradasAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entradas, container, false);
        rvEntradas = v.findViewById(R.id.recyclerViewEntradas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvEntradas.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        entradasAdapter = new ProductosAdapter(productoArrayList, getActivity());

        rvEntradas.setAdapter(entradasAdapter);

        mAuth = FirebaseAuth.getInstance(); //obtenemos al usuario actual

        myRef.addValueEventListener(new ValueEventListener() {
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

                entradasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        entradasAdapter.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick( int pos) {
                mtoast(productoArrayList.get(pos).getName());
                Intent i = new Intent(getContext(), RegistrarE_SActivity.class);
//                i.putExtra("")
                startActivity(i);
            }
        });

        return v;
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }
}
