package com.xmchx.vote.controller;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.User;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.payload.UserProfile;
import com.xmchx.vote.payload.UserRequest;
import com.xmchx.vote.payload.UserResponse;
import com.xmchx.vote.repository.UserRepository;
import com.xmchx.vote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @Autowired
    private SessionRegistry sessionRegistry;

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

    @PostMapping("/users/update")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity saveProfile(Principal principal,
                                      @RequestBody UserRequest userRequest) {
        String loginName = principal.getName();
        User user = userRepository.findByUsernameOrEmail(loginName, loginName).orElseThrow(() -> new AppException(
                "user not exist."));
        user.setUsername(userRequest.getUsername());
        user.setAvatar(userRequest.getAvatar());
        user.setMotto(userRequest.getMotto());
        user.setName(userRequest.getName());
        userRepository.save(user);
        return new ResponseEntity(new ApiResponse(true, "User update successfully"), HttpStatus.OK);
    }


    private void removeSession(Object principal) {
        List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
        for (SessionInformation sessionInformation : sessionInformations) {
            sessionInformation.expireNow();
        }
    }
}
