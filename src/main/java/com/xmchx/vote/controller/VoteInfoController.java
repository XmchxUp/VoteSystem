package com.xmchx.vote.controller;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.*;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.repository.*;
import com.xmchx.vote.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/29 17:21
 */
@Controller
public class VoteInfoController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/votes")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<ApiResponse> castVote(Principal principal,
                                                @RequestParam(name = "voteId") Long pollId,
                                                @RequestParam(name = "optionIds[]") Long[] choiceIds) {
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


    @GetMapping("/vote/{voteId}")
    @PreAuthorize("hasRole('USER')")
    public String voteInfo(@PathVariable("voteId") Long pollId, Model model) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new AppException("poll not exist."));
        model.addAttribute("poll", poll);
        return "voteinfo";
    }

    @PostMapping("/post/comment")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<ApiResponse> postComment(Principal principal,
                                                   @RequestParam(name = "content") String content,
                                                   @RequestParam(name = "pollId") Long pollId,
                                                   HttpServletRequest request) {
        String loginName = principal.getName();
        User user = userRepository.findByUsernameOrEmail(loginName, loginName).orElseThrow(() -> new AppException(
                "user not exist."));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new AppException("poll not exist."));
        Comment c = new Comment();
        c.setPoll(poll);
        c.setCommentStatus((byte) 0);
        c.setContent(content);
        c.setCommentator(user.getName());
        c.setEmail(user.getEmail());
        c.setCreateTime(new Date());
        c.setAvatar(user.getAvatar());
        //获取IP地址
        String ipAddress = IpUtil.getIpAddr(request);
        c.setCommentatorIp(ipAddress);
        commentRepository.save(c);
        return new ResponseEntity(new ApiResponse(true, "post successfully"),
                HttpStatus.OK);
    }
}
