package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.ui.productos.ProductosFragment;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextView txtNameUser, txtPuestoUser;
    private AppBarConfiguration mAppBarConfiguration;
    public static EditText edtAppBar;
    private static final int REQUEST_CALL = 1;
    Intent intent;
    public static ConstraintLayout layoutFabGroup, layoutFabGroupAddContac;
    public static FloatingActionsMenu fabGroup, fabGroupAddContact;
    public static FloatingActionButton fabAddProduct, fabCall, fabEmail, fabWahtsapp, fabAddProveedor, fabAddContactPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar();


        fabAddProduct           = findViewById(R.id.fabAddProduct);
        fabGroup                = findViewById(R.id.fabGroup);
        fabCall                 = findViewById(R.id.fabCall);
        fabEmail                = findViewById(R.id.fabEmail);
        fabWahtsapp             = findViewById(R.id.fabWahtsapp);
        layoutFabGroup          = findViewById(R.id.layoutFabGroup);
        fabAddProveedor         = findViewById(R.id.fabAddProveedor);
        fabAddContactPhone      = findViewById(R.id.fabAddContactPhone);
        fabGroupAddContact      = findViewById(R.id.fabGroupAddContact);
        layoutFabGroupAddContac = findViewById(R.id.layoutFabGroupAddContac);


        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Estamos trabajando en esta funcionalidad", Toast.LENGTH_SHORT).show();
                fabGroup.collapse();
            }
        });

        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SendEmailActivity.class);
                startActivity(i);
                Animatoo.animateShrink(MainActivity.this);
                fabGroup.collapse();
            }
        });

        fabWahtsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "3222068332";
                boolean installed = appInstalledOrNot("com.whatsapp");

                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+52"+ phone));
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "No tienes instalado Whatssap", Toast.LENGTH_SHORT).show();
                }
                fabGroup.collapse();
            }
        });


        fabAddContactPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Estamos trabajando en esta funcionalidad", Toast.LENGTH_SHORT).show();
                fabGroupAddContact.collapse();
            }
        });

        fabAddProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddContactoActivity.class);
                i.putExtra("type","new");
                startActivity(i);
                Animatoo.animateShrink(MainActivity.this);
                fabGroupAddContact.collapse();
            }
        });

        edtAppBar = findViewById(R.id.edtAppBarBuscador);
        edtAppBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {

                    //aqui tenemos que hacer un siwtch para saber en que fragment está y así mandarle al metdo buscar de la clase correcta o un metodo

                    ProductosFragment.metodoBuscar(s); //le mandamos el texto a los adapter y recycler que mostraran la info

                }else{
                    //igual aqui o un metodo que sepa mandar los fragment segun en el que se encuentra
                    ProductosFragment.rvProductos.setAdapter(ProductosFragment.productosAdapter);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_productos, R.id.nav_matanzas, R.id.nav_procesos,
                R.id.nav_pedidos, R.id.nav_historial, R.id.nav_tickets, R.id.nav_camaras, R.id.nav_inventario,
                R.id.nav_proveedores, R.id.nav_contactanos)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        PackageManager packageManager = getApplicationContext().getPackageManager();
        boolean app_installed;

        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;

        }catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }

        return app_installed;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_perfil:
                intent = new Intent(getApplicationContext(), CrearUsuarioActivity.class);
                startActivity(intent);
                Animatoo.animateSplit(MainActivity.this);
                return true;
            case R.id.action_exit:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
