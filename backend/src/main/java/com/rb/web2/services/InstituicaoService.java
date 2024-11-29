package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.repositories.InstituicaoRepository;

@Service
public class InstituicaoService {

  @Autowired
  private InstituicaoRepository instituicaoRepository;

  public Instituicao create(Instituicao instituicao) {
    return instituicaoRepository.save(instituicao);
  }

  public Optional<Instituicao> getInstituicaoById(String id) {
    return instituicaoRepository.findById(id);
  }

  public List<Instituicao> getAllInstituicaos() {
    return instituicaoRepository.findAll();
  }

  public Instituicao updateInstituicao(
    String id,
    Instituicao updatedInstituicao
  ) {
    if (checkInstituicaoExists(id)) {
      updatedInstituicao.setId(id);
      return instituicaoRepository.save(updatedInstituicao);
    }

    return null;
  }

  public boolean deleteInstituicao(String id) {
    Optional<Instituicao> existingInstituicao = getInstituicaoById(id);
    if (checkInstituicaoExists(id)) {
      Instituicao instituicao = existingInstituicao.get();
      instituicao.setAtivo(false); // Atualiza o campo ativo para false, marcando como inativa

      instituicaoRepository.save(instituicao);
      return true;
    }
    
    return false;
  }

  public boolean checkInstituicaoExists(String id) {
    return instituicaoRepository.existsById(id);
  }
}
