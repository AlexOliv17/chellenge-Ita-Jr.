package com.itauJr.chellenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itauJr.chellenge.domain.entity.Transacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class TransacaoService {

    public ResponseEntity<?> createTransacao(Transacao transacao) throws IOException {
        if (transacao == null || transacao.getData() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else if(transacao.getData().isAfter(LocalDateTime.now()) || transacao.getData().equals(LocalDateTime.now()) || transacao.getValor() < 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String jsonTransacao = mapper.writeValueAsString(transacao);
            try{
                FileWriter db = new FileWriter("db.txt", true);
                db.write(jsonTransacao+ "," + System.lineSeparator());
                db.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    public ResponseEntity<?> deleteTransacoes() throws IOException {
        FileWriter db = new FileWriter("db.txt");
        db.flush();
        db.close();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
