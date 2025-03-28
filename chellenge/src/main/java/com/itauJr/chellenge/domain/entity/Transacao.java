package com.itauJr.chellenge.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class Transacao {
    private double valor;
    @JsonProperty("data")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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

