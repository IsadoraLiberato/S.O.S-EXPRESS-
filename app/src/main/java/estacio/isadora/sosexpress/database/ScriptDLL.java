package estacio.isadora.sosexpress.database;

/**
 * Created by isado on 19/04/2018.
 */

public class ScriptDLL {
    public static String getCreateTableCadastroCliente(){

        StringBuilder sql = new StringBuilder();


        sql.append(" Create Table If Not Exists CadastroCliente ( ");
        sql.append("      ID Integer Primary Key Autoincrement Not Null, ");
        sql.append("      Nome Varchar (250) Not Null Default (''), ");
        sql.append("      Email Varchar (255) Not Null Default (''), ");
        sql.append("      Senha Int (20) Not Null Default (''), ");
        sql.append("      ConfirmarSenha Int (20) Not Null Default ('') ) ");

        return sql.toString();
    }


}

