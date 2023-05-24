package uz.g4.ecommerce.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.cart.CartEntity;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    private String name;
    @Column(unique = true, nullable = false)
    private String username;  // email or phone number
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;
    @OneToOne(mappedBy = "owner")
    private CartEntity cart;


    private Boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.enabled;
    }
}
