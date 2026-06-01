package JMR.ModularTickets.auth.infraestructure.security;

import JMR.ModularTickets.auth.domain.CurrentUserProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityCurrentUserProvider
        implements CurrentUserProvider {

    @Override
    public String getUsername() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}