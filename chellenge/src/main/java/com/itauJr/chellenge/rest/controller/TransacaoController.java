package com.itauJr.chellenge.rest.controller;
import com.itauJr.chellenge.domain.entity.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itauJr.chellenge.service.TransacaoService;

@RestController
@RequestMapping
public class TransacaoController {
    @Autowired
    TransacaoService service;

    @PostMapping("/transacao")
    public ResponseEntity<?> recebeTransacao(@RequestBody Transacao transacao) throws Exception {
        return service.createTransacao(transacao);
    }
}
