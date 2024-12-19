package com.rb.web2.domain.enums;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(
            Permissao.REGISTER_USER, Permissao.EDIT_USER, Permissao.VIEW_USER,
            Permissao.EDIT_PROCESSO_SELETIVO, Permissao.VIEW_PROCESSO_SELETIVO,
            Permissao.EDIT_CRITERIOS, Permissao.VIEW_CRITERIOS,
            Permissao.EDIT_INSCRICAO, Permissao.VIEW_INSCRICAO,
            Permissao.EDIT_INSCRICOES, Permissao.VIEW_INSCRICOES,
            Permissao.EDIT_RESULTADOS, Permissao.VIEW_RESULTADOS,
            Permissao.VIEW_RESULTADO, Permissao.EDIT_RESULTADO,
            Permissao.EDIT_MEMBRO_COMISSAO, Permissao.VIEW_MEMBRO_COMISSAO,
            Permissao.VIEW_AGENDA, Permissao.EDIT_AGENDA,
            Permissao.VIEW_DOCUMENTO, Permissao.EDIT_DOCUMENTO,
            Permissao.VIEW_DOCUMENTOS, Permissao.EDIT_DOCUMENTOS,
            Permissao.HOMOLOGAR_DOCUMENTO_INSCRICAO,
            Permissao.VIEW_CARGOS, Permissao.EDIT_CARGOS,
            Permissao.VIEW_INSTITUICAO, Permissao.EDIT_INSTITUICAO,
            Permissao.VIEW_PONTUACOES, Permissao.EDIT_PONTUACOES,
            Permissao.VIEW_VAGAS, Permissao.EDIT_VAGAS,
            Permissao.VIEW_ETAPAS, Permissao.EDIT_ETAPAS)),
    COORDENADOR(Set.of(
            Permissao.VIEW_INSCRICOES, Permissao.EDIT_INSCRICOES,
            Permissao.VIEW_RESULTADOS, Permissao.EDIT_RESULTADOS,
            Permissao.VIEW_PROCESSO_SELETIVO, Permissao.EDIT_PROCESSO_SELETIVO,
            Permissao.VIEW_PONTUACOES, Permissao.EDIT_PONTUACOES,
            Permissao.VIEW_USER, Permissao.EDIT_USER,
            Permissao.VIEW_AGENDA, Permissao.EDIT_AGENDA,
            Permissao.EDIT_MEMBRO_COMISSAO, Permissao.VIEW_MEMBRO_COMISSAO,
            Permissao.VIEW_DOCUMENTO, Permissao.EDIT_DOCUMENTO,
            Permissao.VIEW_DOCUMENTOS, Permissao.EDIT_DOCUMENTOS,
            Permissao.VIEW_VAGAS, Permissao.EDIT_VAGAS,
            Permissao.VIEW_CARGOS, Permissao.EDIT_CARGOS,
            Permissao.VIEW_INSTITUICAO, Permissao.EDIT_INSTITUICAO,
            Permissao.HOMOLOGAR_DOCUMENTO_INSCRICAO)),
    ASSISTENTE(Set.of(
            Permissao.VIEW_PROCESSO_SELETIVO,
            Permissao.VIEW_MEMBRO_COMISSAO,
            Permissao.VIEW_DOCUMENTOS,
            Permissao.HOMOLOGAR_DOCUMENTO_INSCRICAO,
            Permissao.VIEW_RESULTADO, Permissao.EDIT_RESULTADO,
            Permissao.VIEW_PONTUACOES, Permissao.EDIT_PONTUACOES,
            Permissao.EDIT_INSCRICOES, Permissao.VIEW_INSCRICOES,
            Permissao.EDIT_INSCRICAO, Permissao.VIEW_INSCRICAO,
            Permissao.VIEW_AGENDA, Permissao.EDIT_AGENDA,
            Permissao.VIEW_USER, Permissao.EDIT_USER,
            Permissao.VIEW_CRITERIOS, Permissao.EDIT_CRITERIOS)),
    CANDIDATO(Set.of(
            Permissao.VIEW_INSCRICAO, Permissao.EDIT_INSCRICAO,
            Permissao.VIEW_USER, Permissao.EDIT_USER,
            Permissao.VIEW_AGENDA, Permissao.EDIT_AGENDA,
            Permissao.VIEW_DOCUMENTOS, Permissao.EDIT_DOCUMENTOS,
            Permissao.VIEW_PROCESSO_SELETIVO)),
    USER(Set.of(
            Permissao.REGISTER_USER, Permissao.EDIT_USER, Permissao.VIEW_USER,
            Permissao.VIEW_AGENDA,
            Permissao.VIEW_PROCESSO_SELETIVO));

    private final Set<Permissao> permissions;

    Role(Set<Permissao> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissao> getPermissions() {
        return permissions;
    }
}
