package com.xmchx.vote.service.impl;

import com.xmchx.vote.exception.ResourceNotFoundException;
import com.xmchx.vote.model.User;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.payload.UserProfile;
import com.xmchx.vote.payload.UserResponse;
import com.xmchx.vote.repository.PollRepository;
import com.xmchx.vote.repository.RoleRepository;
import com.xmchx.vote.repository.UserRepository;
import com.xmchx.vote.repository.VoteRepository;
import com.xmchx.vote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.xmchx.vote.util.ValidUtil.validatePageNumberAndSize;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/9 20:48
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    VoteRepository voteRepository;

    @Override
    public PagedResponse<UserResponse> getAllUsers(int page, int size, String sidx, String order) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable;
        if ("desc".equals(order)) {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sidx);
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, sidx);
        }

        Page<User> users = userRepository.findAll(pageable);

        if (users.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), users.getNumber(),
                    users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
        }

        return new PagedResponse(users.getContent(), users.getNumber(),
                users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
    }

    @Override
    public PagedResponse<UserResponse> getAllUsersByKeyword(int page, int size, String keyword) {
        validatePageNumberAndSize(page - 1, size);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createTime");
        String k = "%" + keyword + "%";
        Page<User> users = userRepository.findUsersByUsernameLikeOrNameLikeOrEmailLike(k,
                pageable);

        if (users.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), users.getNumber(),
                    users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
        }

        return new PagedResponse(users.getContent(), users.getNumber(),
                users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
    }

    @Override
    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
                "User", "username", username));
        long pollCount = pollRepository.countByUserId(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(),
                user.getCreateTime(), pollCount, voteCount, user.getAvatar());
        userProfile.setMotto(user.getMotto());
        return userProfile;
    }

    @Override
    public UserResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
                "User", "username", username));
        UserResponse userResponse = new UserResponse();
        userResponse.setAvatar(user.getAvatar());
        userResponse.setCreateTime(user.getCreateTime());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setUsername(user.getUsername());
        userResponse.setMotto(user.getMotto());
        return userResponse;
    }

}
