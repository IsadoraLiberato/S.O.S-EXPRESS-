package com.example.fran.mapagoogle.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "sos.preferences";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private static final String NOME_CLIENTE = "email_cliente";
    private static final String SENHA_CLIENTE = "senha_cliente";


    public Preferences( Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDados( String email_cliente, String senha_cliente ){

        editor.putString(NOME_CLIENTE, email_cliente);
        editor.putString(SENHA_CLIENTE, senha_cliente);
        editor.commit();

    }

    public String getNomeCliente(){
        return preferences.getString(NOME_CLIENTE, null);
    }

    public String getSenhaCliente(){
        return preferences.getString(SENHA_CLIENTE, null);
    }


}
