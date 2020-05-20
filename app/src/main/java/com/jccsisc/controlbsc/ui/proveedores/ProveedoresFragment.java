package com.jccsisc.controlbsc.ui.proveedores;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.activities.AddContactoActivity;
import com.jccsisc.controlbsc.activities.CrearUsuarioActivity;
import com.jccsisc.controlbsc.activities.LoginActivity;
import com.jccsisc.controlbsc.activities.MainActivity;
import com.jccsisc.controlbsc.activities.RegistrarE_S_C_Activity;
import com.jccsisc.controlbsc.adapters.ProveedorAdapter;
import com.jccsisc.controlbsc.adapters.ProveedorAdapter.ProveedoresViewHolder;
import com.jccsisc.controlbsc.dialogs.ModifyProductFragment;
import com.jccsisc.controlbsc.model.Proveedor;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ProveedoresFragment extends Fragment {

    private int position;
    private AlertDialog dialog;
    private ImageButton btnExit;
    private TextView nameContacto, numberPhone, lastName;
    public  ArrayList<Proveedor> proveedorArrayList;
    public ProveedorAdapter proveedorAdapter;
    public static Button btnEliminar;
    private static final int REQUEST_CALL = 1;
    public static ShimmerRecyclerViewX rvProveedores;

    public ProveedoresFragment() { }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_proveedores, container, false);
        rvProveedores  = v.findViewById(R.id.recyclerProveedores);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProveedores.setLayoutManager(linearLayoutManager);

        proveedorArrayList = new ArrayList<>();
        proveedorAdapter   = new ProveedorAdapter(proveedorArrayList, getActivity());

        rvProveedores.setAdapter(proveedorAdapter);
        rvProveedores.showShimmerAdapter();

        NodosFirebase.addContac = "addContac";
        MainActivity.fab.setImageResource(R.drawable.ic_add_user);

        NodosFirebase.myRefProveedor.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rvProveedores.hideShimmerAdapter();
                proveedorArrayList.clear();
//                proveedorArrayList.removeAll(proveedorArrayList);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Proveedor proveedor = new Proveedor(
                            snapshot.child("name").getValue(String.class),
                            snapshot.child("lastName").getValue(String.class),
                            snapshot.child("nameCompany").getValue(String.class),
                            snapshot.child("imgCompany").getValue(String.class),
                            snapshot.child("numberPhone").getValue(String.class),
                            snapshot.child("idKey").getValue(String.class));

                    proveedorArrayList.add(proveedor);
                }
                proveedorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        proveedorAdapter.setOnClickListener(new ProveedorAdapter.OnClickListener() {
           //marcar un numero
            @Override
            public void onItemClick(int pos) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else{
                        String phone = String.valueOf(proveedorArrayList.get(pos).getNumberPhone());
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CALL_PHONE, phone}, REQUEST_CALL);
                            return;
                        }
                        startActivity(intent);
                    }
                }

             //eliminar el contacto
            @Override
            public void onLongItemClick(int pos) {
                position = pos;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.AppCompatDialogStyle);
                View view = getLayoutInflater().inflate(R.layout.fragment_eliminar_contacto, null);
                alertDialog.setView(view);
                dialog = alertDialog.create();
                instancias(view);
                dialog.show();
            }

            //editar el contacto
            @Override
            public void openIntent(int pos) {
                Intent i = new Intent(getContext(), AddContactoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("modelo", proveedorArrayList.get(pos));
                i.putExtras(bundle);
                i.putExtra("type","edit");
                i.putExtra("idKey", proveedorArrayList.get(pos).getIdKey());
//                Log.e("idKey enviado",proveedorArrayList.get(pos).getIdKey());
                startActivity(i);
                Animatoo.animateZoom(getActivity());
            }

            //abrir whatssap
            @Override
            public void openWhatsapp(int pos) {
                String phone = String.valueOf(proveedorArrayList.get(pos).getNumberPhone());
                boolean installed = appInstalledOrNot("com.whatsapp");

                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+52"+ phone));
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "No tienes instalado Whatssap", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NodosFirebase.addContac = "other";
        MainActivity.fab.setImageResource(R.drawable.ic_mas);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_DENIED && requestCode == REQUEST_CALL) {

        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == REQUEST_CALL) {

            String phone = permissions[1];
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }

    //is intalled whatsaopp or not
    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean app_installed;

        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;

        }catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }

        return app_installed;
    }

    private void instancias(View v) {
        btnExit      = v.findViewById(R.id.btnExit);
        nameContacto = v.findViewById(R.id.txtNameContact);
        lastName     = v.findViewById(R.id.txtLastNameProveedor);
        numberPhone  = v.findViewById(R.id.txtPhoneNumber);
        btnEliminar  = v.findViewById(R.id.btnEliminar);

        nameContacto.setText(proveedorArrayList.get(position).getName());
        lastName.setText(proveedorArrayList.get(position).getLastName());
        numberPhone.setText(proveedorArrayList.get(position).getNumberPhone());

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NodosFirebase.myRefProveedor.child(proveedorArrayList.get(position).getIdKey()).removeValue();
                Toast.makeText(getContext(), "Eliminado",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


}
