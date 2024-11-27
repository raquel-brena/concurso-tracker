package com.rb.web2.domain.vaga.mapper;

import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagasRequestDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.formacao.Formacao;

public class VagaMapper {

    // Método para mapear o DTO para a Entidade
    public static Vaga toEntity(VagasRequestDTO vagasRequestDTO, ProcessoSeletivo processoSeletivo, Formacao formacao) {
        return new Vaga(
            null,  // ID auto-gerado
            processoSeletivo,
            formacao,
            vagasRequestDTO.quantidade(),
            vagasRequestDTO.reserva(),
            vagasRequestDTO.remuneracao(),
            vagasRequestDTO.descricao(),
            vagasRequestDTO.taxaInscricao(),
            vagasRequestDTO.ativo(),
            null,  // Criado em (timestamp será gerado automaticamente)
            null   // Atualizado em (timestamp será gerado automaticamente)
        );
    }
}
