package com.rb.web2.domain.vaga.mapper;

import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagasRequestDTO;
import com.rb.web2.domain.formacao.Cargo;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

public class VagaMapper {

   public static Vaga toEntity(VagasRequestDTO vagasRequestDTO, ProcessoSeletivo processoSeletivo, Cargo cargo) {
        Vaga vaga = new Vaga();
        vaga.setProcessoSeletivo(processoSeletivo);
        vaga.setCargo(cargo);
        vaga.setQuantidade(vagasRequestDTO.quantidade());
        vaga.setDescricao(vagasRequestDTO.descricao());
        vaga.setTaxaInscricao(vagasRequestDTO.taxaInscricao());
        vaga.setAtivo(vagasRequestDTO.ativo());
        return vaga;
    }
}
