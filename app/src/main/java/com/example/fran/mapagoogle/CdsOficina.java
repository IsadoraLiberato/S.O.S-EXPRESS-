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

                String rua = edt_rua.getText().toString();
                String numero = edt_numero.getText().toString();
                int numeroFinal = Integer.parseInt(numero);
                String bairro =edt_bairro.getText().toString();
                String cep =edt_cep.getText().toString();
                String nomeFantasia =edt_nomeFantasia.getText().toString();
                String telefone =edt_telefone.getText().toString();
                String cnpj =edt_cnpj.getText().toString();
                String email =edt_email.getText().toString();
                String senha =edt_senha.getText().toString();

                //Chamada do metodo do retrofit
                retrofitConverter( rua, numeroFinal, bairro,cep,nomeFantasia,telefone, cnpj,email,senha);

                //Chamando o progress
                progress = new ProgressDialog(CdsOficina.this);
                progress.setTitle("Salavando dados..... ");
                progress.show();
            }
        });

    }

    public void retrofitConverter( String rua,int numero, String bairro,String cep,String nFantasia,String telefone, String cnpj,String email,String senha) {

        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

        Call<Oficina> call = service.cadastarOficinas(rua,numero, bairro,cep,nFantasia,telefone,cnpj,email,senha);

        call.enqueue(new Callback<Oficina>() {
            @Override
            public void onResponse(Call<Oficina> call, Response<Oficina> response) {
                if (response.isSuccessful()) {

                    Oficina oficina = response.body();
                   // Log.i("AppOficina", "Response = "+response);
                    //Log.i("AppOficina", "Body = "+response.body());

                     //String rua =  oficina.getBairro();
                     //Log.i("AppOficina", "Body = "+rua);
                    //verifica aqui se o corpo da resposta não é nulo
                    if (oficina != null) {


                        //resOficina.setRua(oficina.getRua());


                        progress.dismiss();
                        Intent intent = new Intent(CdsOficina.this, Login.class);
                        startActivity(intent);
                        finish();


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
            public void onFailure(Call<Oficina> call, Throwable t) {
                Log.e("NotRetorn", "Não tivemos retorno da API", t);
                progress.dismiss();
                Intent intent = new Intent(CdsOficina.this, Login.class);
                startActivity(intent);
                finish();

            }
        });


    }


}
