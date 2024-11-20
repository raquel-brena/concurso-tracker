package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.repositories.DocumentoRepository;

@Service
public class DocumentoService {

  @Autowired
  private DocumentoRepository repository;

  public Documento create(Documento Documento) {
    return repository.save(Documento);
  }

  public Optional<Documento> getDocumentoById(Long id) {
    return repository.findById(id);
  }

  public List<Documento> getAllDocumentos() {
    return repository.findAll();
  }

  // public Documento updateDocumento(
  //   String id,
  //   Documento updatedDocumento
  // ) {
  //   if (checkDocumentoExists(id)) {
  //     updatedDocumento.setId(id);
  //     return repository.save(updatedDocumento);
  //   }

  //   return null;
  // }

  // public boolean deleteDocumento(String id) {
  //   var existingDocumento = this.getDocumentoById(id);
  //   if (existingDocumento.isPresent()) {
  //     Documento Documento = existingDocumento.get();
  //     Documento.setAtivo(false); // Atualiza o campo ativo para false, marcando como inativa

  //     repository.save(Documento);
  //     return true;
  //   }
    
  //   return false;
  // }

  // public boolean checkDocumentoExists(String id) {
  //   return repository.existsById(id);
  // }
}
