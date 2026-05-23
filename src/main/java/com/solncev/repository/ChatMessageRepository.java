package com.solncev.repository;

import com.solncev.model.ChatMessage;
import com.solncev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findTop50ByOrderBySentAtDesc();

    List<ChatMessage> findByAuthor(User author);

    @Query("select m from ChatMessage m where lower(m.content) like lower(concat('%', :content, '%')) order by m.sentAt desc")
    List<ChatMessage> searchByContent(@Param("content") String content);
}
