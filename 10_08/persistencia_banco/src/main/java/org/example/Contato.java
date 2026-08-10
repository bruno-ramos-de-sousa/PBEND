package org.example;

public class Contato {
    private int id;
    private String nome;
    private String telefone;

    //construtor usado para dicionar (o ID sera gerado pelo banco)
    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    //construtor usado para listar e atualizar (o id vem do banco)
    public Contato(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %-20s | Telefone: %s", id, nome, telefone);
    }
}
