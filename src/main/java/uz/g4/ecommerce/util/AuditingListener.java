package uz.g4.ecommerce.util;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("auditorAware")
public class AuditingListener implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return Optional.of(auth.getName());
    }
}
