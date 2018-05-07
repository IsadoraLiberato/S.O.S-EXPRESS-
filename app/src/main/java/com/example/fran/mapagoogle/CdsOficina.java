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
import com.example.fran.mapagoogle.util.CamposCdsOficina;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CdsOficina extends AppCompatActivity {

    private EditText edt_rua;
    private  EditText edt_numero;
    private  EditText edt_bairro;
    private EditText edt_cep;
    private  EditText edt_nomeFantasia;
    private  EditText edt_telefone;
    private  EditText edt_cnpj;
    private  EditText edt_email;
    private  EditText edt_senha;
    private EditText edt_confirmS_ofic;

    Oficina resOficina = new Oficina();
    Button cadOficina;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cds_oficina);

        cadOficina = (Button)findViewById(R.id.btn_cad_oficina);

        cadOficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Pegar as variaveis
                edt_rua =(EditText)findViewById(R.id.edt_rua_ofic);
                edt_numero =(EditText)findViewById(R.id.edt_numero_ofic);
                edt_bairro=(EditText)findViewById(R.id.edt_bairro_ofic);
                edt_cep=(EditText)findViewById(R.id.edt_cep_ofic);
                edt_nomeFantasia=(EditText)findViewById(R.id.edt_nomeF_ofic);
                edt_telefone=(EditText)findViewById(R.id.edt_tel_ofic);
                edt_cnpj=(EditText)findViewById(R.id.edt_cnpj_ofic);
                edt_email=(EditText)findViewById(R.id.edt_email_ofic);
                edt_senha=(EditText)findViewById(R.id.edt_senha_ofic);
                edt_confirmS_ofic=(EditText)findViewById(R.id.edt_confirmS_ofic);

                CamposCdsOficina camposCdsOficina = new CamposCdsOficina();
                camposCdsOficina.setEdt_rua(edt_rua);
                camposCdsOficina.setEdt_numero(edt_numero);
                camposCdsOficina.setEdt_bairro(edt_bairro);
                camposCdsOficina.setEdt_cep(edt_cep);
                camposCdsOficina.setEdt_nomeFantasia(edt_nomeFantasia);
                camposCdsOficina.setEdt_telefone(edt_telefone);
                camposCdsOficina.setEdt_cnpj(edt_cnpj);
                camposCdsOficina.setEdt_email(edt_email);
                camposCdsOficina.setEdt_senha(edt_senha);
                camposCdsOficina.setEdt_confirmS_ofic(edt_confirmS_ofic);

                Oficina ofic = new Oficina();
                ofic.setRua(camposCdsOficina.getEdt_rua().getText().toString());
                ofic.setNumero(camposCdsOficina.getEdt_numero().getText().toString());
                ofic.setBairro(camposCdsOficina.getEdt_bairro().getText().toString());
                ofic.setCep(camposCdsOficina.getEdt_cep().getText().toString());
                ofic.setNome(camposCdsOficina.getEdt_nomeFantasia().getText().toString());
                ofic.setTelefone(camposCdsOficina.getEdt_telefone().getText().toString());
                ofic.setCnpj(camposCdsOficina.getEdt_cnpj().getText().toString());
                ofic.setEmail(camposCdsOficina.getEdt_email().getText().toString());
                ofic.setSenha(camposCdsOficina.getEdt_senha().getText().toString());


                if(validaCampos(camposCdsOficina)){
                    Toast.makeText(CdsOficina.this, "Campos todos preenchidos", Toast.LENGTH_SHORT).show();
                    //Chamada do metodo do retrofit
                    retrofitConverter(ofic);
                    //Chamando o progress
                    progress = new ProgressDialog(CdsOficina.this);
                    progress.setTitle("Salavando dados..... ");
                    progress.show();
                }else {
                    Toast.makeText(CdsOficina.this, "Campos não foram todos preenchidos", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void retrofitConverter(Oficina oficina) {

        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);
        int num  = Integer.valueOf(oficina.getNumero());

        Call<Oficina> call = service.cadastarOficinas(oficina.getRua(),num, oficina.getBairro(),oficina.getCep(),
                                     oficina.getNome(),oficina.getTelefone(),oficina.getCnpj(),oficina.getEmail(),oficina.getSenha());

        call.enqueue(new Callback<Oficina>() {
            @Override
            public void onResponse(Call<Oficina> call, Response<Oficina> response) {
                if (response.isSuccessful()) {

                    Oficina oficina = response.body();



                    //verifica aqui se o corpo da resposta não é nulo
                    if (oficina != null) {


                        //resOficina.setRua(oficina.getRua());


                        progress.dismiss();
                        finish();
                        Intent intent = new Intent(CdsOficina.this, OpcaoLogin.class);
                        startActivity(intent);



                    } else {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(CdsOficina.this, OpcaoLogin.class);
                        startActivity(intent);
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    progress.dismiss();
                    finish();
                    Intent intent = new Intent(CdsOficina.this, OpcaoLogin.class);
                    startActivity(intent);
                }

            }
            @Override
            public void onFailure(Call<Oficina> call, Throwable t) {

                progress.dismiss();
                finish();
                Intent intent = new Intent(CdsOficina.this, OpcaoLogin.class);
                startActivity(intent);
            }
        });


    }

    private boolean validaCampos(CamposCdsOficina camposCdsOficina){
        boolean resultado = true;

        String rua = camposCdsOficina.getEdt_rua().getText().toString();
        String numero = camposCdsOficina.getEdt_numero().getText().toString();
        String bairro = camposCdsOficina.getEdt_bairro().getText().toString();
        String cep = camposCdsOficina.getEdt_cep().getText().toString();
        String nome = camposCdsOficina.getEdt_nomeFantasia().getText().toString();
        String telefone = camposCdsOficina.getEdt_telefone().getText().toString();
        String cnpj = camposCdsOficina.getEdt_cnpj().getText().toString();
        String email = camposCdsOficina.getEdt_email().getText().toString();
        String senha =camposCdsOficina.getEdt_senha().getText().toString();
        String senhaConfirm =camposCdsOficina.getEdt_confirmS_ofic().getText().toString();

        if("".equals(rua) || rua == null){
            camposCdsOficina.getEdt_rua().setError("Preencha o campo rua");
            resultado = false;
        }

        if("".equals(numero) || numero == null){
            camposCdsOficina.getEdt_numero().setError("Preencha o campo numero");
            resultado = false;
        }

        if("".equals(bairro) || bairro == null){
            camposCdsOficina.getEdt_bairro().setError("Preencha o campo bairro");
            resultado = false;
        }
        if("".equals(cep) || cep == null){
            camposCdsOficina.getEdt_cep().setError("Preencha o campo cep");
            resultado = false;
        }
        if("".equals(nome) || nome == null){
            camposCdsOficina.getEdt_nomeFantasia().setError("Preencha o campo nome");
            resultado = false;
        }
        if("".equals(telefone) || telefone == null){
            camposCdsOficina.getEdt_telefone().setError("Preencha o campo telefone");
            resultado = false;
        }
        if("".equals(cnpj) || cnpj == null){
            camposCdsOficina.getEdt_cnpj().setError("Preencha o campo cnpj");
            resultado = false;
        }
        if("".equals(email) || email == null){
            camposCdsOficina.getEdt_email().setError("Preencha o campo email");
            resultado = false;
        }
        if("".equals(senha) || senha == null){
            camposCdsOficina.getEdt_senha().setError("Preencha o campo senha");
            resultado = false;
        }

        if("".equals(senhaConfirm) || senhaConfirm == null){
            camposCdsOficina.getEdt_confirmS_ofic().setError("Preencha o campo senhaConfirm");
            resultado = false;
        }

        if(!senha.equals(senhaConfirm)){
            Toast.makeText(this, "As senhas não correspondem...", Toast.LENGTH_SHORT).show();
            resultado = false;
        }

        return resultado;
    }


}
