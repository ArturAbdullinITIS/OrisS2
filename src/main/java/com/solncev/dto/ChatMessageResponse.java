package com.solncev.dto;

import com.solncev.model.ChatMessage;

import java.time.LocalDateTime;

public class ChatMessageResponse {
    private Long id;
    private String content;
    private LocalDateTime sentAt;
    private Long authorId;
    private String authorUsername;

    public static ChatMessageResponse from(ChatMessage message) {
        ChatMessageResponse response = new ChatMessageResponse();
        response.setId(message.getId());
        response.setContent(message.getContent());
        response.setSentAt(message.getSentAt());
        response.setAuthorId(message.getAuthor().getId());
        response.setAuthorUsername(message.getAuthor().getUsername());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
