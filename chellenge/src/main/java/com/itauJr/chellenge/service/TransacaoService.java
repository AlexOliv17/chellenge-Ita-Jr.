package com.itauJr.chellenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itauJr.chellenge.domain.entity.Transacao;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

    public ResponseEntity<?> getStatsTransacoesLastOneMinute() throws IOException {
        Scanner scanner = new Scanner(new File("db.txt"));
        List<Transacao> transacoes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String transacao = scanner.nextLine();
            ObjectMapper mapper = new ObjectMapper();
            transacoes.add(mapper.readValue(transacao, Transacao.class));
        }
        try{
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastMinute = now.minusSeconds(60);
            DoubleSummaryStatistics stats = transacoes.stream()
                    .filter(transacao1 -> {
                        LocalDateTime dataTransacao = transacao1.getData();
                        return !dataTransacao.isBefore(lastMinute) && !dataTransacao.isAfter(now);
                    })
                    .mapToDouble(Transacao::getValor)
                    .summaryStatistics();
            Map<String, Object> result = new HashMap<>();
            if (stats.getCount() > 0) {
                result.put("Contagem:", stats.getCount());
                result.put("Soma:", stats.getSum());
                result.put("Média:", stats.getAverage());
                result.put("Minimo:", stats.getMin());
                result.put("Maximo:", stats.getMax());
            } else {
                result.put("Contagem:", 0L);    // Long para contagem
                result.put("Soma:", 0.0);       // Double para soma
                result.put("Média:", 0.0);      // Double para média
                result.put("Minimo:", 0.0);     // Double para mínimo
                result.put("Maximo:", 0.0);     // Double para máximo
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
