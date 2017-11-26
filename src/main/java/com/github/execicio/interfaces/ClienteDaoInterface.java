package com.github.execicio.interfaces;

import com.github.execicio.model.Cliente;

import java.util.ArrayList;

public interface ClienteDaoInterface {

    public boolean incluir(Cliente cliente);
    public boolean alterar(Cliente cliente);
    public boolean excluir(Cliente cliente);
    public ArrayList<Cliente> listar();

}
