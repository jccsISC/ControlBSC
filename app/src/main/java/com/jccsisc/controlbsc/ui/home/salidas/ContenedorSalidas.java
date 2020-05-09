package com.jccsisc.controlbsc.ui.home.salidas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.tabs.SeccionesAdapter;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

public class ContenedorSalidas extends Fragment {
    View vista;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ContenedorSalidas() {}

    //creamos nuestra instancia
    public static ContenedorSalidas newInstance() {

        Bundle args = new Bundle();

        ContenedorSalidas fragment = new ContenedorSalidas();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_contenedor, container, false);

        if (NodosFirebase.tab == 0) {
            View parent = (View) container.getRootView();
            if (appBar == null) {
                appBar  = parent.findViewById(R.id.appBar);
                tabLayout = new TabLayout(getActivity());
                tabLayout.setTabTextColors(Color.parseColor("#DCDCDC"), Color.parseColor("#ffffff"));
                appBar.addView(tabLayout);

                viewPager = vista.findViewById(R.id.viewPagerResumen);
                llenarViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
                tabLayout.setupWithViewPager(viewPager);
            }
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }else {
            NodosFirebase.tab = 1;
        }

        return vista;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (NodosFirebase.tab == 0) {
            appBar.removeView(tabLayout);
        }
    }

    private void llenarViewPager(ViewPager viewPager) {
        SeccionesAdapter adapter = new SeccionesAdapter(getChildFragmentManager());
        adapter.addFragment(new SalidasFragment(), "Salidas");
        adapter.addFragment(new ResumenSalidasFragment(), "Resumen");
        viewPager.setAdapter(adapter);
    }
}
