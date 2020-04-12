package com.jccsisc.controlbsc.ui.tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.MainActivity;

public class TicketsFragment extends Fragment {

    public String fragment_text = "Tickets";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tickets, container, false);
        final TextView textView = root.findViewById(R.id.text_entradas);
        textView.setText("Tickets");

        MainActivity.visivilitySearch(fragment_text);

        return root;
    }
}
