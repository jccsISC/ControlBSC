package com.jccsisc.controlbsc.ui.productos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_S_C_Activity;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.dialogs.ModifyProductFragment;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;

public class ProductosFragment extends Fragment {

    private FirebaseAuth mAuth;
    public static ShimmerRecyclerViewX rvProductos;
    public static ArrayList<Producto> productoArrayList, productoArrayList2;
    public static ProductosAdapter productosAdapter, productosAdapter2;

    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_productos, container, false);
        rvProductos = v.findViewById(R.id.recyclerProductos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProductos.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        productosAdapter = new ProductosAdapter(productoArrayList, getActivity(), "Producto");

        //replicamos el arrayList para la hora de buscar
        productoArrayList2 = new ArrayList<>();
        productosAdapter2  = new ProductosAdapter(productoArrayList2, getActivity(), "Producto");
//        mAuth = FirebaseAuth.getInstance(); //obtenemos al usuario actual

        rvProductos.setAdapter(productosAdapter);
        rvProductos.showShimmerAdapter();

        MainActivity.visivilitySearch("Productos");

        NodosFirebase.myRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rvProductos.hideShimmerAdapter();
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

                if(!MainActivity.edtAppBarBuscador.getText().toString().toUpperCase().equals("")){
                    metodoBuscar(MainActivity.edtAppBarBuscador.getText().toString().toUpperCase());
                }else{
                    productosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showOptions(productoArrayList, productosAdapter);
        showOptions(productoArrayList2, productosAdapter2);

        return v;
    }

    private void showOptions(final ArrayList<Producto> arrayList, ProductosAdapter adapter) {
        adapter.setOnClickListener(new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick(final int pos) {

                String idKey = arrayList.get(pos).getIdKey();
                String name = arrayList.get(pos).getName();

                ModifyProductFragment modifyProductFragment = new ModifyProductFragment(idKey, name);
                modifyProductFragment.show(getChildFragmentManager(), "dialogModificar");
            }
        });


    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void metodoBuscar(CharSequence s){
        productoArrayList2.clear();
        for (int i = 0; i<productoArrayList.size(); i++) {
            if (productoArrayList.get(i).getName().contains(s.toString().toUpperCase())) {
                productoArrayList2.add(productoArrayList.get(i));
            }
        }
        rvProductos.setAdapter(productosAdapter2);
    }
}
