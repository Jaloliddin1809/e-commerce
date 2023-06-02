package uz.g4.ecommerce.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findUserEntitiesById(UUID id);
    boolean existsByUsername(String username);
    @Query("SELECT u FROM users u WHERE u.roles NOT IN (:excludedRoles)")
    List<UserEntity> findAllUsersExceptRoles(@Param("excludedRoles") List<Role> excludedRoles);
}
