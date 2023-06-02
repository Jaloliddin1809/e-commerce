package uz.g4.ecommerce.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.entity.history.HistoryEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, UUID> {
    @Query("select h from histories h where h.user.chatId = :chatId")
    List<HistoryEntity> getUserHistories(@Param("chatId") Long chatId);
}
