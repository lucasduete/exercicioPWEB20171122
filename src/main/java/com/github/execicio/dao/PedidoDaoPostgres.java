package com.github.execicio.dao;

import com.github.execicio.factory.Conexao;
import com.github.execicio.interfaces.PedidoDaoInterface;
import com.github.execicio.model.Pedido;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;

public class PedidoDaoPostgres implements PedidoDaoInterface {

    @Override
    public boolean incluir(Pedido pedido) {

        try {
            Connection conn = Conexao.getConnection();

            String sql = String.format("INSERT INTO Pedido (Data, Valor, idCliente) " +
                    "VALUES('%tF', %f, %d)", Date.from(pedido.getData().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    pedido.getValor(), pedido.getCliente().getId());

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

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

            String sql = String.format("UPDATE Pedido SET Data = '%tF', Valor = %f, idCliente = %d " +
                    "WHERE Id = %d", Date.from(pedido.getData().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    pedido.getValor(), pedido.getCliente().getId(), pedido.getId());

            JdbcRowSet stmt = new JdbcRowSetImpl(conn);
            stmt.setCommand(sql);

            stmt.execute();

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            if(!e.getMessage().contains("No results were returned by the query")) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean excluir(Pedido pedido) {

        try {
            Connection conn = Conexao.getConnection();

            String sql = String.format("DELETE FROM Pedido WHERE Id = %d", pedido.getId());

            JdbcRowSet stmt = new JdbcRowSetImpl(conn);
            stmt.setCommand(sql);

            stmt.execute();

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            if(!e.getMessage().contains("No results were returned by the query")) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public ArrayList<Pedido> listar(int idCliente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            Connection conn = Conexao.getConnection();
            ResultSet rs = null;
            String sql = null;

            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT
            );

            sql = "SELECT * FROM Pedido";
            rs = stmt.executeQuery(sql);
            CachedRowSet rowSetPedido = new CachedRowSetImpl();
            rowSetPedido.populate(rs);

            sql = "SELECT * FROM Cliente";
            rs = stmt.executeQuery(sql);
            CachedRowSet rowSetCliente = new CachedRowSetImpl();
            rowSetCliente.populate(rs);

            JoinRowSet join = new JoinRowSetImpl();

            join.addRowSet(rowSetCliente, "Id");
            join.addRowSet(rowSetPedido, "IdCliente");

            /*
                DESCRIÇAO DAS TABELAS JOIN
                Coluna 1 = Cliente Id
                Coluna 2 = Cliente Nome
                Coluna 3 = Cliente Documento
                Coluna 4 = Cliente Saldo
                Coluna 5 = Cliente Ativo
                Coluna 6 = Pedido Id
                Coluna 7 = Pedido Data
                Coluna 8 = Pedido Valor
                Coluna 9 = Pedido idCliente
            */

            while (join.next()) {

                if(join.getInt(1) == idCliente) {

                    Pedido pedido = new Pedido();
                    pedido.setId(join.getInt(6));
                    pedido.setValor(join.getDouble(8));

                    Date data = join.getDate(7);
                    Instant instant = Instant.ofEpochMilli(data.getTime());
                    pedido.setData(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());

                    pedido.setCliente(
                            new ClienteDaoPostgres().
                                    getCliente(join.getInt(1))
                    );

                    pedidos.add(pedido);
                }
            }

            rowSetCliente.close();
            rowSetPedido.close();
            join.close();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return pedidos;
    }

    public Pedido getPedido(int idPedido) {

        try {
            Connection conn = Conexao.getConnection();

            String sql = String.format("SELECT * FROM Pedido WHERE Id = %d", idPedido);

            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT
            );
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(idPedido);
                pedido.setValor(rs.getDouble("Valor"));

                Date data = rs.getDate("Data");
                Instant instant = Instant.ofEpochMilli(data.getTime());
                pedido.setData(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());

                pedido.setCliente(
                        new ClienteDaoPostgres().
                                getCliente(rs.getInt("IdCliente"))
                );

                return pedido;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
