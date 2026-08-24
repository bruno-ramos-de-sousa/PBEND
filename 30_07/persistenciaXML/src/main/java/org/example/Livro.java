package org.example;

public class Livro {

    private String titulo;
    private String autor;
    private int ano;

    //Esta vazio para que o jackson desentralize o objeto
    public Livro(){}

    public Livro(String titulo, String autor, int ano) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return String.format("Titúlo: %s, Autor: %s, Ano: %d", titulo, autor, ano);
    }
}
