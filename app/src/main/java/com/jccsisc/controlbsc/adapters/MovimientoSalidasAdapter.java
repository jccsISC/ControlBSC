package com.jccsisc.controlbsc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Movimiento;

import java.util.ArrayList;

public class MovimientoSalidasAdapter extends RecyclerView.Adapter<MovimientoSalidasAdapter.ProductosViewHolder>{

    private ArrayList<Movimiento> movimientos_model;
    private OnClickListener onClickListener = null;
    private Context context;
    private String unit, name;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MovimientoSalidasAdapter(ArrayList<Movimiento> movimientos_model, Context context, String unit, String name) {
        this.movimientos_model = movimientos_model;
        this.context = context;
        this.unit = unit;
        this.name = name;
    }

    @NonNull
    @Override //le creamos la vista para el recycler
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_salida_movimiento, parent, false);
        ProductosViewHolder holder = new ProductosViewHolder(v);
        return holder;
    }

    @Override //aqui el damos los valores
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, final int position) {

        //obtenemos la posicion de la lista
        holder.txtDateEntrada.setText(movimientos_model.get(position).getDate());
        holder.txtPiezas.setText(String.valueOf(movimientos_model.get(position).getQuantity()));

        holder.txtKilogramos.setText(String.valueOf(movimientos_model.get(position).getWeight()));

        holder.cardViewMovimientoSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick( position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movimientos_model.size();
    }

    //creamos nuestra inner class
    public  class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView txtDateEntrada, txtPiezas, txtKilogramos, txtDateEnvasado, txtPiezasEnvasado, txtKilogramosEnvasado;
        CardView cardViewMovimientoSalida;
        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewMovimientoSalida = itemView.findViewById(R.id.cardViewMovimientoSalida);
            txtDateEntrada           = itemView.findViewById(R.id.txtDateEntrada);
            txtPiezas                = itemView.findViewById(R.id.txtPiezas);
            txtKilogramos            = itemView.findViewById(R.id.txtKilogramos);
            txtDateEnvasado          = itemView.findViewById(R.id.txtDateEnvasado);
            txtPiezasEnvasado        = itemView.findViewById(R.id.txtPiezasEnvasado);
            txtKilogramosEnvasado    = itemView.findViewById(R.id.txtKilogramosEnvasado);
        }
    }
    public interface OnClickListener {
        void onItemClick(int pos);
    }

}
