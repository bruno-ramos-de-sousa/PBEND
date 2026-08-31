package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExportadorJSON {
    public static  void  exportar(List<Venda> vendas, String caminhoArquivo) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(caminhoArquivo), vendas);
            System.out.println("Arquivo JSON gerado com sucesso: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao exportar JSON: " + e.getMessage());
        }
    }
}