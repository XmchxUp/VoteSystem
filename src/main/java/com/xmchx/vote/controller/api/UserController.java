package com.xmchx.vote.controller.api;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.Role;
import com.xmchx.vote.model.User;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.payload.UserRequest;
import com.xmchx.vote.payload.UserResponse;
import com.xmchx.vote.repository.RoleRepository;
import com.xmchx.vote.repository.UserRepository;
import com.xmchx.vote.service.UserService;
import com.xmchx.vote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/9 20:19
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public PagedResponse<UserResponse> list(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sidx", defaultValue = "createTime") String sidx,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        if ("roleName".equals(sidx)) {
            sidx = "createTime";
        }
        return userService.getAllUsers(page, size, sidx, order);
    }

    @GetMapping("/search")
    public PagedResponse<UserResponse> search(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        return userService.getAllUsersByKeyword(page, size, keyword);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> info(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(
                "User not exist."));
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new ResponseEntity(new ApiResponse(false, "ids is null!"),
                    HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteUsersByIdIn(Arrays.asList(ids));

        return new ResponseEntity(new ApiResponse(true, "delete users successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User u = new User(userRequest.getName(), userRequest.getUsername(), userRequest.getEmail(),
                userRequest.getPassword());
        u.setCreateTime(new Date());
        u.setEncodePassword(u.getPassword());
        u.setAvatar(userRequest.getAvatar());
        List<Role> roles = roleRepository.findAllById(userRequest.getRoleIds());
        Set<Role> set = new HashSet<>(roles);
        u.setRoles(set);
        userRepository.save(u);
        return new ResponseEntity(new ApiResponse(true, "User save successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        User u = userRepository.findById(userRequest.getId()).orElseThrow(() -> new AppException(
                "User not exist."));
        u.setAvatar(userRequest.getAvatar());
        List<Role> roles = roleRepository.findAllById(userRequest.getRoleIds());
        Set<Role> set = new HashSet<>(roles);
        u.setRoles(set);
        u.setEncodePassword(userRequest.getPassword());
        u.setStatus(userRequest.getStatus());
        userRepository.save(u);

        return new ResponseEntity(new ApiResponse(true, "User update successfully"),
                HttpStatus.OK);
    }
}
