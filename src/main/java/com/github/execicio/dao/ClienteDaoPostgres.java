package com.github.execicio.dao;

import com.github.execicio.factory.ClienteDaoInterface;
import com.github.execicio.factory.Conexao;
import com.github.execicio.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDaoPostgres implements ClienteDaoInterface {

    @Override
    public boolean incluir(Cliente cliente) {
        try {
            Connection conn = Conexao.getConnection();

            String sql = "INSERT INTO Cliente (Nome, Documento, Saldo, Ativo) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean alterar(Cliente cliente) {
        try {

            Connection conn = Conexao.getConnection();

            String sql = "UPDATE Cliente SET Nome = ? Documento = ? Saldo = ? Ativo = ? WHERE Id = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getDocumento());
            stmt.setDouble(3, cliente.getSaldo());
            stmt.setString(4, cliente.getAtivo().toString());

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean excluir(Cliente cliente) {
        return false;
    }

    @Override
    public ArrayList<Cliente> listar() {
        return null;
    }
}
