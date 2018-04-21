package estacio.isadora.sosexpress;

import android.os.ConditionVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CdsUsuario extends AppCompatActivity {

EditText nomeusu;
EditText Nascimento;
EditText CPF;
EditText Emailusu;
EditText Senhausu;
EditText ConfSenhaUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cds_usuario);

        nomeusu = findViewById(R.id.editText8);
        Nascimento = findViewById(R.id.editText10);
        CPF = findViewById(R.id.editText15);
        Emailusu = findViewById(R.id.editText20);
        Senhausu = findViewById(R.id.editText21);
        ConfSenhaUsu = findViewById(R.id.editText22);
    }
}
