package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Cliente extends AppCompatActivity {
 Button BotaoCadastro;
 Button BotaoAcesso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__cliente);

        BotaoAcesso = (Button) findViewById(R.id.btn_acessarcliente);
        BotaoCadastro = (Button) findViewById(R.id.btn_cadastrocliente);

        BotaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Cliente.this, CdsUsuario.class);
                startActivity(intent);
            }
        });
        BotaoAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
