package model;

import java.util.Objects;

public class Produto {
    private String id;
    private String nome;
    private String descricao;
    private String valor;
    private String estado;

    public Produto(String id, String nome, String descricao, String valor, String estado) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.estado = estado;
    }

    public Produto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id)
                && nome.equals(produto.nome)
                && Objects.equals(descricao, produto.descricao)
                && valor.equals(produto.valor)
                && Objects.equals(estado, produto.estado);
    }

    @Override
    public String toString() {
        return  id + " -- " + nome + " -- " + descricao + " -- " + valor + " -- " + estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, valor, estado);
    }
}
