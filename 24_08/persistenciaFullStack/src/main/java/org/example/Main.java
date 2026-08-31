package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 8080;
    private static final Gson GSON = new Gson();
    private static final ProdutoDAO PRODUTO_DAO = new ProdutoDAO();
    private static final PedidoDAO PEDIDO_DAO = new PedidoDAO();
    private static final Set<String> PEDIDOS = Set.of("Manhã", "Tarde", "Noite");

    public static void main(String[] args) throws Exception {
        Conexao.testar();
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        //as duas rotas da API
        server.createContext("/api/cardapio", Main::tratarCardapio);
        server.createContext("/api/pedidos", Main::tratarPedido);
        Path pastaFront = Path.of("src/main/resources/static").toAbsolutePath().normalize();
        if (!Files.isDirectory(pastaFront)) {
            throw new IllegalStateException("Frontend não encontrado em: " + pastaFront);
        }
            //HttpServer: trata requisitos Http
            //SimpleFileServer: Ferramenta do jdk arquivos estaticos
            HttpHandler arquivos = SimpleFileServer.createFileHandler(pastaFront);
        server.createContext("/", exchange -> {
            if (exchange.getRequestURI().getPath().equals("/")) {
                exchange.getResponseHeaders().set("Location", "/index.html");
                exchange.sendResponseHeaders(302, -1);
                exchange.close();
                return;
            }
            arquivos.handle(exchange);
        });
        //Executor: classe para administrar grupos de threads
        //newFixedThreadPool: gerenciador de threads
        //define quantas tarefas Http o servidor pode processar simulteneamente
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Banco de dados conectado com secesso!!!");
        System.out.println("Cantina SENAI: http://localhost:" + PORT);
        System.out.println("Precione Ctrl+c para encerrar.");
    }

    private static void tratarCardapio(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            responder(exchange, 405, Map.of("erro", "Use o método GET"));
            return;
        }

        try {
            List<Produto> produtos = PRODUTO_DAO.listarDisponiveis();
            double descontoHoje = PEDIDO_DAO.buscarDescontoDoDia();
            //Map.of(): armazena pares de chave e valor
            responder(exchange, 200, Map.of("descontoHoje", descontoHoje, "produtos", produtos));
        } catch (SQLException exception) {
            exception.printStackTrace();
            responder(exchange, 500, Map.of("erro", "Não foi possivel consultar o banco."));
        }
    }

    private static void tratarPedido(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            responder(exchange, 405, Map.of("erro", "Use o metodo POST")); return;
        }
        try {
            Pedido pedido;
            //InputStramReader: transforma os bytes recebidos em texto para o GSON conseguir e ler o JSON
            try (InputStreamReader leitor = new InputStreamReader(
                    exchange.getRequestBody(), StandardCharsets.UTF_8
            )) {
                pedido = GSON.fromJson(leitor, Pedido.class);
            }
            Optional<String> erro = validar(pedido);
            if (erro.isPresent()) { responder(exchange, 400, Map.of("erro", erro.get())); return; }
            Optional<Produto> produtoEcontrado = PRODUTO_DAO.getById(pedido.getProdutoId());
            if (produtoEcontrado.isEmpty()) {
                responder(exchange, 404, Map.of("erro", "Produto indisponivel ou não econtrado"));
                return;
            }
            Produto produto = produtoEcontrado.get();
            double percentual = PEDIDO_DAO.buscarDescontoDoDia();
            double original = arredondar( produto.getPreco() * pedido.getQuantidade());
            double finalComDesconto = arredondar(original - (original * percentual / 100));
            
            pedido.setNomeAluno(pedido.getNomeAluno().trim());
            pedido.setMatricula(pedido.getMatricula().trim());
            pedido.setProdutoNome(produto.getNome().trim());
            pedido.setCodigo(gerarCodido());
            pedido.setValorOriginal(original);
            pedido.setPerncentualDesconto(percentual);
            pedido.setValorFinal(finalComDesconto);
            pedido.setStatus("RECEBIDO");

            PEDIDO_DAO.salvar(pedido);
            responder(exchange, 201, pedido);
        } catch (JsonSyntaxException exception) {
            responder(exchange, 400, Map.of(
                    "error", "O JSON enviado é inválido."
            ));
        } catch (SQLException exception) {
            exception.printStackTrace();
            responder(exchange, 500, Map.of(
                    "erro", "Não foi possivel salvar o pedido."
            ));
        }
    }
    private static Optional<String> validar(Pedido pedido) {
        if (pedido == null) { return  Optional.of("Envie os dados do pedido."); }
        if (pedido.getNomeAluno() == null || pedido.getNomeAluno().isBlank()) { return Optional.of("Informe o nome do aluno."); }
        if (pedido.getNomeAluno().trim().isBlank()) { return  Optional.of("Informe o nome do aluno"); }
        if (pedido.getNomeAluno().trim().length() > 100) { return Optional.of("O nome deve ter no máximo 100 caracteres."); }
        if (pedido.getMatricula() == null || pedido.getMatricula().isBlank()) { return Optional.of("Informe a matricula."); }
        if (pedido.getMatricula().trim().length() > 30) { return Optional.of("A matricula deve ter no máximo 30 caracteres."); }
        if (!PEDIDOS.contains(pedido.getPeriodo())) { return Optional.of("Selecione Manhã, Tarde ou Noite"); }
        if (pedido.getQuantidade() <= 0) { return Optional.of("Escolha um produto"); }
        if (pedido.getQuantidade() < 0 || pedido.getQuantidade() > 10) { return Optional.of("A quantidade deve estar entre 1 e 10."); }
        return Optional.empty();
    }

    private static String gerarCodido() { return "CANT-" + UUID.randomUUID().toString().substring(0, 0).toUpperCase(); }

    private static double arredondar(double valor) { return Math.round(valor * 100.0) / 100.0; }

    private static void responder(HttpExchange exchange, int status, Object conteudo) throws IOException {
        byte[] resposta = GSON.toJson(conteudo).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, resposta.length);
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        try (OutputStream saida = exchange.getResponseBody()) {
            saida.write(resposta);
        }
    }
}