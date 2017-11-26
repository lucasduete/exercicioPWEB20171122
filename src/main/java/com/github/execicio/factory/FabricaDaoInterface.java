package com.github.execicio.factory;

import com.github.execicio.dao.*;

public interface FabricaDaoInterface {

    public ClienteDaoPostgres criarClienteDao();
    public PedidoDaoPostgres criarPedidoDao();
}
