package com.rb.web2.domain.vaga.mapper;

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagasRequestDTO;

public class VagaMapper {

   public static Vaga toEntity(VagasRequestDTO dto, ProcessoSeletivo processoSeletivo, Cargo cargo) {
        Vaga vaga = new Vaga();
        vaga.setProcessoSeletivo(processoSeletivo);
        vaga.setCargo(cargo);
        vaga.setQuantidade(dto.quantidade());
        vaga.setDescricao(dto.descricao());
        vaga.setTaxaInscricao(dto.taxaInscricao());
        vaga.setAtivo(true);
        return vaga;
    }
    
}
