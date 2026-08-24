import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String nomeArquivoEstoque = "estoque.txt";
        EstoqueManager manager = new EstoqueManager(nomeArquivoEstoque);
        Scanner scanner = new Scanner(System.in);
        int codigo = 100;
        String nome;
        double preco;
        int quant;
        System.out.println("--- Tentando carregar o estoque do arquivo ---");
        List<Produto> estoque = manager.carregarProdutos();
        System.out.printf("""
                
                Estoque carregado com %d produto(s).
                
                Estado atual do estoque:
                """, estoque.size());
        estoque.forEach(System.out::println);
        System.out.println("\n---Realizando operações no sistema...---");

        if (estoque.isEmpty()) {
            codigo += estoque.size();
            System.out.println("Digite o nome do produto: ");
            nome = scanner.nextLine();
            System.out.println("Digite o preço do produto: ");
            preco = scanner.nextDouble();
            System.out.println("Digite a quantidade do produto: ");
            quant = scanner.nextInt();
            estoque.add(new Produto(codigo, nome, preco, quant));
            System.out.println("Adicionando produtos iniciais...");
        } else {
            codigo += estoque.size();
            System.out.println("Adicionando um novo produto e atualizando um existente...");
            estoque.add(new Produto(codigo, "Monitor 24 polegadas", 500, 24));
            if (!estoque.isEmpty()) {
                Produto primeiroProduto = estoque.get(0);
                primeiroProduto.setQuantidade(primeiroProduto.getQuantidade() + 5);
                System.out.println("Estoque do produto '" + primeiroProduto.getNome() + "' atualizado.");
            }
        }
        System.out.println("\nEstoque após as operações");
        estoque.forEach(System.out::println);
        System.out.println("\n--- Salvando o estado atual do estoque... ---");
        manager.salvarProdutos(estoque);
        System.out.println("Estoque salvo com sucesso em '" + nomeArquivoEstoque + "'!");
    }
}
