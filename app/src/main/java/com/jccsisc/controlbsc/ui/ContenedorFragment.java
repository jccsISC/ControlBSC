package com.jccsisc.controlbsc.ui;

import android.graphics.Color;
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
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

public class ContenedorFragment extends Fragment {

    View vista;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ContenedorFragment() { }


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

        if (NodosFirebase.tab == 0) {
            View parent = (View) container.getRootView();
            if(appBar == null) {
                appBar = parent.findViewById(R.id.appBar);
                tabLayout = new TabLayout(getActivity());
                tabLayout.setTabTextColors(Color.parseColor("#DCDCDC"), Color.parseColor("#FFFFFF"));
                appBar.addView(tabLayout);

                //ahora instanciamos nuestro viewpager este si esta directamente en el fragment
                viewPager = vista.findViewById(R.id.viewPagerResumen);
                llenarViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
                tabLayout.setupWithViewPager(viewPager);
            }
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL); //para que las pestañas se distribullan en el tab al girar
        }else {
            NodosFirebase.tab = 1; //inicializamos en uno para que nos aparezca el tab y asi lo crea solo una vez
        }

        return vista;
    }

    @Override //eliminamos el tabLyout cada que salga del fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (NodosFirebase.tab == 0) {
            appBar.removeView(tabLayout);
        }
    }

    private void llenarViewPager(ViewPager viewPager) {
    SeccionesAdapter adapter = new SeccionesAdapter(getFragmentManager());
        adapter.addFragment(new ProcesosFragment(), "Procesos");
        adapter.addFragment(new MatanzasFragment(), "Matanzas");
        adapter.addFragment(new ProductosFragment(), "Productos");
        viewPager.setAdapter(adapter);
    }
}
