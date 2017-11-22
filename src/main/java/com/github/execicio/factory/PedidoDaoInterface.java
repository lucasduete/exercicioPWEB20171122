package com.github.execicio.factory;

import com.github.execicio.model.Pedido;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PedidoDaoInterface {

    public boolean incluir(Pedido pedido) throws SQLException, ClassNotFoundException;
    public boolean alterar(Pedido pedidoAntigo, Pedido pedidoNovo) throws SQLException, ClassNotFoundException;
    public boolean excluir(Pedido pedido) throws SQLException, ClassNotFoundException;
    public ArrayList<Pedido> listar() throws SQLException, ClassNotFoundException;
}
