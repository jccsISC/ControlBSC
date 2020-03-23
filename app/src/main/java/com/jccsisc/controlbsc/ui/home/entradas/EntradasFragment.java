package com.jccsisc.controlbsc.ui.home.entradas;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.RegistrarE_SActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_S_C_Activity;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;

public class EntradasFragment extends Fragment {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
    FirebaseAuth mAuth;

    private RecyclerView rvEntradas;
    private ArrayList<Producto> productoArrayList, productoArrayList2;
    private ProductosAdapter entradasAdapter, entradasAdapter2;
    private EditText edtBuscador;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_entradas, container, false);
        edtBuscador = v.findViewById(R.id.txtBuscador);
        rvEntradas = v.findViewById(R.id.recyclerViewEntradas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvEntradas.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        entradasAdapter = new ProductosAdapter(productoArrayList, getActivity(), "Entrada");


        productoArrayList2 = new ArrayList<>();
        entradasAdapter2 = new ProductosAdapter(productoArrayList2, getActivity(), "Entrada");


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

                if(productoArrayList.get(pos).getUnit().equals("Caja")){
                    Intent i = new Intent(getContext(), RegistrarE_S_C_Activity.class);
                    i.putExtra("nameProducto", productoArrayList.get(pos).getName());
                    i.putExtra("idKey", productoArrayList.get(pos).getIdKey());
                    startActivity(i);
                }else if(productoArrayList.get(pos).getUnit().equals("Pieza")){
                    Intent i = new Intent(getContext(), RegistrarE_SActivity.class);
                    startActivity(i);
                }

            }
        });

        entradasAdapter2.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick( int pos) {

                if(productoArrayList2.get(pos).getUnit().equals("Caja")){
                    Intent i = new Intent(getContext(), RegistrarE_S_C_Activity.class);
                    i.putExtra("nameProducto", productoArrayList2.get(pos).getName());
                    i.putExtra("idKey", productoArrayList2.get(pos).getIdKey());
                    startActivity(i);
                }else if(productoArrayList2.get(pos).getUnit().equals("Pieza")){
                    Intent i = new Intent(getContext(), RegistrarE_SActivity.class);
                    startActivity(i);
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
                    rvEntradas.setAdapter(entradasAdapter2);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }


    private void metodoBuscar(CharSequence s){
        productoArrayList2.clear();
        for (int i = 0; i<productoArrayList.size(); i++) {
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
