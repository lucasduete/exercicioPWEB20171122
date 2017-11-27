package com.github.execicio.dao;

import com.github.execicio.Enum.EnumAtivo;
import com.github.execicio.filtros.ClienteFiltro;
import com.github.execicio.interfaces.ClienteDaoInterface;
import com.github.execicio.factory.Conexao;
import com.github.execicio.model.Cliente;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.FilteredRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import java.sql.*;
import java.util.ArrayList;

public class ClienteDaoPostgres implements ClienteDaoInterface {

    @Override
    public boolean incluir(Cliente cliente) {
        try {
            Connection conn = Conexao.getConnection();

            String sql = String.format("INSERT INTO Cliente (Nome, Documento, Saldo, Ativo) " +
                    "VALUES (%s,%s,%d,%s)", cliente.getNome(), cliente.getDocumento(), cliente.getSaldo(),
                        cliente.getAtivo().toString());

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

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

            String sql = String.format("UPDATE Cliente SET Nome = %s Documento = %s Saldo = %d Ativo = %s " +
                    "WHERE Id = %n)", cliente.getNome(), cliente.getDocumento(), cliente.getSaldo(),
                    cliente.getAtivo().toString(), cliente.getId());

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

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
        try {
            Connection conn = Conexao.getConnection();

            String sql = String.format("DELETE FROM Cliente WHERE Id = %n", cliente.getId());

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public ArrayList<Cliente> listar() {

        ArrayList<Cliente> clientes = new ArrayList<>();

        try {

            Connection conn = Conexao.getConnection();

            String sql = "SELECT * FROM Cliente";
            Statement stmt= conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT
            );

            ResultSet rs = stmt.executeQuery(sql);

            CachedRowSet rowSet = new CachedRowSetImpl();

            rowSet.populate(rs);

            while (rowSet.next())
                clientes.add(
                        new Cliente(
                                rowSet.getInt("id"),
                                rowSet.getString("Nome"),
                                rowSet.getString("Documento"),
                                rowSet.getDouble("Saldo"),
                                EnumAtivo.valueOf(rowSet.getString("Ativo"))
                        )
                );

            rowSet.close();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }

        return clientes;
    }

    public Cliente getCliente(int idCliente) {

        try {

            Connection conn = Conexao.getConnection();

            String sql = String.format("SELECT * FROM Cliente WHERE Id = %n", idCliente);

            Statement stmt= conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT
            );
            ResultSet rs = stmt.executeQuery(sql);

            FilteredRowSet rowSet = new FilteredRowSetImpl();

            rowSet.setCommand(sql);
            rowSet.execute();

            rowSet.beforeFirst();
            rowSet.setFilter(new ClienteFiltro(idCliente));

            if (rowSet.next())
                return new Cliente(
                    idCliente,
                    rowSet.getString("Nome"),
                    rowSet.getString("Documento"),
                    rowSet.getDouble("Saldo"),
                    EnumAtivo.valueOf(rowSet.getString("Ativo"))
                );

            rowSet.close();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
