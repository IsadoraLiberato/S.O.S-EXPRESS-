package com.example.fran.mapagoogle;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fran.mapagoogle.entidade.Oficina;
import com.example.fran.mapagoogle.service.RespostaServidor;
import com.example.fran.mapagoogle.service.RetrofitService;
import com.example.fran.mapagoogle.service.ServiceGenerator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CdsOficina extends AppCompatActivity {

    private EditText edt_rua_ofic;
    private EditText edt_numero_ofic;
    private EditText edt_bairro_ofic;
    private EditText edt_cep_ofic;
    private EditText edt_nomeF_ofic;
    private EditText edt_telefone_ofic;
    private EditText edt_cnpj_ofic;
    private EditText edt_email_ofic;
    private Button btn_cad_ofic;
    private ProgressDialog progress;

    private Oficina oficina;

    RespostaServidor resposta = new RespostaServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cds_oficina);

        edt_rua_ofic = findViewById(R.id.edt_rua_ofic);
        edt_numero_ofic = findViewById(R.id.edt_numero_ofic);
        edt_bairro_ofic = findViewById(R.id.edt_bairro_ofic);
        edt_cep_ofic = findViewById(R.id.edt_cep_ofic);
        edt_nomeF_ofic = findViewById(R.id.edt_nomeF_ofic);
        edt_telefone_ofic = findViewById(R.id.edt_cnpj_ofic);
        edt_cnpj_ofic = findViewById(R.id.edt_rua_ofic);
        edt_email_ofic = findViewById(R.id.edt_email_ofic);
        btn_cad_ofic = findViewById(R.id.btn_cad_oficina);

        listenersButtons();

    }

    public void listenersButtons() {

        btn_cad_ofic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(getApplicationContext());
                progress.setTitle("enviando...");
                progress.show();

                oficina = new Oficina();
                oficina.setRua(edt_rua_ofic.getText().toString());
                oficina.setNumero(Integer.valueOf(edt_numero_ofic.getText().toString()));
                oficina.setBairro(edt_bairro_ofic.getText().toString());
                oficina.setCep(edt_cep_ofic.getText().toString());
                oficina.setNome(edt_nomeF_ofic.getText().toString());
                oficina.setTelefone(edt_telefone_ofic.getText().toString());
                oficina.setCnpj(edt_cnpj_ofic.getText().toString());
                oficina.setEmail(edt_email_ofic.getText().toString());



                //chama o retrofit para fazer a requisição no webservice
                retrofitConverter(oficina);

            }
        });
    }


    public void retrofitConverter(Oficina oficina) {

        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        Call<RespostaServidor> call = service.converterUnidade(oficina);

        call.enqueue(new Callback<RespostaServidor>() {
            @Override
            public void onResponse(Call<RespostaServidor> call, Response<RespostaServidor> response) {

                if (response.isSuccessful()) {

                    RespostaServidor respostaServidor = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {


                  if(respostaServidor.isValid()) {

                            resposta.setFrom_type(respostaServidor.getFrom_type());
                            resposta.setFrom_value(respostaServidor.getFrom_value());
                            resposta.setResult(respostaServidor.getResult());
                            resposta.setTo_type(respostaServidor.getTo_type());
                            resposta.setValid(respostaServidor.isValid());

                            progress.dismiss();


                        } else{

                            Toast.makeText(getApplicationContext(),"Insira unidade e valores válidos", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(),"Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<RespostaServidor> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
