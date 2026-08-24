package org.example;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        VendaDAO dao = new VendaDAO();

        int opcao = -1;
        while (opcao != 0) {
            String menu = """
                    Sistema de Vendas
                    [1] - Cadastrar Vendas
                    [2] - Listar Vendas
                    [3] - Exportar para csv
                    [4] - Exportar para JSON
                    [0] - Sair
                    
                    Escolha uma das opções:
                    """;
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.println("Nome do produto: ");
                    String produto = sc.nextLine();
                    System.out.println("Escolha a categoria: ");
                    String categoria = sc.nextLine();

                    System.out.println("Valor Unitário (Ex: 49,90): ");
                    double valor = sc.nextDouble();

                    System.out.println("Quantidade: ");
                    int qtd = sc.nextInt();

                    dao.salvar(new Venda(produto, categoria, valor, qtd));
                }

                case 2 -> {
                    List<Venda> vendas = dao.listarTodas();
                    System.out.println("-- Vendas Registradas --");
                    vendas.forEach(v -> System.out.printf("[%d] %s (%s) -Qtd: %d preço: R$ %.2f - Total: R$ %.2f \n",
                            v.getId(),
                            v.getProduto(),
                            v.getCategoria(),
                            v.getQuantidade(),
                            v.getValorUnitario(),
                            v.getValorTotal()
                    ));
                }

                case 3 -> {
                    List<Venda> vendas = dao.listarTodas();
                    ExportadorCSV.exportar(vendas, "vendas.csv");
                }

                case 4 -> {
                    List<Venda> vendas = dao.listarTodas();
                    ExportadorJSON.exportar(vendas, "vendas.json");
                }

                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Escolha uma opção válida");

            }

        }
    }
}