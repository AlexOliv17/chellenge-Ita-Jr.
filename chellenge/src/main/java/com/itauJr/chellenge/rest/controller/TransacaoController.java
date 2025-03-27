package com.itauJr.chellenge.rest.controller;
import com.itauJr.chellenge.domain.entity.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itauJr.chellenge.service.TransacaoService;

import java.io.IOException;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    @Autowired
    TransacaoService service;

    @PostMapping
    public ResponseEntity<?> recebeTransacao(@RequestBody Transacao transacao) throws Exception {
        return service.createTransacao(transacao);
    }

    @DeleteMapping
    public ResponseEntity<?> deletarTransacoes() throws IOException {
        return service.deleteTransacoes();
    }
}
