package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationManager {
    private final String archiveName;
    private final Gson gson;

    public ConfigurationManager(String archiveName){
        this.archiveName = archiveName;
        //O GsonBuilder() cria um objeto Gson(JSON) configurado
        //setPrettyPrinting faz o JSON salvo ficar formatado (identado)
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public GameConfiguration load(){
        File archive = new File(archiveName);
        if (!archive.exists()){
            System.out.println("Arquivo de configuração não encontrado!!!");
            return new GameConfiguration();
        }
        try(FileReader reader = new FileReader(archive)){
            GameConfiguration config = gson.fromJson(reader, GameConfiguration.class);
            return (config != null) ? config : new GameConfiguration() ;
        } catch (IOException | JsonSyntaxException error){
            System.out.println("Erro ao carregar o arquivo de configuração JSON: " + error.getMessage());
            return new GameConfiguration();
        }
    }

    public void save(GameConfiguration config){
        try(FileWriter writer = new FileWriter(archiveName)){
            gson.toJson(config, writer);
        } catch (IOException error){
            System.out.println("Erro ao salvar o arquivo de configuração JSON: " + error.getMessage());
        }
    }
}