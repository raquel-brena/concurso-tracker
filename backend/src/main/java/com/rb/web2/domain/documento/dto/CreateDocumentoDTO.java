package com.rb.web2.domain.documento.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateDocumentoDTO (
    String nome, 
    String tipo, 
    String userId) {

}
