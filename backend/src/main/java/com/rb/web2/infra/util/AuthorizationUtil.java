package com.rb.web2.infra.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.rb.web2.domain.user.User;
import com.rb.web2.services.UserService;
import com.rb.web2.shared.exceptions.BadRequestException;

public class AuthorizationUtil {

    private final UserService userService;

    public AuthorizationUtil(UserService userService) {
        this.userService = userService;
    }

    public <T> void verificarPermissaoOuComissao(
        T entityId,
        String authority,
        EntityResolver<T> resolver,
        EntityAssociationChecker<T> associationChecker
    ) {
        // Obtém o usuário logado
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(login);

        // Verifica a permissão
        if (user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals(authority))) {
            throw new BadRequestException("Usuário não possui a permissão necessária: " + authority);
        }

        System.out.println(entityId + " EntidadeId");

        // Verifica a associação, se um ID for fornecido
        if (entityId != null) {
            var entity = resolver.resolveEntity(entityId);
            if (!associationChecker.isUserAssociated(entity, (User) user)) {
                throw new BadRequestException("Usuário não está associado à entidade especificada.");
            }
        }
    }

    @FunctionalInterface
    public interface EntityResolver<T> {
        Object resolveEntity(T id);
    }

    @FunctionalInterface
    public interface EntityAssociationChecker<T> {
        boolean isUserAssociated(Object entity, User user);
    }
}
