package com.github.execicio.dao;

import com.github.execicio.Enum.EnumAtivo;
import com.github.execicio.interfaces.ClienteDaoInterface;
import com.github.execicio.factory.Conexao;
import com.github.execicio.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDaoPostgres implements ClienteDaoInterface {

    @Override
    public boolean incluir(Cliente cliente) {
        try {
            Connection conn = Conexao.getConnection();

            String sql = "INSERT INTO Cliente (Nome, Documento, Saldo, Ativo) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getDocumento());
            stmt.setDouble(3, cliente.getSaldo());
            stmt.setString(4, cliente.getAtivo().toString());

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
    public boolean excluir(Cliente cliente) {
        try {
            Connection conn = Conexao.getConnection();

            String sql = "DELETE FROM Cliente WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, cliente.getId());

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
    public ArrayList<Cliente> listar() {

        ArrayList<Cliente> clientes = new ArrayList<>();

        try {

            Connection conn = Conexao.getConnection();

            String sql = "SELECT * FROM Cliente";
            PreparedStatement stmt= conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                clientes.add(
                        new Cliente(
                                rs.getInt("id"),
                                rs.getString("Nome"),
                                rs.getString("Documento"),
                                rs.getDouble("Saldo"),
                                EnumAtivo.valueOf(rs.getString("Ativo"))
                        )
                );

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

            String sql = "SELECT * FROM Cliente WHERE Id = ?";
            PreparedStatement stmt= conn.prepareStatement(sql);

            stmt.setInt(1, idCliente);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return new Cliente(
                    idCliente,
                    rs.getString("Nome"),
                    rs.getString("Documento"),
                    rs.getDouble("Saldo"),
                    EnumAtivo.valueOf(rs.getString("Ativo"))
                );

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
