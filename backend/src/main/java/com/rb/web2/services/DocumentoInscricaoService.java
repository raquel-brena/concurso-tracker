package com.rb.web2.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documentoInscricao.DocumentoInscricao;
import com.rb.web2.domain.documentoInscricao.dto.DocInscricaoRequestDTO;
import com.rb.web2.domain.documentoInscricao.dto.HomologarDocInscricaoRequestDTO;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.DocumentoInscricaoRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class DocumentoInscricaoService {

    @Autowired
    private DocumentoInscricaoRepository repository;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private InscricaoService inscricaoService;

    @Autowired
    private UserService userService;

    private AuthorizationUtil authorizationUtil;

    @PostConstruct
    public void init() {
        this.authorizationUtil = new AuthorizationUtil(userService);
    }

    // @TODO: Fazer a verificação de permissão de Visualização

    private void verificarPermissaoDeCriacaoOuAlteracao(Long documentoInscricaoId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                documentoInscricaoId,
                // @TODO: verificar permissão
                "EDIT_DOCUMENTO",
                id -> repository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Documento de inscrição não encontrado.")),
                (entity, user) -> {
                    DocumentoInscricao documentoInscricao = (DocumentoInscricao) entity;
                    return documentoInscricao.getInscricao().getCandidato().equals(user);
                });
    }

    private void verificarPermissaoHomologacao(Long documentoInscricaoId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                documentoInscricaoId,
                "HOMOLOGAR_DOCUMENTO",
                id -> repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Documento de inscrição não encontrado.")),
                (entity, user) -> {
                    DocumentoInscricao documentoInscricao = (DocumentoInscricao) entity;
                    return documentoInscricao.getInscricao().getVaga().getProcessoSeletivo().getComissaoOrganizadora()
                            .contains(user);
                });
    }

    public DocumentoInscricao criarDocumentoInscricao(DocInscricaoRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao(null);
        Inscricao inscricao = inscricaoService.buscarInscricaoPorId(dto.inscricaoId());

        Documento documento = documentoService.buscarDocumentoPorId(dto.documentoId());

        DocumentoInscricao documentoInscricao = new DocumentoInscricao();
        documentoInscricao.setInscricao(inscricao);
        documentoInscricao.setDocumento(documento);
        documentoInscricao.setHomologado(false);
        return repository.save(documentoInscricao);
    }

    public DocumentoInscricao findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Documento Inscricao não encontrado"));
    }

    public DocumentoInscricao homologarDocumento(HomologarDocInscricaoRequestDTO dto) {
        verificarPermissaoHomologacao(dto.documentoInscricaoId());

        DocumentoInscricao documentoInscricao = this.findById(dto.documentoInscricaoId());
        Agenda agenda = documentoInscricao.getInscricao().getVaga().getProcessoSeletivo().getAgenda();

        if (!this.dataHomologacaoValida(agenda.getInicioHomologacao(), agenda.getFimHomologacao())) {
            throw new BadRequestException("Fora do período de homologação");
        }

        documentoInscricao.setHomologado(dto.homologado());
        documentoInscricao.setMotivoRejeicao(dto.homologado() ? null : dto.motivoRejeicao());

        return repository.save(documentoInscricao);
    }

    public boolean dataHomologacaoValida(LocalDate homologacaoInicio, LocalDate homologacaoFim) {
        var dataAtual = LocalDate.now();
        return dataAtual.isAfter(homologacaoFim) && dataAtual.isBefore(homologacaoInicio);
    }
}
