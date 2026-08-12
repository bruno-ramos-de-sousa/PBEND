package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    //CREATE (criar) - Adicionar um contato ao banco
    public void adicionar(Contato contato) {
        //O "?" é um placeholder, para evitar SQL Injection
        String sql = "INSERT INTO contatos(nome, telefone) VALUES(?, ?)";

        try (Connection conn = Database.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            //Define os valores para os placeholders
            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar contato: " + e.getMessage());
        }
    }

    //READ (ler) - consulta o banco trazendo todos os contatos
    public List<Contato> listar() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos";
        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                contatos.add(new Contato(id, nome, telefone));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
        }
        return contatos;
    }

    //UPDATE (atualizar) - modifica um contato existente pelo id no banco
    public void atualizar(Contato contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone());
            pstmt.setInt(3, contato.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contato: " + e.getMessage());
        }
    }

    //DELETE (deletar/remover) - remove um contato do banco pelo id
    public void deletar(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar contato: " + e.getMessage());
        }
    }
}
