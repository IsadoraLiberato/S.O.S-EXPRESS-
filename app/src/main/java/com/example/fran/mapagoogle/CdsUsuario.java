package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fran.mapagoogle.util.Preferences;

public class CdsUsuario extends AppCompatActivity {
    private EditText edt_nome_usu;
    private EditText edt_cpf_usu;
    private EditText edt_email_usu;
    private EditText edt_senha_usu;
    private EditText edt_senhaConfirm_usu;
    private Button btn_cad_usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cds_usuario);

        edt_nome_usu = findViewById(R.id.edt_nome_usu);
        edt_cpf_usu = findViewById(R.id.edt_cpf_usu);
        edt_email_usu = findViewById(R.id.edt_email_usu);
        edt_senha_usu = findViewById(R.id.edt_senha_usu);
        edt_senhaConfirm_usu = findViewById(R.id.edt_confirmS_usu);
        btn_cad_usu = findViewById(R.id.btn_cad_usu);


    }

    public void cadastraUser(View view){
        String email = edt_email_usu.getText().toString();
        String senha = edt_senha_usu.getText().toString();

        Preferences preferences = new Preferences(this);
        preferences.salvarDados(email,senha);

        Toast.makeText(this, "Email: "+preferences.getEmailCliente()+"Senha: "+preferences.getSenhaCliente(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CdsUsuario.this, Login.class);
        startActivity(intent);

    }
}
