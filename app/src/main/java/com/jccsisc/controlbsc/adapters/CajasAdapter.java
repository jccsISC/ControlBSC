package com.jccsisc.controlbsc.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Detalle;

import java.util.ArrayList;

public class CajasAdapter extends RecyclerView.Adapter<CajasAdapter.ProductosViewHolder>{

    private ArrayList<Detalle> detalles_model;
    private Activity activity;
    private OnClickListener onClickListener = null;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CajasAdapter(ArrayList<Detalle> productos_model, Activity activity) {
        this.detalles_model = productos_model;
        this.activity = activity;
    }

    @NonNull
    @Override //le creamos la vista para el recycler
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cajas, parent, false);
        ProductosViewHolder holder = new ProductosViewHolder(v);
        return holder;
    }

    @Override //aqui el damos los valores
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, final int position) {
        //obtenemos la posicion de la lista
        holder.txtPesoCaja.setText(String.valueOf(detalles_model.get(position).getPeso()));
        holder.txtNumberCaja.setText(String.valueOf(position + 1));

        holder.cardCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick( position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return detalles_model.size();
    }

    //creamos nuestra inner class
    public  class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView txtPesoCaja, txtNumberCaja;
        CardView cardCaja;
        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardCaja = itemView.findViewById(R.id.cardCaja);
            txtPesoCaja = itemView.findViewById(R.id.txtPesoCaja);
            txtNumberCaja = itemView.findViewById(R.id.txtNumberCaja);
        }
    }
    public interface OnClickListener {
        void onItemClick(int pos);
    }

}
