package com.github.execicio.dao;

import com.github.execicio.factory.Conexao;
import com.github.execicio.factory.PedidoDaoInterface;
import com.github.execicio.model.Pedido;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PedidoDaoPostgres implements PedidoDaoInterface {

    @Override
    public boolean incluir(Pedido pedido) {

        try {
            Connection conn = Conexao.getConnection();

            String sql = "INSERT INTO Pedido (Data, Valor, idCliente) VALUES(?,?,?) ";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(pedido.getData()));
            stmt.setDouble(2, pedido.getValor());
            stmt.setInt(3, pedido.getCliente().getId());

            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean alterar(Pedido pedido) {

        try {
            Connection conn = Conexao.getConnection();

            String sql = "UPDATE Pedido SET Data = ? Valor = ? idCliente = ? WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(pedido.getData()));
            stmt.setDouble(2, pedido.getValor());
            stmt.setInt(3, pedido.getCliente().getId());
            stmt.setInt(4, pedido.getId());

            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean excluir(Pedido pedido) {
        return false;
    }

    @Override
    public ArrayList<Pedido> listar() {
        return null;
    }
}
