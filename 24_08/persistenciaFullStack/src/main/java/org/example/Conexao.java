package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
    //SSL: criptografia
    private static final String URL = "jdbc:mysql://localhost:3306/cantina_senai_db" +
            "?userSSL=false" +
            "&allowpublicKeyRetrieval=true" +
            "&serverTimezone=America/Sao_Paulo";
    //allowpublicKeyRetrieval: permite que o driver do JDVC solicite ao mysql uma chave publica
    //serverTimezone: ingorma ao JDBC o fuso horario para trabalhar

    private static final String USER = "cantina";
    private static final String PASSWORD = "cantina123";

    public Conexao() {}

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testar() throws SQLException {
        try (Connection conexao = conectar()){
            if (!conexao.isValid(2)) {
                throw new SQLException("O MySQL não confirmou a conexão!");
            }
        }
    }
}