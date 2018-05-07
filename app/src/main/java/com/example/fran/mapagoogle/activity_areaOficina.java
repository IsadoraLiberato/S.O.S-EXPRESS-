package com.example.fran.mapagoogle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fran.mapagoogle.GeneratoRetrofit.RetrofitServiceGenerator;
import com.example.fran.mapagoogle.RestClient.RetrofitService;
import com.example.fran.mapagoogle.entidade.Oficina;
import com.example.fran.mapagoogle.entidade.Registro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_areaOficina extends AppCompatActivity {
    private final int MODE = 0;
    private final String NOME_ARQUIVO = "sos.preferences";

    private List<Registro> listaDados;
    private List<Registro> listaRegistro;
    private ProgressDialog progress;
    private  int idOficina;
    private  String nomeOficina;

    Button btn_sai;
    Button btn_verMap;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_oficina);

        btn_sai =(Button)findViewById(R.id.btn_sair);
        btn_verMap =(Button)findViewById(R.id.btn_verMap);

        //pegar os dados do Adapter
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idOficina = bundle.getInt("id");
        //String nomeUser= bundle.getString("nome");


        //Chamando o progress
        progress = new ProgressDialog(activity_areaOficina.this);
        progress.setTitle("Carregando sua lista... ");
        progress.show();



        //setando o recycler
        recycler = (RecyclerView) findViewById(R.id.rec_listaChamado);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

       //pegar a lista da api
       listaDados = retornaListRegistro(idOficina);


       btn_sai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferences.Editor editor = getSharedPreferences(NOME_ARQUIVO,MODE).edit();
               editor.remove(com.example.fran.mapagoogle.util.Preferences.EMAIL_OFICINA);
               editor.remove(com.example.fran.mapagoogle.util.Preferences.SENHA_OFICINA);
               editor.remove(com.example.fran.mapagoogle.util.Preferences.ID_OFICINA);
               editor.commit();
               finish();
               Intent intent = new Intent(activity_areaOficina.this, OpcaoLogin.class);
               startActivity(intent);

           }
       });

    }

    public List<Registro> retornaListRegistro(final int id) {


        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

        Call<List<Registro>> response = service.getListaRegistro(id);
        response.enqueue(new Callback<List<Registro>>(){
            @Override
            public void onResponse(Call <List<Registro>> call, Response <List<Registro>> response) {
                if (response.isSuccessful()) {
                    listaRegistro = response.body();

                    //setar lista no recycler
                    AdapterDados adapter = new AdapterDados(listaRegistro,getBaseContext());
                    recycler.setAdapter(adapter);

                    progress.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<Registro>> call, Throwable t) {
                progress.dismiss();
            }

        });
        return listaRegistro;
    }

}
