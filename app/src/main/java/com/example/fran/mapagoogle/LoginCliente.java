package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginCliente extends AppCompatActivity {
    Button clienteBt;
    Button cadastroClie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cliente);

        clienteBt = (Button) findViewById(R.id.btn_acessarcli);
        cadastroClie = (Button) findViewById(R.id.btn_cadastrocli);

        cadastroClie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telacadastrocliente();
            }
        });
    }

    private void telacadastrocliente() {
        Intent intent = new Intent(LoginCliente.this, CdsUsuario.class);
        startActivity(intent);
    }

    public void telaOpcao(View view) {
    }

    public void fazerLogin(View view) {
    }
}
