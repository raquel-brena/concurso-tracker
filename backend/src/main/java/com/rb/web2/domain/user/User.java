package com.rb.web2.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.enums.Perfil;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefone;
    private String cargo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    @ManyToMany(mappedBy = "comissaoOrganizadora")
    private List<ProcessoSeletivo> processosComissao;

    @JsonBackReference
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    public User(String cpf, String password, Perfil perfil) {
        this.cpf = cpf;
        this.password = password;
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(this.perfil.getPermissoes().stream()
                .map(p -> new SimpleGrantedAuthority("ROLE_" + p.name()))
                .collect(Collectors.toList()));

        authorities.add(new SimpleGrantedAuthority(
            "ROLE_" + this.perfil.name().toUpperCase()));

        System.out.println("Authorities: " + authorities);
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
