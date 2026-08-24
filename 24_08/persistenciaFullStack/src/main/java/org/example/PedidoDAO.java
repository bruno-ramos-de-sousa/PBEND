package org.example;

import java.sql.*;

public class PedidoDAO {
    public double buscarDescontoDoDia() throws SQLException {
        String sql = "SELECT percentual FROM cupons WHERE data_cupom = CURRENT_DATA() AND ativo = TRUE LIMIT 1";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement comando = conn.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
                ) {
            if (resultado.next()) {
                return resultado.getDouble("percentual");
            }
        }
        return 0;
    }
    public Pedido salvar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos(codigo, nome_aluno, matricula, periodo, produto_id, quantidade, valor_original, percentual_disconto, valor_final, status) VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement comando = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS
                )
                ) {
            comando.setString(1, pedido.getCodigo());
            comando.setString(2, pedido.getNomeAluno());
            comando.setString(3, pedido.getMatricula());
            comando.setString(4, pedido.getPeriodo());
            comando.setInt(5, pedido.getProdutoId());
            comando.setInt(6, pedido.getQuantidade());
            comando.setDouble(7, pedido.getValorOriginal());
            comando.setDouble(8, pedido.getPerncentualDesconto());
            comando.setDouble(9, pedido.getValorFinal());
            comando.setString(10, pedido.getStatus());
            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (chaves.next()) {
                    pedido.setId(chaves.getInt(1));
                }
            }
        }
        return pedido;
    }
}
