package com.xmchx.vote.controller;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.Choice;
import com.xmchx.vote.model.Poll;
import com.xmchx.vote.model.User;
import com.xmchx.vote.model.Vote;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.repository.*;
import com.xmchx.vote.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/21 20:04
 */
@Controller
public class ToolController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PollService pollService;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @GetMapping("/isVote")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<ApiResponse> isVote(Principal principal,
                                                @RequestParam(value = "voteId") Long pollId) {
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


    @PostMapping("/votes")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<ApiResponse> castVote(Principal principal,
                                                @RequestParam(value = "voteId") Long pollId,
                                                @RequestParam(value = "optionIds[]") Long[] choiceIds) {
        String loginName = principal.getName();
        User user = userRepository.findByUsernameOrEmail(loginName, loginName).orElseThrow(() -> new AppException(
                "user not exist."));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new AppException(
                "poll not exist."));

        if (voteRepository.countByUserIdAndPollId(user.getId(), poll.getId()) > 0) {
            return new ResponseEntity(new ApiResponse(true, "已经参与过"),
                    HttpStatus.OK);
        }
        // save more votes
        for (Long choiceId : choiceIds) {
            Choice choice = choiceRepository.findById(choiceId).orElseThrow(() -> new AppException(
                    "choice not exist."));
            Vote vote = new Vote();
            vote.setChoice(choice);
            vote.setCreateTime(new Date());
            vote.setUser(user);
            vote.setPoll(poll);
            voteRepository.save(vote);
        }
        poll.setCount(poll.getCount() + 1);
        pollRepository.save(poll);
        return new ResponseEntity(new ApiResponse(true, "vote successfully"),
                HttpStatus.OK);
    }

}
