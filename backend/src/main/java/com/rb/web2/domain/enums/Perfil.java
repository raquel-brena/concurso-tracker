package com.rb.web2.domain.enums;

import java.util.EnumSet;
import java.util.Set;

public enum Perfil {
    ADMIN(EnumSet.of(
        Permissao.LOGIN_USER,
        Permissao.LOGOUT_USER,
        Permissao.REGISTER_USER,
        Permissao.EDIT_USER,
        Permissao.VIEW_USER,
        Permissao.VIEW_USERS,
        Permissao.EDIT_USERS,
        Permissao.EDIT_PROCESSO_SELETIVO,
        Permissao.VIEW_PROCESSO_SELETIVO,
        Permissao.VIEW_CRITERIOS,
        Permissao.EDIT_CRITERIOS,
        Permissao.EDIT_INSCRICAO,
        Permissao.VIEW_INSCRICAO,
        Permissao.EDIT_INSCRICOES,
        Permissao.VIEW_INSCRICOES,
        Permissao.VIEW_RESULTADOS,
        Permissao.EDIT_RESULTADOS,
        Permissao.EDIT_RESULTADO,
        Permissao.VIEW_RESULTADO,
        Permissao.EDIT_MEMBRO_COMISSAO,
        Permissao.VIEW_MEMBRO_COMISSAO,
        Permissao.VIEW_AGENDA,
        Permissao.EDIT_AGENDA,
        Permissao.VIEW_DOCUMENTO,
        Permissao.EDIT_DOCUMENTO,
        Permissao.VIEW_DOCUMENTOS,
        Permissao.EDIT_DOCUMENTOS,
        Permissao.HOMOLOGAR_DOCUMENTO_INSCRICAO,
        Permissao.VIEW_CARGOS,
        Permissao.EDIT_CARGOS,
        Permissao.VIEW_INSTITUICAO,
        Permissao.EDIT_INSTITUICAO,
        Permissao.VIEW_PONTUACOES,
        Permissao.EDIT_PONTUACOES,
        Permissao.VIEW_VAGAS,
        Permissao.EDIT_VAGAS,
        Permissao.VIEW_ETAPAS,
        Permissao.EDIT_ETAPAS
    )),
    COORDENADOR(EnumSet.of(
        Permissao.LOGIN_USER,
        Permissao.LOGOUT_USER,
        Permissao.VIEW_USER,
        Permissao.EDIT_USERS,
        Permissao.EDIT_PROCESSO_SELETIVO,
        Permissao.VIEW_PROCESSO_SELETIVO,
        Permissao.VIEW_CRITERIOS,
        Permissao.EDIT_CRITERIOS,
        Permissao.VIEW_INSCRICAO,
        Permissao.VIEW_INSCRICOES,
        Permissao.VIEW_RESULTADOS,
        Permissao.EDIT_RESULTADOS,
        Permissao.VIEW_RESULTADO,
        Permissao.VIEW_MEMBRO_COMISSAO,
        Permissao.EDIT_MEMBRO_COMISSAO,
        Permissao.VIEW_AGENDA,
        Permissao.EDIT_AGENDA,
        Permissao.VIEW_DOCUMENTOS,
        Permissao.VIEW_CARGOS,
        Permissao.VIEW_INSTITUICAO,
        Permissao.VIEW_PONTUACOES,
        Permissao.VIEW_VAGAS,
        Permissao.VIEW_ETAPAS
    )),
    ASSISTENTE(EnumSet.of(
        Permissao.LOGIN_USER,
        Permissao.LOGOUT_USER,
        Permissao.VIEW_USER,
        Permissao.VIEW_PROCESSO_SELETIVO,
        Permissao.VIEW_CRITERIOS,
        Permissao.VIEW_INSCRICAO,
        Permissao.VIEW_INSCRICOES,
        Permissao.VIEW_RESULTADOS,
        Permissao.VIEW_RESULTADO,
        Permissao.VIEW_MEMBRO_COMISSAO,
        Permissao.VIEW_AGENDA,
        Permissao.VIEW_DOCUMENTOS,
        Permissao.VIEW_CARGOS,
        Permissao.VIEW_INSTITUICAO,
        Permissao.VIEW_PONTUACOES,
        Permissao.VIEW_VAGAS,
        Permissao.VIEW_ETAPAS
    )),
    CANDIDATO(EnumSet.of(
        Permissao.LOGIN_USER,
        Permissao.LOGOUT_USER,
        Permissao.VIEW_USER,
        Permissao.EDIT_USER,
        Permissao.VIEW_INSCRICAO,
        Permissao.EDIT_INSCRICAO,
        Permissao.VIEW_RESULTADO,
        Permissao.VIEW_DOCUMENTOS,
        Permissao.EDIT_DOCUMENTOS,
        Permissao.VIEW_PROCESSO_SELETIVO
    )),
    USER(EnumSet.of(
        Permissao.LOGIN_USER,
        Permissao.LOGOUT_USER,
        Permissao.VIEW_USER,
        Permissao.VIEW_PROCESSO_SELETIVO
    ));

    private final Set<Permissao> permissoes;

    Perfil(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }
}
