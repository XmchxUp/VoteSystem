package com.xmchx.vote.controller;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.User;
import com.xmchx.vote.payload.UserProfile;
import com.xmchx.vote.payload.UserResponse;
import com.xmchx.vote.repository.UserRepository;
import com.xmchx.vote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/29 17:18
 */
@Controller
@Slf4j
public class UserInfoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('USER')")
    public String userInfo(Principal principal,
                           @PathVariable("username") String username,
                           Model model) {
        String loginName = principal.getName();
        User user = userRepository.findByUsernameOrEmail(loginName, loginName).orElseThrow(() -> new AppException(
                "user not exist."));
        if (!user.getUsername().equals(username)) {
            username = user.getUsername();
        }
        UserProfile userProfile = userService.getUserProfile(username);
        model.addAttribute("userProfile", userProfile);
        return "userinfo";
    }

    @GetMapping("/users/{username}/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile(Principal principal,
                              @PathVariable("username") String username,
                              Model model) {
        String loginName = principal.getName();
        User user = userRepository.findByUsernameOrEmail(loginName, loginName).orElseThrow(() -> new AppException(
                "user not exist."));
        if (!user.getUsername().equals(username)) {
            username = user.getUsername();
        }
        UserResponse userResponse = userService.getUserInfo(username);
        model.addAttribute("userResponse", userResponse);
        return "profile";
    }
}
