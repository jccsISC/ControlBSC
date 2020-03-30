package com.jccsisc.controlbsc.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.ui.historial.HistorialFragment;
import com.jccsisc.controlbsc.ui.home.entradas.EntradasFragment;
import com.jccsisc.controlbsc.ui.home.salidas.SalidasFragment;
import com.jccsisc.controlbsc.ui.inventario.InventarioFragment;
import com.jccsisc.controlbsc.ui.procesos.ProcesosFragment;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private CardView cardViewEntrada, cardViewSalidas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        cardViewEntrada = v.findViewById(R.id.cardViewEntrada);
        cardViewSalidas = v.findViewById(R.id.cardViewSalida);

        cardViewEntrada.setOnClickListener(this);
        cardViewSalidas.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardViewEntrada:
                Fragment nuevoFragmento = new EntradasFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.cardViewSalida:
                Fragment nuevoFrament = new SalidasFragment();
                FragmentTransaction transaction1 = getParentFragmentManager().beginTransaction();
                transaction1.replace(R.id.nav_host_fragment, nuevoFrament);
                transaction1.addToBackStack(null);
                transaction1.commit();
                break;
        }
    }
}
