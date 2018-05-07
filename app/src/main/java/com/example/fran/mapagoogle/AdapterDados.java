package com.example.fran.mapagoogle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fran.mapagoogle.entidade.Registro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handerson on 05/05/18.
 */

public class AdapterDados extends RecyclerView.Adapter<AdapterDados.ViewHolderDados>{
    List<Registro> listaDados;
    private final Context context;


    public AdapterDados(List <Registro> listaDados,final Context context) {
        this.listaDados = listaDados;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolderDados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Setando o layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);

        return new ViewHolderDados(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDados holder, final int position) {
        //responsavel pela comunicação
        holder.asignarDados(listaDados.get(position));

        holder.btn_verMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro registro;
                registro = getItem(position);
                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putString("lat", registro.getLatUser());
                bundle.putString("log", registro.getLogUser());
                bundle.putString("nome", registro.getNome());

                intent = new Intent(context, ActMapOficina.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //tamanho da lista
        return listaDados.size();
    }
    public Registro getItem(int position)
    {
        return listaDados.get(position);
    }

    public class ViewHolderDados extends RecyclerView.ViewHolder {
        TextView problema;
        TextView nome;
        TextView placa;
        TextView modelo;
        Button btn_verMap;

        public ViewHolderDados(View itemView) {
            super(itemView);
            //pegando o componente
            problema = (TextView)itemView.findViewById(R.id.txt_problema);
            nome = (TextView)itemView.findViewById(R.id.txt_nomeUser);
            modelo = (TextView)itemView.findViewById(R.id.txt_modelo);
            placa = (TextView)itemView.findViewById(R.id.txt_placa);
            btn_verMap = (Button) itemView.findViewById(R.id.btn_verMap);

        }

        public void asignarDados(Registro dados) {

            problema.setText(dados.getDescProblema());
            nome.setText(dados.getNome());
            placa.setText(dados.getPlaca());
            modelo.setText(dados.getModelo());
        }
    }
}
