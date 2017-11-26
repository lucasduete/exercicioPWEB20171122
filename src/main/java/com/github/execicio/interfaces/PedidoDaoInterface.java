package com.github.execicio.interfaces;

import com.github.execicio.model.Pedido;

import java.util.ArrayList;

public interface PedidoDaoInterface {

    public boolean incluir(Pedido pedido);
    public boolean alterar(Pedido pedido);
    public boolean excluir(Pedido pedido);
    public ArrayList<Pedido> listar();
}
