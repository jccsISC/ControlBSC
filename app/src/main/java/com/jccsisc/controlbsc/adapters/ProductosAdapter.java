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
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.Aritmetica;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>{

    private ArrayList<Producto> productos_model;
    private Activity activity;
    private OnClickListener onClickListener = null;
    private String vista; //para saber si es la entrada o la salida
    DecimalFormat df = new DecimalFormat("0.00");

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProductosAdapter(ArrayList<Producto> productos_model, Activity activity, String vista) {
        this.productos_model = productos_model;
        this.activity = activity;
        this.vista = vista;
    }

    @NonNull
    @Override //le creamos la vista para el recycler
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_producto, parent, false);
        ProductosViewHolder holder = new ProductosViewHolder(v);
        return holder;
    }

    @Override //aqui el damos los valores
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, final int position) {
        //obtenemos la posicion de la lista
        final Producto producto = productos_model.get(position);

        holder.textNameProducto.setText(producto.getName());
        if(producto.getUnit().equals("Caja")) {
            holder.textUnit.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.textUnit.setTextColor(activity.getResources().getColor(R.color.colorPieza));
        }

        holder.textUnit.setText(producto.getUnit());

        holder.cardViewProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick( position);
            }
        });

        holder.cardViewProducto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener == null) return false;
                onClickListener.onLongItemClick(position);
                return true;
            }
        });

        holder.textKg.setText(df.format(Aritmetica.sumaMovimiento(producto.getMovimientos())));
        if(vista.equals("Entrada")) {
            holder.textCantidad.setText(String.valueOf(Aritmetica.sumaCajaFecha(producto.getMovimientos())));
            holder.textKg.setText(String.valueOf(Aritmetica.sumaMovimientoFecha(producto.getMovimientos())));
        }else {
            holder.textCantidad.setText(String.valueOf(Aritmetica.sumaCaja(producto.getMovimientos())));
            holder.textKg.setText(String.valueOf(Aritmetica.sumaMovimiento(producto.getMovimientos())));
        }

    }


    @Override
    public int getItemCount() {
        return productos_model.size();
    }

    //creamos nuestra inner class
    public  class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView textNameProducto, textUnit, textCantidad, textKg;
        CardView cardViewProducto;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewProducto = itemView.findViewById(R.id.cardViewProducto);
            textNameProducto  = itemView.findViewById(R.id.textNameProducto);
            textCantidad     = itemView.findViewById(R.id.textCantidad);
            textUnit         = itemView.findViewById(R.id.textUnit);
            textKg           = itemView.findViewById(R.id.textKg);
        }
    }

    public interface OnClickListener {
        void onItemClick( int pos);
        void onLongItemClick(int pos);
    }

}
