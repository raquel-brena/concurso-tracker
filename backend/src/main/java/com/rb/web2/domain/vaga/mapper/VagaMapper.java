package com.rb.web2.domain.vaga.mapper;

import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagaRequestDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.formacao.Formacao;

public class VagaMapper {

    // Método para mapear o DTO para a Entidade
    public static Vaga toEntity(VagaRequestDTO vagaRequestDTO, ProcessoSeletivo processoSeletivo, Formacao formacao) {
        return new Vaga(
            null,  // ID auto-gerado
            processoSeletivo,
            formacao,
            vagaRequestDTO.quantidade(),
            vagaRequestDTO.reserva(),
            vagaRequestDTO.remuneracao(),
            vagaRequestDTO.descricao(),
            vagaRequestDTO.taxaInscricao(),
            vagaRequestDTO.ativo(),
            null,  // Criado em (timestamp será gerado automaticamente)
            null   // Atualizado em (timestamp será gerado automaticamente)
        );
    }
}
