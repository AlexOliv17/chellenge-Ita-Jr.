package com.itauJr.chellenge.domain.entity;

import java.time.LocalDateTime;

public class Transacao {
    private double valor;
    private LocalDateTime dataHora;

    public double getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return dataHora;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}

