package com.example.fran.mapagoogle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fran.mapagoogle.GeneratoRetrofit.RetrofitServiceGenerator;
import com.example.fran.mapagoogle.RestClient.RetrofitService;
import com.example.fran.mapagoogle.entidade.MensagemLogin;
import com.example.fran.mapagoogle.util.Preferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCliente extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_acessar;
    private Button btn_cadastro;

    private Preferences preferences;
    ProgressDialog progress;


    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cliente);

        preferences = new Preferences(LoginCliente.this);
        edt_email =  findViewById(R.id.edt_email_login);
        edt_senha =  findViewById(R.id.edt_senha_login);
        btn_acessar =  findViewById(R.id.btn_acessar);

        verificaSeEstaLogado();

    }

    public void fazerLogin(View view)
    {
        //Chamando o progress
        progress = new ProgressDialog(LoginCliente.this);
        progress.setTitle("Verificando dados..... ");
        progress.show();

        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        retornaCliente(email,senha);


    }


    private void telamapa (){
        Intent intent = new Intent(LoginCliente.this, ActPrincipal.class);
        intent.putExtra("id",preferences.getIdCliente());
        startActivity(intent);
        finish();
     }

    public void verificaSeEstaLogado(){

        String email = preferences.getEmailCliente();
        String senha = preferences.getSenhaCliente();

        if(email != null && senha != null){

            telamapa();
        }
    }
    private boolean resultado;

    public void retornaCliente(final String email, final String senha){

        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

        Call<MensagemLogin> call = service.getCliente(email);

        call.enqueue(new Callback<MensagemLogin>() {

            @Override
            public void onResponse(Call<MensagemLogin> call, Response<MensagemLogin> response) {
                if (response.isSuccessful()) {

                    MensagemLogin msgL = response.body();
                    preferences.salvaIdCliente(msgL.getId());

                    //verifica aqui se o corpo da resposta não é nulo
                    if(msgL != null){
                            Log.i("MensagemRetorno ", msgL.getMsg());

                        if (msgL.getMsg().equals("true") && msgL.getEmail().equals(email) && msgL.getSenha().equals(senha)){
                            resultado = true;
                            id_user = preferences.getIdCliente();
                            progress.dismiss();
                            Toast.makeText(LoginCliente.this, "Dados conferem", Toast.LENGTH_SHORT).show();

                           // Toast.makeText(this, ""+existeCliente, Toast.LENGTH_SHORT).show();
                            boolean valida = validaCampos(email,senha);


                            if( resultado && valida){

                                preferences.salvarDados(email,senha);

                                telamapa();
                            }else{
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginCliente.this);
                                alert.setTitle("Erro");
                                alert.setMessage("Os dados digitados não correspondem a nenhum cliente cadastrado\n" +
                                        " ou faltam campos a serem preenchidos");
                                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alert.create()
                                        .show();
                                progress.dismiss();
                            }


                        }else{
                            resultado = false;
                            Log.i("MensagemRetorno ", msgL.getMsg());
                            progress.dismiss();
                        }
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    //progress.dismiss();
                }

            }
            @Override
            public void onFailure(Call<MensagemLogin> call, Throwable t) {
                Log.e("AppCep", "Não foi possível recuperar o Cep", t);

            }
        });

        //return resultado;

    }

    private boolean validaCampos(String emailDig, String senhaDig){
        boolean result = true;

        if("".equals(emailDig) || emailDig == null){
            edt_email.setError("Campo obrigatório");
            result = false;
        }
        if(("".equals(emailDig) || emailDig == null) && ("".equals(senhaDig) || senhaDig == null)){
            edt_email.setError("Campo obrigatório");
            edt_senha.setError("Campo obrigatório");
            result = false;
        }
        if ("".equals(senhaDig) || senhaDig == null){
            edt_senha.setError("Campo obrigatório");
            result = false;
        }


        return  result;

    }


}
