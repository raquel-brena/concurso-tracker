package com.rb.web2.domain.processoComissao;

import java.time.LocalDateTime;
import java.util.Optional;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(ProcessoComissaoId.class) 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "processo_comissao")
public class ProcessoComissao {
    @Id
    @Column(name = "processo_seletivo_id", nullable = false)
    private String processoSeletivoId;

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "processo_seletivo_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProcessoSeletivo processoSeletivo;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public ProcessoComissao(ProcessoSeletivo processoSeletivo, User user) {
        this.processoSeletivoId = processoSeletivo.getId();
        this.userId = user.getId();
        this.processoSeletivo = processoSeletivo;
        this.user = user;
    }

    public Optional<CriterioAvaliacao> findByEtapaProcessoSeletivoId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEtapaProcessoSeletivoId'");
    }
}
