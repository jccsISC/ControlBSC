package com.jccsisc.controlbsc.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Producto;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>{

    private List<Producto> productos_model;
    private Activity activity;

    public ProductosAdapter(List<Producto> productos_model, Activity activity) {
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
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        //obtenemos la posicion de la lista
        Producto producto = productos_model.get(position);

        holder.textNameProduct.setText(producto.getName());
        holder.textUnit.setText(producto.getUnit());
        holder.textCantidad.setText(String.valueOf(producto.getQuantity()));
        holder.textKg.setText(String.valueOf(producto.getWeight()));
    }

    @Override
    public int getItemCount() {
        return productos_model.size();
    }

    //creamos nuestra inner class
    public  class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView textNameProduct, textUnit, textCantidad, textKg;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameProduct = itemView.findViewById(R.id.textNameProducto);
            textCantidad    = itemView.findViewById(R.id.textCantidad);
            textUnit        = itemView.findViewById(R.id.textUnit);
            textKg          = itemView.findViewById(R.id.textKg);
        }
    }
}
