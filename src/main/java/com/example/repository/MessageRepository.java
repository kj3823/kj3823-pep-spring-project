package com.example.repository;

// public interface MessageRepository {
// }
import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value="SELECT * FROM Message where posted_by = ?1", nativeQuery = true)
    List<Message> getMessagesByPosted_by(Integer account_id);

    @Modifying // Instructs Spring it can modify existing records
    @Query(value="DELETE from Message where message_id = ?1", nativeQuery = true)
    Integer deleteMessageByMessage_id(Integer message_id);

    @Modifying // Instructs Spring it can modify existing records
    @Query(value="UPDATE Message SET message_text = ?1 where message_id = ?2", nativeQuery = true)
    Integer updateMessageByMessage_id(String messageText, Integer message_id);
}

