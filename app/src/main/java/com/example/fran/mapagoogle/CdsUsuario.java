package com.example.fran.mapagoogle;

import android.app.ProgressDialog;
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
import com.example.fran.mapagoogle.entidade.Oficina;
import com.example.fran.mapagoogle.entidade.Usuario;
import com.example.fran.mapagoogle.util.Preferences;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CdsUsuario extends AppCompatActivity {
    private EditText edt_nome_usu;
    private EditText edt_cpf_usu;
    private EditText edt_email_usu;
    private EditText edt_telefone_usu;
    private EditText edt_senha_usu;
    private EditText edt_senhaConfirm_usu;
    private Button btn_cad_usu;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cds_usuario);

        edt_nome_usu = findViewById(R.id.edt_nome_usu);
        edt_cpf_usu = findViewById(R.id.edt_cpf_usu);
        edt_email_usu = findViewById(R.id.edt_email_usu);
        edt_telefone_usu = findViewById(R.id.edt_telefone_usu);
        edt_senha_usu = findViewById(R.id.edt_senha_usu);
        edt_senhaConfirm_usu = findViewById(R.id.edt_confirmS_usu);
        btn_cad_usu = findViewById(R.id.btn_cad_usu);


    }

    public void cadastraUser(View view){
        String nome = edt_nome_usu.getText().toString();
        String cpf  = edt_cpf_usu.getText().toString();
        String email = edt_email_usu.getText().toString();
        String telefone = edt_telefone_usu.getText().toString();
        String senha = edt_senha_usu.getText().toString();
        String confS = edt_senhaConfirm_usu.getText().toString();

        Usuario user = new Usuario();
        user.setNome(nome);
        user.setCpf(cpf);
        user.setEmail(email);
        user.setTelefone(telefone);
        user.setSenha(senha);
        user.setConfirmSen(confS);

        cadastraCliente(user.getNome(),user.getEmail(),user.getTelefone(),user.getCpf(),user.getSenha());


        Preferences preferences = new Preferences(this);
        preferences.salvarDados(user.getEmail(),user.getSenha());

        Toast.makeText(this, "Email: "+preferences.getEmailCliente()+" Senha: "+preferences.getSenhaCliente(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CdsUsuario.this, Login_Oficina.class);
        startActivity(intent);

        //Chamando o progress
        progress = new ProgressDialog(CdsUsuario.this);
        progress.setTitle("Salavando dados..... ");
        progress.show();

    }

    public void cadastraCliente( String nome,String email, String telefone,String cpf,String senha) {

        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

        Call<Usuario> call = service.cadastrarCliente(nome,email, telefone,cpf,senha);

        call.enqueue(new Callback<Usuario>() {
            private Call<Usuario> call;
            private Response<Usuario> response;

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                this.call = call;
                this.response = response;
                if (response.isSuccessful()) {

                    Usuario usuario = response.body();

                    Toast.makeText(CdsUsuario.this, "NomeCli: "+usuario.getNome(), Toast.LENGTH_SHORT).show();
                    String rua =  usuario.getNome();
                    Log.i("AppCliente", "Body = "+rua);
                    //verifica aqui se o corpo da resposta não é nulo
                    if (usuario != null) {

                        //resOficina.setRua(oficina.getRua());

                        progress.dismiss();


                    } else {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    progress.dismiss();
                }

            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("AppCep", "Não foi possível recuperar o Cep", t);
                progress.dismiss();
            }
        });


    }

}
