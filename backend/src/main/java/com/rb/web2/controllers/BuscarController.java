package com.rb.web2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

@RestController
@RequestMapping("/api/buscar")
public class BuscarController {

    @Autowired
    ProcessoSeletivoService processoSeletivoService;

    @GetMapping
    public ResponseEntity<RestSuccessMessage> search(
            @RequestParam String q,
            @RequestParam String type) {

        if ("processos".equalsIgnoreCase(type)) {
            List<ProcessoSeletivo> result = processoSeletivoService
                    .buscarProcessos(q);
            RestSuccessMessage successMessage = new RestSuccessMessage("Processos encontrados com sucesso", result);
            return ResponseEntity.ok(successMessage);
        } else if ("vagas".equalsIgnoreCase(type)) {
            // Exemplo de outros tipos de busca
        }

        List<ProcessoSeletivo> result = processoSeletivoService.buscarProcessos(q);
        RestSuccessMessage successMessage = new RestSuccessMessage("Processos encontrados com sucesso", result);
        return ResponseEntity.ok(successMessage);
    }
}
