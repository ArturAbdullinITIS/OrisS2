package com.solncev.controller;

import com.solncev.dto.ChatMessageResponse;
import com.solncev.repository.ChatMessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/admin/messages")
public class AdminChatController {

    private final ChatMessageRepository chatMessageRepository;

    public AdminChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping
    public List<ChatMessageResponse> findAll() {
        return chatMessageRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(message -> message.getSentAt()))
                .map(ChatMessageResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!chatMessageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        chatMessageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
