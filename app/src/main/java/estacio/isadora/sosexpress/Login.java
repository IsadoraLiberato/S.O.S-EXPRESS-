package estacio.isadora.sosexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button botaocadastro;
    Button botaologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botaocadastro = (Button) findViewById(R.id.btcds);
        botaocadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaselecionarcadastro ();
            }


        });
botaologin = (Button) findViewById(R.id.btacessar);
botaologin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        telamapa();
    }
});

    }
    public void telamapa() {
        Intent intent = new Intent(this, MapaUsuario.class);
        startActivity(intent);
    }
    public void telaselecionarcadastro (){
        Intent intent = new Intent(this, CadastroOficina.class);
        startActivity(intent);
    }
}
