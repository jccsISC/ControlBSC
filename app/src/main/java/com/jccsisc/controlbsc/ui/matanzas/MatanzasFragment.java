package com.jccsisc.controlbsc.ui.matanzas;

import android.os.Bundle;
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
import com.jccsisc.controlbsc.activities.MainActivity;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;

public class MatanzasFragment extends Fragment {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1");
    FirebaseAuth mAuth;

    private RecyclerView rvMatanzas;
    private ArrayList<Producto> productoArrayList;
    private ProductosAdapter productosAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_matanzas, container, false);
        rvMatanzas = v.findViewById(R.id.recyclerMatanzas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMatanzas.setLayoutManager(linearLayoutManager);

        productoArrayList = new ArrayList<>();
        productosAdapter = new ProductosAdapter(productoArrayList, getActivity(), "Producto");

        rvMatanzas.setAdapter(productosAdapter);

        mAuth = FirebaseAuth.getInstance(); //obtenemos al usuario actual
        final  String uid = mAuth.getUid();

        myRef.child("DB_Matanzas").addValueEventListener(new ValueEventListener() {
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


    //validar si es pieza de la matanza
    private boolean isOfMatanza(String pieza) {
        String [] piezasMatanza = {"CAPOTES VENDIDOS", "CABEZA DE CERDO", "PIERNAS SIN HUESO", "ESPALDILLAS SIN HUESO",
                "MANITAS", "LONJA", "HUNTOS", "HUESOS", "RIÑON", "CHAMORRO", "COSTILLA", "ESPINAZO CON LOMO COMPLETO", "ESPINAZO FRESCO",
                "LOMO", "CABEZA DE LOMO", "CHULETA MARIPOSA", "DESPIQUE", "DENTROS"};

        for (int i=0; i<piezasMatanza.length; i++) {
            if(pieza.equals(piezasMatanza[i])) { return true; }
        }
        return false;
    }

    //validar si es producto de la matanza
    private boolean isProcesoChuleta(String pieza) {
        String [] piezaProcesoCh = {"CHULETA DE MARIPOZA","CABEZA DE LOMO","ESPINACITO"};
        for(int i=0; i<piezaProcesoCh.length; i++) {
            if(pieza.equals(piezaProcesoCh[i])) { return true;}
        }
        return false;
    }

    private boolean isProcesoEspinazo(String pieza) {
        String [] piezaProcesoE = {"ESPINAZO","CABEZA DE LOMO", "LOMOS"};
        for(int i=0; i<piezaProcesoE.length; i++) {
            if(pieza.equals(piezaProcesoE[i])) { return true;}
        }
        return false;
    }
}
