package com.jccsisc.controlbsc.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>{

    private ArrayList<Producto> productos_model;
    private Activity activity;
    private OnClickListener onClickListener = null;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProductosAdapter(ArrayList<Producto> productos_model, Activity activity) {
        this.productos_model = productos_model;
        this.activity = activity;
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

        holder.textNameProduct.setText(producto.getName());
        holder.textUnit.setText(producto.getUnit());

        holder.cardViewProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick( position);
            }
        });

        holder.textKg.setText(String.valueOf(sumaMovimiento(producto.getMovimientos())));
        holder.textCantidad.setText(String.valueOf(sumaCaja(producto.getMovimientos())));
    }


    double sumaMovimiento(ArrayList<Movimiento> recibido){
        double total = 0;


        for(Movimiento nuevo : recibido){
            total = total + (nuevo.getWeight());

        }

        return total;
    }

    int sumaCaja(ArrayList<Movimiento> recibido){
        int total = 0;

        for(Movimiento nuevo : recibido){
            total = total + (nuevo.getQuantity());

        }

        return total;
    }

    @Override
    public int getItemCount() {
        return productos_model.size();
    }

    //creamos nuestra inner class
    public  class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView textNameProduct, textUnit, textCantidad, textKg;
        CardView cardViewProducto;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewProducto = itemView.findViewById(R.id.cardViewProducto);
            textNameProduct = itemView.findViewById(R.id.textNameProducto);
            textCantidad    = itemView.findViewById(R.id.textCantidad);
            textUnit        = itemView.findViewById(R.id.textUnit);
            textKg          = itemView.findViewById(R.id.textKg);
        }


    }
    public interface OnClickListener {
        void onItemClick( int pos);
    }

}
