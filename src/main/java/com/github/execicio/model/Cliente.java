package com.github.execicio.model;

public class Cliente {

    private int id;
    private String nome;
    private String documento;
    private Double saldo;
    private Enum ativo;

    public Cliente() {

    }

    public Cliente(int id, String nome, String documento, Double saldo, Enum ativo) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.saldo = saldo;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Enum getAtivo() {
        return ativo;
    }

    public void setAtivo(Enum ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        if (id != cliente.id) return false;
        if (!nome.equals(cliente.nome)) return false;
        if (!documento.equals(cliente.documento)) return false;
        if (!saldo.equals(cliente.saldo)) return false;
        return ativo.equals(cliente.ativo);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nome.hashCode();
        result = 31 * result + documento.hashCode();
        result = 31 * result + saldo.hashCode();
        result = 31 * result + ativo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", saldo=" + saldo +
                ", ativo=" + ativo +
                '}';
    }
}
