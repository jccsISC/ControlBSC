package com.jccsisc.controlbsc.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.tabs.SeccionesAdapter;
import com.jccsisc.controlbsc.ui.matanzas.MatanzasFragment;
import com.jccsisc.controlbsc.ui.procesos.ProcesosFragment;
import com.jccsisc.controlbsc.ui.productos.ProductosFragment;
import com.jccsisc.controlbsc.ui.proveedores.ProveedoresFragment;

public class ContenedorFragment extends Fragment {

    View vista;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ContenedorFragment() {
        // Required empty public constructor
    }


    public static ContenedorFragment newInstance(String param1, String param2) {
        ContenedorFragment fragment = new ContenedorFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_contenedor, container, false);

        View parent = (View) container.getParent();

        if(appBar == null) {
            appBar = parent.findViewById(R.id.appBar);
            tabLayout = new TabLayout(getActivity());
            appBar.addView(tabLayout);

            //ahora instanciamos nuestro viewpager este si esta directamente en el fragment
            viewPager = vista.findViewById(R.id.viewPagerProductos);
            llenarViewPager(viewPager);
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            });

            tabLayout.setupWithViewPager(viewPager);
        }

        return vista;
    }

    private void llenarViewPager(ViewPager viewPager) {
        SeccionesAdapter seccionesAdapter = new SeccionesAdapter(getFragmentManager());
        seccionesAdapter.addFragment(new ProcesosFragment(), "Procesos");
        seccionesAdapter.addFragment(new MatanzasFragment(), "Matanzas");
        seccionesAdapter.addFragment(new ProductosFragment(), "Productos");
        viewPager.setAdapter(seccionesAdapter);
    }
}
