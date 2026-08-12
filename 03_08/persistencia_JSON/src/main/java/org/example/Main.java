package org.example;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final ConfigurationManager manager = new ConfigurationManager("configJogo.json");
    private static final Scanner scanner = new Scanner(System.in);
    private static GameConfiguration settings;

    public static void main(String[] args) {
        settings = manager.load();
        System.out.println("Painel de Configuracoes de Jogo");

        int opcao = 0;

        while(opcao != 6){
            displayMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao){
                    case 1:
                        showConfig();
                        break;
                    case 2:
                        changePlayerName();
                        break;
                    case 3:
                        changeDifficulty();
                        break;
                    case 4:
                        changeAudio();
                        break;
                    case 5:
                        changeResolution();
                        break;
                    case 6:
                        System.out.println("Salvando configuracoes...");
                        break;
                    default:
                        System.out.println("Opcao Invalida!!!");

                }
            } catch (InputMismatchException e){
                System.out.println("Erro: Por favor, digite um numero");
                scanner.nextLine();
            }
        }
        manager.save(settings);
        System.out.println("Configuracoes salvas em 'configJogo.json'");
    }
    private static void displayMenu(){
        System.out.println("""
                    |------------------- MENU -------------------|
                        [1] - Ver configuracoes
                        [2] - Alterar o nome do jogador
                        [3] - Alterar nivel de dificuldade (1-3)
                        [4] - Habilitar/Desabilitar audio
                        [5] - Trocar resolucao
                        [6] - Sair e salvar
                    
                    Choose an option:
                    """);
    }
    private static void showConfig(){
        System.out.println(settings.toString());
    }
    private static void changePlayerName(){
        System.out.println("Digite o nome para subescrever: ");
        String newName = scanner.nextLine();
        settings.setPlayerName(newName);
        System.out.println("Nome do jogador alterado para:   " + newName);
    }
    private static void changeDifficulty(){
        System.out.println("Digite o novo nivel de dificuldade (1 - Facil | 2 - Médio | 3 - Dificil)");
        try{
            int newNivel = scanner.nextInt();
            scanner.nextLine();
            if (newNivel >= 1 && newNivel <= 3){
                settings.setLevelDifficulty(newNivel);
            }
        } catch (InputMismatchException e){
            System.out.println("Erro: Digite um numero " + e.getMessage());
            scanner.nextLine();
        }
    }
    private static void changeAudio(){
        System.out.println("""
                [1] - Habilitar som
                [2] - Desabilitar som
                """);
        int newAudio = scanner.nextInt();
        if (newAudio != 1 && newAudio != 2){
            System.out.println("Opcao Invalida");
        }
        if (newAudio == 1){
            settings.setAudioEnabled(true);
        }
        if (newAudio == 2){
            settings.setAudioEnabled(false);
        }
    }

    private static  void changeResolution(){
        System.out.println("Escolha sua nova resolucao: ");
        String newResolution = scanner.nextLine();
        int[] dimensoes = Arrays.stream(newResolution.split("x")).mapToInt(Integer::parseInt).toArray();
        double razao = (double) dimensoes[0] / dimensoes[1];

        if (razao == 1 || razao == (double) 4 / 3 || razao == (double) 16 / 9 || razao == (double) 16 / 10) {
            settings.setScreenResolution(newResolution);
            System.out.println("Seu PC esta com a resolucao de: " + newResolution);
        } else {
            System.out.println("Resolucao invalida!!!");
        }
    }
}