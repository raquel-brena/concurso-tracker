package com.rb.web2.services;

import com.rb.web2.domain.etapa.Etapa;
import com.rb.web2.domain.etapa.dto.EtapaRequestDTO;
import com.rb.web2.domain.etapa.dto.EtapaResponseDTO;
import com.rb.web2.repositories.EtapaRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtapaService {

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private ProcessoSeletivoRepository processoSeletivoRepository;

    public EtapaResponseDTO create(EtapaRequestDTO requestDTO) {
        var processoSeletivo = processoSeletivoRepository.findById(requestDTO.processoSeletivoId())
                .orElseThrow(() -> new NotFoundException("Processo Seletivo não encontrado"));

        Etapa etapa = new Etapa();
        etapa.setNome(requestDTO.nome());
        etapa.setProcessoSeletivo(processoSeletivo);

        etapa = etapaRepository.save(etapa);

        return EtapaResponseDTO.from(etapa);
    }

    public List<EtapaResponseDTO> findAll() {
        return etapaRepository.findAll()
                .stream()
                .map(EtapaResponseDTO::from)
                .collect(Collectors.toList());
    }

    public EtapaResponseDTO findById(Long id) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Etapa não encontrada"));
        return EtapaResponseDTO.from(etapa);
    }

    public EtapaResponseDTO update(Long id, EtapaRequestDTO requestDTO) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Etapa não encontrada"));

        var processoSeletivo = processoSeletivoRepository.findById(requestDTO.processoSeletivoId())
                .orElseThrow(() -> new NotFoundException("Processo Seletivo não encontrado"));

        etapa.setNome(requestDTO.nome());
        etapa.setProcessoSeletivo(processoSeletivo);

        etapa = etapaRepository.save(etapa);

        return EtapaResponseDTO.from(etapa);
    }

    public void delete(Long id) {
        if (!etapaRepository.existsById(id)) {
            throw new NotFoundException("Etapa não encontrada");
        }
        etapaRepository.deleteById(id);
    }
}
