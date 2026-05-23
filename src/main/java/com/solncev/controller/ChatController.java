package com.solncev.controller;

import com.solncev.dto.ChatMessageResponse;
import com.solncev.model.ChatMessage;
import com.solncev.model.User;
import com.solncev.repository.ChatMessageRepository;
import com.solncev.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatController(ChatMessageRepository chatMessageRepository,
                          UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String chat(Model model, Principal principal) {
        model.addAttribute("messages", recentMessages());
        model.addAttribute("username", principal.getName());
        return "chat";
    }

    @GetMapping("/public")
    public String publicChat(Model model) {
        model.addAttribute("messages", recentMessages());
        return "public_chat";
    }

    @GetMapping("/my")
    public String myMessages(Model model, Principal principal) {
        User author = findCurrentUser(principal);
        model.addAttribute("messages", chatMessageRepository.findByAuthor(author)
                .stream()
                .map(ChatMessageResponse::from)
                .toList());
        return "my_messages";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        User currentUser = findCurrentUser(principal);
        ChatMessage message = chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!message.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can delete only your own messages");
        }

        chatMessageRepository.delete(message);
        return "redirect:/chat/my";
    }

    private List<ChatMessageResponse> recentMessages() {
        List<ChatMessageResponse> messages = new ArrayList<>(chatMessageRepository.findTop50ByOrderBySentAtDesc()
                .stream()
                .map(ChatMessageResponse::from)
                .toList());
        Collections.reverse(messages);
        return messages;
    }

    private User findCurrentUser(Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Authentication required");
        }
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new AccessDeniedException("User not found"));
    }
}
