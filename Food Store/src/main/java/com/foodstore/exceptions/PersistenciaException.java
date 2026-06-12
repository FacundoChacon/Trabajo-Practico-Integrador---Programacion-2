package com.foodstore.exceptions;

import java.sql.SQLException;

public class PersistenciaException extends RuntimeException {
    private final int errorCode;
    private final String sqlState;

    public PersistenciaException(String mensaje, SQLException causa) {
        super(mensaje, causa);
        this.errorCode = causa.getErrorCode();
        this.sqlState = causa.getSQLState();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getSQLState() {
        return sqlState;
    }
}