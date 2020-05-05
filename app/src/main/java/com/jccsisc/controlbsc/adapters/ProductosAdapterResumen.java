package com.jccsisc.controlbsc.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.utilidades.Aritmetica;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductosAdapterResumen extends RecyclerView.Adapter<ProductosAdapterResumen.ProductosViewHolder>{

    private ArrayList<Producto> productos_model;
    private Activity activity;
    private String vista; //para saber si es la entrada o la salida
    DecimalFormat df = new DecimalFormat("0.00");

    public ProductosAdapterResumen(ArrayList<Producto> productos_model, Activity activity, String vista) {
        this.productos_model = productos_model;
        this.activity = activity;
        this.vista = vista;
    }

    @NonNull
    @Override //le creamos la vista para el recycler
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_resumen, parent, false);
        ProductosViewHolder holder = new ProductosViewHolder(v);
        return holder;
    }

    @Override //aqui el damos los valores
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, final int position) {
        //obtenemos la posicion de la lista
        final Producto producto = productos_model.get(position);

        holder.textNameProduct.setText(producto.getName());
        if(producto.getUnit().equals("Caja")) {
            holder.imgUnit.setBackground(activity.getDrawable(R.drawable.ic_caja));
        }else {
            holder.imgUnit.setBackground(activity.getDrawable(R.drawable.ic_carne));
        }

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
        TextView textNameProduct, textCantidad, textKg;
        CardView cardView;
        ImageView  imgUnit;
        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView         = itemView.findViewById(R.id.cardViewProductoR);
            cardView.setElevation(0);
            textNameProduct  = itemView.findViewById(R.id.textNameProductoR);
            textCantidad     = itemView.findViewById(R.id.textCantidadR);
            imgUnit          = itemView.findViewById(R.id.imgUnit);
            textKg           = itemView.findViewById(R.id.textKgR);
        }
    }
}
