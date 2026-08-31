package org.example;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.File;

public class BibliotecaManager {
    private final String nomeArquivo;
    private final XmlMapper xmlMapper;

    public BibliotecaManager(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.xmlMapper = XmlMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
    }
    public Biblioteca carregar() {
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo não econtrado. Criando nova Biblioteca...");
            return new Biblioteca();
        }
        try {
            //Le o arquivo xml e converte para objeto
            return xmlMapper.readValue(arquivo, Biblioteca.class);
        } catch (JacksonException e) {
            System.out.println("Erro ao lero o arquivo xml: " + e.getMessage());
            //printStackTrace exibe erros no console.
            e.printStackTrace();
            return new Biblioteca();
        }
    }

    public void salvar(Biblioteca biblioteca) {
        try {
            xmlMapper.writeValue(new File(nomeArquivo), biblioteca);
        } catch (JacksonException e) {
            System.out.println("Erro ao salvar o arquivo xml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
