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
        final  String uid = mAuth.getUid();

        myRef.child("DB_Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productoArrayList.removeAll(productoArrayList);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
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
