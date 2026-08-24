package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    //caminho para o banco "vendas_db.mv.db"
    private static final String URL = "jdbc:h2:./vendas_db";
    private static final String USER = "root";
    private static final String PASS = "";


    public VendaDAO() {
        criarTabelaSeNaoExistir();
    }

    private Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private void criarTabelaSeNaoExistir() {
        String sql = """
                CREATE TABLE IF NOT EXISTS venda(
                id INT AUTO_INCREMENT PRIMARY KEY,
                produto VARCHAR(100),
                categoria VARCHAR(50)
                valor_unitario DOUBLE,
                quantidade INT
                );
                """;

        try (Connection conn = getConexao(); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void salvar(Venda venda) {
        String sql = "INSERT INTO venda(produto, categoria, valor_unitario, quantidade VALUES(?, ?, ?, ?)";

        try (Connection conn = getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, venda.getProduto());
            pstmt.setString(2, venda.getCategoria());
            pstmt.setDouble(3, venda.getValorUnitario());
            pstmt.setInt(4, venda.getQuantidade());
            pstmt.execute();
            System.out.println("Venda registrada com sucesso!!!");
        }catch (SQLException e){
            System.out.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    public List<Venda> listarTodas() {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        try (Connection conn = getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setProduto(rs.getString("produto"));
                v.setCategoria(rs.getString("categoria"));
                v.setValorUnitario(rs.getDouble("valor_unitario"));
                v.setQuantidade(rs.getInt("quantidade"));
                lista.add(v);
            }
        }catch (SQLException e) {
            System.out.println("Erro ao buscar vendas:" + e.getMessage());
        }
        return lista;
    }
}