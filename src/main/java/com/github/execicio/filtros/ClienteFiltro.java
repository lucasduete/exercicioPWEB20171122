package com.github.execicio.filtros;

import javax.sql.RowSet;
import javax.sql.rowset.Predicate;
import java.sql.SQLException;

public class ClienteFiltro implements Predicate {

    public static final String COLUMN_NAME = "Id";
    private int idCliente;

    public ClienteFiltro(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public boolean evaluate(RowSet rs) {
        try {
            int id = rs.getInt(COLUMN_NAME);

            return (id == idCliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean evaluate(Object value, int column) throws SQLException {
        return false;
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {
        return false;
    }
}
