package org.example;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "biblioteca")
public class Biblioteca {
    @JacksonXmlElementWrapper(localName = "livros")
    @JacksonXmlProperty(localName = "livro")
    private List<Livro> livros = new ArrayList<>();

    public Biblioteca(){}

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}
