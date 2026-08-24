package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {
    public static void exportar(List<Venda> vendas, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))){
            writer.write("ID;Produto;Categoria;ValorUnitario;Quantidade;ValorTotal");
            writer.newLine();

            for(Venda v : vendas) {
                String linha = String.format("%d;%s;%s;%.2f;%d;%.2f",
                        v.getId(),v.getProduto(),
                        v.getCategoria(),
                        v.getValorUnitario(),
                        v.getQuantidade(),
                        v.getValorTotal());
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Arquivo CSV gerado com sucesso: " + caminhoArquivo);
        }catch (IOException e) {
            System.out.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }
}