package uz.g4.ecommerce.repository.user;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Past;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.domain.entity.user.UserState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findUserEntitiesById(UUID id);
    boolean existsByUsername(String username);
    @Query("select u from users u where u.name like %:name%")
    List<UserEntity> search(@Param("name") String name);
  
    @Query("SELECT u FROM users u WHERE u.roles NOT IN (:excludedRoles)")
    List<UserEntity> findAllUsersExceptRoles(@Param("excludedRoles") List<Role> excludedRoles);
    
    Optional<UserEntity> findUserEntityByChatId (Long chatId);

    @Modifying
    @Transactional
    @Query("update users  u set u.userState = :state where u.chatId = :chatId")
    void updateUserState(@Param("state") UserState state, @Param("chatId") Long chatId);


    @Modifying
    @Transactional
    @Query("update users u set u.balance = u.balance + :balance where u.chatId = :chatId")
    void updateUserBalance (@Param("balance") Double balance, @Param("chatId") Long chatId);

    @Modifying
    @Transactional
    @Query("update users u set u.balance = u.balance - :minus where u.id = :id")
    void cutUserBalance(@Param("id") UUID id, @Param("minus") double minusAmount);
    @Query(value = "select u from users u where u.username<>:username")
    List<UserEntity> findAllExceptUser(@Param("username") String username);
}
