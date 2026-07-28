import java.util.List;

public class Main {
    public static void main(String[] args) {
        String nomeArquivoEstoque = "estoque.txt";
        EstoqueManager manager = new EstoqueManager(nomeArquivoEstoque);
        System.out.println("--- Tentando carregar o estoque do arquivo ---");
        List<Produto> estoque = manager.carregarProdutos();
        System.out.printf("""
                
                Estoque carregado com %d produto(s).
                
                Estado atual do estoque:
                """, estoque.size());
        estoque.forEach(System.out::println);
        System.out.println("\n---Realizando operações no sistema...---");

        if (estoque.isEmpty()) {
            System.out.println("Adicionando produtos iniciais...");
            estoque.add(new Produto(101, "Teclado Mecânico", 350.50, 10));
            estoque.add(new Produto(102, "Mouse Gamer", 250.00, 3));
            estoque.add(new Produto(103, "Mouse Pad", 50.29, 40));
        } else {
            System.out.println("Adicionando um novo produto e atualizando um existente...");
            estoque.add(new Produto(104, "Monitor 24 polegadas", 500, 24));
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
