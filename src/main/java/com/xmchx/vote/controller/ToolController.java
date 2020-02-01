package com.xmchx.vote.controller;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.Poll;
import com.xmchx.vote.model.User;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.repository.PollRepository;
import com.xmchx.vote.repository.UserRepository;
import com.xmchx.vote.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/21 20:04
 */
@Controller
public class ToolController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;


    @GetMapping("/isVote")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<ApiResponse> isVote(Principal principal,
                                                @RequestParam(name = "voteId") Long pollId) {
        String loginName = principal.getName();
        User user = userRepository.findByUsernameOrEmail(loginName, loginName).orElseThrow(() -> new AppException(
                "user not exist."));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new AppException(
                "poll not exist."));

        if (voteRepository.countByUserIdAndPollId(user.getId(), poll.getId()) > 0) {
            return new ResponseEntity(new ApiResponse(true, "true"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponse(true, "false"),
                    HttpStatus.OK);
        }
    }



}
