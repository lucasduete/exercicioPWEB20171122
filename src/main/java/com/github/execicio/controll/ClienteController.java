package com.github.execicio.controll;

import com.github.execicio.factory.Fabrica;
import com.github.execicio.interfaces.ClienteDaoInterface;
import com.github.execicio.model.Cliente;

import java.util.ArrayList;

public class ClienteController implements ClienteDaoInterface {

    ClienteDaoInterface clienteDao;

    public ClienteController() {
        clienteDao = new Fabrica().criarDaoPostgres().criarClienteDao();
    }

    @Override
    public boolean incluir(Cliente cliente) {

        return clienteDao.incluir(cliente);
    }

    @Override
    public boolean alterar(Cliente cliente) {

        return clienteDao.alterar(cliente);
    }

    @Override
    public boolean excluir(Cliente cliente) {

        return clienteDao.excluir(cliente);
    }

    @Override
    public ArrayList<Cliente> listar() {

        return clienteDao.listar();
    }
}
