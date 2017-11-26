package com.github.execicio.dao;

import com.github.execicio.factory.Conexao;
import com.github.execicio.factory.PedidoDaoInterface;
import com.github.execicio.model.Pedido;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        try {
            Connection conn = Conexao.getConnection();

            String sql = "DELETE Pedido WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, pedido.getId());

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
    public ArrayList<Pedido> listar() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conn = Conexao.getConnection();

            String sql = "SELECT * FROM Pedido";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("Id"));
                pedido.setValor(rs.getDouble("Valor"));

                Date data = rs.getDate("Data");
                Instant instant = Instant.ofEpochMilli(data.getTime());
                pedido.setData(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());

                pedido.setCliente(
                        new ClienteDaoPostgres().
                                getCliente(rs.getInt("IdCliente"))
                );
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return pedidos;
    }
}
