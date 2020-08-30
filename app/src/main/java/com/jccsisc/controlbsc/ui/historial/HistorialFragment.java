package com.jccsisc.controlbsc.ui.historial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;
import com.jccsisc.controlbsc.ui.historial.show.ContenedorES;
import com.jccsisc.controlbsc.ui.home.entradas.ContenedorEntradas;
import com.jccsisc.controlbsc.utilidades.EventCalculator;

public class HistorialFragment extends Fragment {

    private String fecha;
    EventCalculator eventCalculator = new EventCalculator();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_historial, container, false);

        CalendarView calendarView= v.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                fecha = eventCalculator.fechaCompleta(dayOfMonth, month,year);

                Toast.makeText(getContext(), fecha, Toast.LENGTH_SHORT).show();

                Fragment nuevoFragmento = new ContenedorES(fecha);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return v;
    }
}
