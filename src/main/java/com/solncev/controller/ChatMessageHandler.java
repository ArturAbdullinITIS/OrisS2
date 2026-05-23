package com.solncev.controller;

import com.solncev.dto.ChatMessageRequest;
import com.solncev.dto.ChatMessageResponse;
import com.solncev.model.ChatMessage;
import com.solncev.model.User;
import com.solncev.repository.ChatMessageRepository;
import com.solncev.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ChatMessageHandler {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageHandler(ChatMessageRepository chatMessageRepository,
                              UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageResponse send(ChatMessageRequest request, Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Authentication required");
        }

        String content = request == null ? "" : request.getContent();
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Message content must not be blank");
        }

        User author = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        ChatMessage message = new ChatMessage();
        message.setContent(content.trim());
        message.setSentAt(LocalDateTime.now());
        message.setAuthor(author);

        return ChatMessageResponse.from(chatMessageRepository.save(message));
    }
}
