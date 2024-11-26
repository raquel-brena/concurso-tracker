package com.rb.web2.domain.user;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.enums.Role;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "role_id")
    private Role role;

    private String email;
    private String telefone;
    private String cargo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    @ManyToMany(mappedBy = "comissaoOrganizadora")
    private List<ProcessoSeletivo> processosComissao;

    @ManyToMany(mappedBy = "participantes")
    private List<ProcessoSeletivo> processosParticipante;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        this.role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.name().toUpperCase()))
                .forEach(authorities::add); 

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.login;
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
