package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Set;

public class Main {
    private static final int PORT = 8080;
    private static final Gson GSON = new Gson();
    private static final Produto PRODUTO_DAO = new Produto();
    private static final PedidoDAO PEDIDO_DAO = new PedidoDAO();
    private static final Set<String> PEDIDOS = Set.of("Manhã", "Tarde", "Noite");

    public static void main(String[] args) throws SQLException {
        Conexao.testar();
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        //as duas rotas da API
        server.createContext("/api/cardapio", Main::tratarCardapio);
        server.createContext("/api/pedidos", Main::tratarPedida);
        Path pastaFront = Path.of("src/main/resources/static").toAbsolutePath().normalize();
        if (!Files.isDirectory(pastaFront)) {
            throw new IllegalStateException("Frontend não encontrado em: " + pastaFront);
        }
    }
}