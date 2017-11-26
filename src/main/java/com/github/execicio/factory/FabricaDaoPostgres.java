package com.github.execicio.factory;

import com.github.execicio.dao.ClienteDaoPostgres;
import com.github.execicio.dao.PedidoDaoPostgres;

public class FabricaDaoPostgres implements FabricaDaoInterface {

    @Override
    public ClienteDaoPostgres criarClienteDao() {
        return new ClienteDaoPostgres();
    }

    @Override
    public PedidoDaoPostgres criarPedidoDao() {
        return new PedidoDaoPostgres();
    }
}
