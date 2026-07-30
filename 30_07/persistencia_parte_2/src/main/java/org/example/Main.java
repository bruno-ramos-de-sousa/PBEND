package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final BibliotecaManager manager = new BibliotecaManager("biblioteca.xml");
    private static final Scanner scanner = new Scanner(System.in);
    private static Biblioteca biblioteca;

    public static void main(String[] args) {
        biblioteca = manager.carregar();
        System.out.println("Bem vindo a biblioteca dos estudos!!!");
        System.out.println(biblioteca.getLivros().size() + " Livro(s) carregado(s)");

        int opcao = 0;
        while (opcao != 3) {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1: adicionarLivros(); break;
                    case 2: listarLivros(); break;
                    case 3: System.out.println("Salvando e saindo..."); break;
                    default: System.out.println("Opção inválida!!!"); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número.");
                scanner.nextLine();
            }
        }
        manager.salvar(biblioteca);
        System.out.println("Salvo com sucesso em 'biblioteca.xml'.");
        scanner.close();
    }
    private static void exibirMenu() {
        System.out.println("""
                
                Escolha uma das opções abaixo:
                
                [1] Adicionar novo livro
                [2] Listar todos os livros
                [3] Sair e salvar""");
    }
    private static void adicionarLivros() {
        System.out.println("----Adicionar Livro----");
        try {
            System.out.print("Digite o titúlo: ");
            String titulo = scanner.nextLine();

            System.out.print("Digite o Autor: ");
            String autor = scanner.nextLine();

            System.out.println("Digite o Ano de publicação");
            int ano = scanner.nextInt();

            biblioteca.getLivros().add(new Livro(titulo, autor, ano));
            System.out.println("Livro adicionado com sucesso");
        } catch (InputMismatchException e) {
            System.out.println("Erro: o ano deve ser um número!!!");
            scanner.nextLine();
        }
    }
    private static void listarLivros() {
        System.out.println("--- Livros na Biblioteca ---");
        if (biblioteca.getLivros().isEmpty()) {
            System.out.println("Nenhum livro presente na bilioteca");
        } else {
            biblioteca.getLivros().forEach(System.out::println);
        }
    }
}