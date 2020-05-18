package com.jccsisc.controlbsc.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.AddContactoActivity;
import com.jccsisc.controlbsc.model.Proveedor;

import java.util.ArrayList;

public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ProveedoresViewHolder> {

    ArrayList<Proveedor> proveedor_model;
    private Activity activity;
    private OnClickListener onClickListener = null;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProveedorAdapter(ArrayList<Proveedor> proveedor_model, Activity activity) {
        this.proveedor_model = proveedor_model;
        this.activity = activity;
    }

    @NonNull
    @Override //le creamos la vista para el recycler
    public ProveedoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_proveedores, parent, false);
        ProveedoresViewHolder holder = new ProveedoresViewHolder(v);
        return holder;
    }

    @Override //aqui el damos los valores
    public void onBindViewHolder(@NonNull final ProveedoresViewHolder holder, final int position) {
        //obtenemos la posicion de la lista
        holder.linearLayoutExpandible.setVisibility(View.GONE);
        holder.linearLayoutExpandible.setEnabled(false);
        final Proveedor proveedor = proveedor_model.get(position);

        holder.txtnameProveedor.setText(proveedor.getName());
        holder.txtLastName.setText(proveedor.getLastName());
        holder.txtNameCompany.setText(proveedor.getNameCompany());
        holder.txtPhoneNumber.setText(proveedor.getNumberPhone() + "");

        holder.layoutWhatssap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.openWhatsapp(position);
            }
        });

        holder.layoutEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.openIntent( position);
            }
        });

        holder.layoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick( position);
            }
        });





        holder.cardViewProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linearLayoutExpandible.isEnabled()) {
                    holder.imgFlechaExpandir.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    holder.linearLayoutExpandible.setVisibility(View.GONE);
                    holder.linearLayoutExpandible.setEnabled(false);
                }else {
                    holder.imgFlechaExpandir.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    holder.linearLayoutExpandible.setVisibility(View.VISIBLE);
                    holder.linearLayoutExpandible.setEnabled(true);
                }
            }
        });

        holder.cardViewProveedor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener == null) return false;
                onClickListener.onLongItemClick(position);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return proveedor_model.size();
    }

    //creamos nuestra inner class
    public static class ProveedoresViewHolder extends RecyclerView.ViewHolder {
        TextView txtnameProveedor, txtLastName, txtNameCompany, txtPhoneNumber;
        CardView cardViewProveedor;
        ImageView imgCompany, imgFlechaExpandir;
        LinearLayout linearLayoutExpandible, layoutCall, layoutEditContact, layoutWhatssap;

        public ProveedoresViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutCall        = itemView.findViewById(R.id.layoutCall);
            imgCompany        = itemView.findViewById(R.id.imgvProveedor);
            layoutWhatssap    = itemView.findViewById(R.id.layoutWhattsap);
            layoutEditContact = itemView.findViewById(R.id.layoutEditContact);
            txtnameProveedor  = itemView.findViewById(R.id.txtNameProveedor);
            txtLastName       = itemView.findViewById(R.id.txtLastNameProveedor);
            txtNameCompany    = itemView.findViewById(R.id.txtNameCompany);
            txtPhoneNumber    = itemView.findViewById(R.id.txtPhoneNumber);
            imgFlechaExpandir = itemView.findViewById(R.id.imgFlechaExpandir);
            cardViewProveedor = itemView.findViewById(R.id.cardViewProveedores);
            linearLayoutExpandible = itemView.findViewById(R.id.linearLayoutExpandible);
        }
    }

    public interface OnClickListener {
        void onItemClick(int pos);
        void onLongItemClick(int pos);
        void openIntent(int pos);
        void openWhatsapp(int pos);
    }

}
