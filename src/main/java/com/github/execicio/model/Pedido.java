package com.github.execicio.model;;;

import java.time.LocalDate;

public class Pedido {

    private int Id;
    private LocalDate data;
    private Cliente cliente;
    private Double valor;

    public Pedido() {

    }

    public Pedido(int id, LocalDate data, Cliente cliente, Double valor) {
        Id = id;
        this.data = data;
        this.cliente = cliente;
        this.valor = valor;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;

        if (Id != pedido.Id) return false;
        if (!data.equals(pedido.data)) return false;
        if (!cliente.equals(pedido.cliente)) return false;
        return valor.equals(pedido.valor);
    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + data.hashCode();
        result = 31 * result + cliente.hashCode();
        result = 31 * result + valor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "Id = " + Id +
                ", valor = " + valor +
                ", data = " + data +
                ", Nome do Cliente = " + cliente.getNome() +
                '}';
    }
}
