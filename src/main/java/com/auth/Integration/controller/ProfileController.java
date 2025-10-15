package com.auth.Integration.controller;

import com.auth.Integration.entity.User;
import com.auth.Integration.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /profile (view authenticated user's profile)
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) {
            return "redirect:/";
        }

        String email = principal.getAttribute("email");
        if (email == null) {
            email = principal.getAttribute("login");
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            model.addAttribute("displayName", user.getDisplayName());
            model.addAttribute("bio", user.getBio());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("picture", user.getAvatarUrl());
            model.addAttribute("name", user.getDisplayName());
        } else {
            model.addAttribute("displayName", principal.getAttribute("name"));
            model.addAttribute("bio", "");
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("picture", principal.getAttribute("avatar_url"));
            model.addAttribute("name", principal.getAttribute("name"));
        }
        return "profile";
    }

    // POST /profile (update displayName & bio)
    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal OAuth2User principal,
                                @RequestParam String displayName,
                                @RequestParam(required = false) String bio) {
        if (principal == null) return "redirect:/";

        String email = principal.getAttribute("email");
        if (email == null) email = principal.getAttribute("login");

        userRepository.findByEmail(email).ifPresent(user -> {
            user.setDisplayName(displayName);
            user.setBio(bio);
            userRepository.save(user);
        });

        return "redirect:/profile";
    }

    // GET /logout (logout & redirect to home)
    // By default Spring Security uses POST, so configure logout to allow GET requests in your SecurityConfig if needed:
    // .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/"))
}
