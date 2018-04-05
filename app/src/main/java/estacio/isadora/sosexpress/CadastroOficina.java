package estacio.isadora.sosexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CadastroOficina extends AppCompatActivity {

    Button botoficina;
    Button botousu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_oficina);

        botousu = findViewById(R.id.btusu);
        botousu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastro_usuario();
            }
        });

        botoficina = findViewById(R.id.btoficina);
        botoficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastro_oficina();
            }
        }); }



    public void cadastro_oficina() {
        Intent intent = new Intent(this, CdsOficina.class);
        startActivity(intent);
    }

    public void cadastro_usuario() {
        Intent intent = new Intent(this,CdsUsuario.class);
        startActivity(intent);
    }

}


