package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.domain.instituicao.dto.InstituicaoResponseDTO;
import com.rb.web2.repositories.InstituicaoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class InstituicaoService {

  @Autowired
  private InstituicaoRepository instituicaoRepository;

  public InstituicaoResponseDTO create(InstituicaoResponseDTO dto) {
    Instituicao instituicao = new Instituicao();
    instituicao.setNome(dto.nome());
    instituicao.setLocal(dto.local());
    instituicao.setAtivo(true);
    instituicao = instituicaoRepository.save(instituicao);

    // Converter a entidade salva para o DTO de resposta
    return InstituicaoResponseDTO.from(instituicao);
  }

  public InstituicaoResponseDTO getById(String id) {
    return this.instituicaoRepository.findById(id)
        .map(InstituicaoResponseDTO::from)
        .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));
  }

  public Instituicao getInstituicaoById(String id) {
    return instituicaoRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Instituição com o ID " + id + " não encontrada."));
  }

  public List<InstituicaoResponseDTO> getAllInstituicaos() {
    List<Instituicao> instituicoes = instituicaoRepository.findAll();
    return instituicoes.stream().map(InstituicaoResponseDTO::from).collect(Collectors.toList());
  }

  public InstituicaoResponseDTO updateInstituicao(
      String id,
      Instituicao updatedInstituicao) {
    if (checkInstituicaoExists(id)) {
      updatedInstituicao.setId(id);
      updatedInstituicao = instituicaoRepository.save(updatedInstituicao);
      return InstituicaoResponseDTO.from(updatedInstituicao);
    }

    return null;
  }

  public boolean softDelete(String id) {
    Instituicao existingInstituicao = getInstituicaoById(id);
    if (checkInstituicaoExists(id)) {
      existingInstituicao.setAtivo(false);

      instituicaoRepository.save(existingInstituicao);
      return true;
    }

    return false;
  }

  public boolean checkInstituicaoExists(String id) {
    return instituicaoRepository.existsById(id);
  }
}
