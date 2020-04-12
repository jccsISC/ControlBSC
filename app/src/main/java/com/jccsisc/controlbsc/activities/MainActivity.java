package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.model.Producto;
import com.jccsisc.controlbsc.ui.productos.ProductosFragment;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.jccsisc.controlbsc.R.id.nav_cerrarsesion;
import static com.jccsisc.controlbsc.R.id.nav_controller_view_tag;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static EditText edtAppBarBuscador;
    public String fragment_text = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarProductoActivity.class);
                startActivity(intent);
            }
        });

        edtAppBarBuscador = findViewById(R.id.edtAppBarBuscador); //instanciamos nuestro buscador

        edtAppBarBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    ProductosFragment.metodoBuscar(s); //le mandamos el texto a los adapter y recycler que mostraran la info
                }else{
                    ProductosFragment.rvProductos.setAdapter(ProductosFragment.productosAdapter);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        visivilitySearch(fragment_text); //para que no muestre el buscador en la vista home

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_productos, R.id.nav_matanzas, R.id.nav_procesos,
                R.id.nav_pedidos, R.id.nav_historial, R.id.nav_inventario, R.id.nav_contactanos, nav_cerrarsesion)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //para ocultar el buscador del toolbar segun el fragment en el que s encuentre
    public static void visivilitySearch(String fragment) {

        switch (fragment) {
            case "Entradas":
            case "Salidas":
            case "Productos":
                edtAppBarBuscador.setVisibility(View.VISIBLE);
                break;
            default:
                edtAppBarBuscador.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
