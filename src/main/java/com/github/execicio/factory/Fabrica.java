package com.github.execicio.factory;

public class Fabrica {

    public FabricaDaoPostgres criarDaoPostgres() {
        return new FabricaDaoPostgres();
    }
}
