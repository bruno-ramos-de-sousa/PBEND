package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAO {
    public List<Produto> listAvailable() throws SQLException {
        String sql = "SELECT id, nome, categoria, descricao, preco, emoji FROM produtos WHERE disponivel = TRUE ORDER BY categoria, nome";
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement comando = conn.prepareStatement(sql);
             ResultSet result = comando.executeQuery()
             ){
            while (result.next()) {
                produtos.add(criarProduto(result));
            }
        }
        return produtos;
    }
    
    public Optional<Produto> getById(int id) throws SQLException{
        String sql = "SELECT id, nome, categoria, descricao, preco, emoji FROM produtos WHERE id = ? AND dispoinvel = TRUE";
        
        try ( 
                Connection conn = Conexao.conectar();
                PreparedStatement comando = conn.prepareStatement(sql)
                ) {
            comando.setInt(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(criarProduto(resultado));
                }
            }
        }
        return Optional.empty();
    }

    private Produto criarProduto(ResultSet resultado) throws SQLException {
        return new Produto(
                resultado.getInt("id"),
                resultado.getString("nome"),
                resultado.getString("categoria"),
                resultado.getString("descricao"),
                resultado.getDouble("preco"),
                resultado.getString("emoji")
        );
    }
}
