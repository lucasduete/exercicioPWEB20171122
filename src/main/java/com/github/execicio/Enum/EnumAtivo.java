package com.github.execicio.Enum;

public enum EnumAtivo {

    ATIVO("ativo"), INATIVO("inativo");

    private String status;

    EnumAtivo(String status) {
        this.status = status;
    }

}
