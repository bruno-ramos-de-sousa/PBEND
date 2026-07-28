import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EstoqueManager {
    private final String nomeArquivo;

    public EstoqueManager(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;

        Locale.setDefault(Locale.US);
    }

    public List<Produto> carregarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo de estoque ainda não existe. Será criado um novo.");
            return produtos;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                //split: aplica um separador de caracteres no arquivo (regex)
                //regex: expressao regular: padrao de caracter para texto
                String[] partes = linha.split(";");
                if (partes.length == 4) {
                    int codigo = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    double preco = Double.parseDouble(partes[2]);
                    int quantidade = Integer.parseInt(partes[3]);
                    produtos.add(new Produto(codigo, nome, preco, quantidade));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo do estoque: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter um número do arquivo: " + e.getMessage());
        }
        return produtos;
    }
    public void salvarProdutos(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Produto produto: produtos) {
                String linha = String.format("%d;%s;%.2f;%d",
                        produto.getCodigo(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getQuantidade());
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de estoque: " + e.getMessage());
        }
    }
}
