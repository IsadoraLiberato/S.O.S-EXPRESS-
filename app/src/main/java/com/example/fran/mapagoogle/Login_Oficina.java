package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fran.mapagoogle.GeneratoRetrofit.RetrofitServiceGenerator;
import com.example.fran.mapagoogle.RestClient.RetrofitService;
import com.example.fran.mapagoogle.entidade.Usuario;
import com.example.fran.mapagoogle.util.Preferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Oficina extends AppCompatActivity {

    public class Login extends AppCompatActivity {


        private EditText edt_email;
        private EditText edt_senha;
        private Button btn_acessar;
        private Button btn_cadastro;

        private boolean resultado;


        Preferences preferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_oficina);

            edt_email = findViewById(R.id.edt_email_login);
            edt_senha = findViewById(R.id.edt_senha_login);
            btn_acessar = findViewById(R.id.btn_acessar);
            btn_cadastro = findViewById(R.id.btn_cadastro);

            verificaSeEstaLogado();

        }

        public void fazerLogin(View view) {

            String email = edt_email.getText().toString();
            String senha = edt_senha.getText().toString();
            retornaCliente(email);

            boolean valida = validaCampos(email, senha);
            Toast.makeText(this, "Resultado: " + resultado, Toast.LENGTH_SHORT).show();

            if (valida && resultado) {
                telamapa();
            }

        }


        public void telaOpcao(View view) {
            telaEscolhaCadastro();
        }

        private void telaEscolhaCadastro() {
            Intent intent = new Intent(Login_Oficina.this, CdsOficina.class);
            startActivity(intent);
        }

        private void telamapa() {
            Intent intent = new Intent(Login_Oficina.this, ActPrincipal.class);
            startActivity(intent);
        }

        private void verificaSeEstaLogado() {
            preferences = new Preferences(this);

            String email = preferences.getEmailCliente();
            String senha = preferences.getSenhaCliente();

            if (email != null && senha != null) {
                telamapa();
            }
        }

        public void retornaCliente(String email) {
            RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

            Call<Usuario> call = service.getCliente(email);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {

                        Usuario usuarioRetorn = response.body();


                        //verifica aqui se o corpo da resposta não é nulo
                        if (usuarioRetorn != null) {
                            resultado = true;
                            //resOficina.setRua(oficina.getRua());
                            //getUsuario(usuarioRetorn);

                            //progress.dismiss();


                        } else {
                            resultado = false;
                            //progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                        // segura os erros de requisição
                        ResponseBody errorBody = response.errorBody();
                        //progress.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("AppCep", "Não foi possível recuperar o Cep", t);

                }
            });

        }

        private boolean validaCampos(String email, String senha) {
            boolean result = true;

            if ("".equals(email) || email == null) {
                edt_email.setError("Campo obrigatório");
                result = false;
            }
            if (("".equals(email) || email == null) && ("".equals(senha) || senha == null)) {
                edt_email.setError("Campo obrigatório");
                edt_senha.setError("Campo obrigatório");
                result = false;
            }
            if ("".equals(senha) || senha == null) {
                edt_senha.setError("Campo obrigatório");
                result = false;
            }


            return result;

        }


    }
}
