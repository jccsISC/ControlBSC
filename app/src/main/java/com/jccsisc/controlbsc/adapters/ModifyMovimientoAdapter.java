package com.jccsisc.controlbsc.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;
import java.util.BitSet;

public class ModifyMovimientoAdapter extends RecyclerView.Adapter<ModifyMovimientoAdapter.ProductosViewHolder>{

    private ArrayList<Movimiento> movimientos_model;
    private OnClickListener onClickListener = null;
    private Context context;
    private String unit, name;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ModifyMovimientoAdapter(ArrayList<Movimiento> movimientos_model, Context context, String unit, String name) {
        this.movimientos_model = movimientos_model;
        this.context = context;
        this.unit = unit;
        this.name = name;
    }

    @NonNull
    @Override //le creamos la vista para el recycler
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_modifi_movimiento, parent, false);
        ProductosViewHolder holder = new ProductosViewHolder(v);
        return holder;
    }

    @Override //aqui el damos los valores
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, final int position) {

        //obtenemos la posicion de la lista
        holder.txtName.setText(name);
        holder.txtHourE.setText(String.valueOf(movimientos_model.get(position).getHour()));

        if(unit.equals("Caja")) {
            holder.imgUnit.setBackground(context.getDrawable(R.drawable.ic_caja));
        }else {
            holder.imgUnit.setBackground(context.getDrawable(R.drawable.ic_carne));
        }

        holder.txtCantidad.setText(String.valueOf(movimientos_model.get(position).getQuantity()));
        holder.txtPeso.setText(String.valueOf(movimientos_model.get(position).getWeight()));

        holder.cardMovimiento.setOnClickListener(new View.OnClickListener() {
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
        TextView txtName, txtHourE, txtCantidad, txtPeso;
        ImageView imgUnit;
        CardView cardMovimiento;
        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardMovimiento = itemView.findViewById(R.id.cardViewMovimiento);
            txtName     = itemView.findViewById(R.id.textNameProductoM);
            txtHourE    = itemView.findViewById(R.id.textHoraProductoM);
            imgUnit     = itemView.findViewById(R.id.imgUnitM);
            txtCantidad = itemView.findViewById(R.id.textCantidadM);
            txtPeso     = itemView.findViewById(R.id.textKgM);

        }
    }
    public interface OnClickListener {
        void onItemClick(int pos);
    }

}
