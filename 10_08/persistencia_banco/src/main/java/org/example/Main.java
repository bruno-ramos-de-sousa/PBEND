package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Variavel | Constantes globais
    private static final ContatoDAO dao = new ContatoDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //garante que a tabela exista antes de qualquer operação
        Database.criarTabela();
        System.out.println("Agenda de contatos");
        int opcao = 0;

        while (opcao != 5) {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1: adicionarContato(); break;
                    case 2: listarContatos(); break;
                    case 3: atualizarContato(); break;
                    case 4: removerContato(); break;
                    case 5: System.out.println("Encerrando..."); break;
                    default: System.out.println("Opção invalida!"); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Por favor, Digite um número!");
                scanner.nextLine();
            }
        }
    }
    private static void exibirMenu() {
        System.out.println("""
                ---------------- MENU ----------------
                   [1] Adicionar um contato
                   [2] Listar todos os contatos
                   [3] Atualizar um contato
                   [4] Remover um contato
                   [5] Sair
                """);
    }

    private static void adicionarContato() {
        System.out.println("""
        \n-------------- Adicionar Contato --------------
        
        Nome:""");
        String nome = scanner.nextLine();
        System.out.println("Telefone: ");
        String telefone = scanner.nextLine();

        dao.adicionar(new Contato(nome, telefone));
        System.out.println("Contato salvo com sucesso");
    }

    private static void listarContatos() {
        System.out.println("\n--- Lista de Contatos ---");
        List<Contato> contatos = dao.listar();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado");
        } else {
            contatos.forEach(System.out::println);
        }
    }

    private static void atualizarContato() {
        System.out.println("""
                \n-------------- Atualizar Contato --------------
                
                Digite o ID do contato a ser atualizado:""");

        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.println("Novo telefone: ");
            String telefone = scanner.nextLine();
            dao.atualizar(new Contato(id, nome, telefone));
            System.out.println("Contato atualizado com sucesso");
        } catch (InputMismatchException e) {
            System.out.println("ID invalido. Digite um numero!");
            scanner.nextLine();
        }
    }

    private static void removerContato() {
        System.out.println("""
                -------------- Remover Contato --------------
                
                Digite o ID do contato a ser removido:""");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            dao.deletar(id);
            System.out.println("Contato removido com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("ID invalido. Digite um numero!");
            scanner.nextLine();
        }
    }
}