package com.jccsisc.controlbsc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jccsisc.controlbsc.R;

public class SendEmailActivity extends AppCompatActivity {

    TextView txtMyEmailCompany, txtEmailUser;
    EditText edtAsunto, edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        myToolbar("Enviar Correo Electronico");

        txtEmailUser      = findViewById(R.id.txtEmailUser);
        txtMyEmailCompany = findViewById(R.id.txtMyEmailCompany);
        edtAsunto         = findViewById(R.id.edtAsunto);
        edtMessage        = findViewById(R.id.edtMensaje);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnSend) {

            String enviarcorreo = "silva.jccs18@gmail.com";
            String enviarasunto = edtAsunto.getText().toString();
            String enviarmensaje = edtMessage.getText().toString();

            if (isEmpty(enviarasunto) & isEmptyMessage(enviarmensaje)) {
                // Defino mi Intent y hago uso del objeto ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);

                // Defino los Strings Email, Asunto y Mensaje con la función putExtra
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { enviarcorreo });
                intent.putExtra(Intent.EXTRA_SUBJECT, enviarasunto);
                intent.putExtra(Intent.EXTRA_TEXT, enviarmensaje);

                // Establezco el tipo de Intent
                intent.setType("message/rfc822");

                // Lanzo el selector de cliente de Correo
                startActivity(
                        Intent.createChooser(intent,
                                "Elije un cliente de Correo:"));
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void myToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);//para que funcione vete al manifest agregalo
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isEmpty(String asunto) {
        if (asunto.isEmpty()) {
            edtAsunto.setHintTextColor(getResources().getColor(R.color.colorPieza));
            return false;
        }

        return true;
    }

    private boolean isEmptyMessage(String message) {
        if (message.isEmpty()) {
            edtMessage.setHintTextColor(getResources().getColor(R.color.colorPieza));
            return false;
        }
        return true;
    }
}