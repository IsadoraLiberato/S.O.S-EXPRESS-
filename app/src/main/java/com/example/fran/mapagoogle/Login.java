package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fran.mapagoogle.util.Preferences;

public class Login extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_acessar;
    private Button btn_cadastro;

    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email =  findViewById(R.id.edt_email_login);
        edt_senha =  findViewById(R.id.edt_senha_login);
        btn_acessar =  findViewById(R.id.btn_acessar);
        btn_cadastro =  findViewById(R.id.btn_cadastro);

        verificaSeEstaLogado();
    }

    public void fazerLogin(View view)
    {
        this.telamapa ();
    }


    public void telaOpcao(View view){
        telaEscolhaCadastro();
    }

    private void telaEscolhaCadastro(){
        Intent intent = new Intent(Login.this, CadastroOficina.class);
        startActivity(intent);
        finish();
    }

    private void telamapa (){
        Intent intent = new Intent(Login.this, ActPrincipal.class);
        startActivity(intent);
        finish();

     }

    private void verificaSeEstaLogado(){
        preferences = new Preferences(this);

        String email = preferences.getEmailCliente();
        String senha = preferences.getSenhaCliente();

        if(email != null && senha != null){
            telamapa();
        }
    }
    private boolean validaCampos(String email, String senha){
        boolean result = true;

        if("".equals(email) || email == null){
            edt_email.setError("Campo obrigatório");
            result = false;
        }
        if(("".equals(email) || email == null) && ("".equals(senha) || senha == null)){
            edt_email.setError("Campo obrigatório");
            edt_senha.setError("Campo obrigatório");
            result = false;
        }
        if ("".equals(senha) || senha == null){
            edt_senha.setError("Campo obrigatório");
            result = false;
        }


        return  result;

    }

}
