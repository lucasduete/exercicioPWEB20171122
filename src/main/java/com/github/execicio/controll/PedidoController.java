package com.github.execicio.controll;

import com.github.execicio.factory.Fabrica;
import com.github.execicio.interfaces.PedidoDaoInterface;
import com.github.execicio.model.Pedido;

import java.util.ArrayList;

public class PedidoController implements PedidoDaoInterface {

    PedidoDaoInterface pedidoDao;

    public PedidoController() {
        pedidoDao = new Fabrica().criarDaoPostgres().criarPedidoDao();
    }

    @Override
    public boolean incluir(Pedido pedido) {

        return pedidoDao.incluir(pedido);
    }

    @Override
    public boolean alterar(Pedido pedido) {

        return pedidoDao.alterar(pedido);
    }

    @Override
    public boolean excluir(Pedido pedido) {

        return pedidoDao.excluir(pedido);
    }

    @Override
    public ArrayList<Pedido> listar() {

        return pedidoDao.listar();
    }
}
