package com.github.execicio.factory;

import com.github.execicio.model.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClienteDaoInterface {

    public boolean incluir(Cliente cliente) throws SQLException, ClassNotFoundException;
    public boolean alterar(Cliente clienteAntigo, Cliente clienteNovo) throws SQLException, ClassNotFoundException;
    public boolean excluir(Cliente cliente) throws SQLException, ClassNotFoundException;
    public ArrayList<Cliente> listar() throws SQLException, ClassNotFoundException;

}
