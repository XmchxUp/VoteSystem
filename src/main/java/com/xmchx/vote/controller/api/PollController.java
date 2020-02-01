package com.xmchx.vote.controller.api;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.*;
import com.xmchx.vote.payload.*;
import com.xmchx.vote.repository.*;
import com.xmchx.vote.service.PollService;
import com.xmchx.vote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/14 17:54
 */
@RestController
@RequestMapping("/api/polls")
public class PollController {
    @Autowired
    PollRepository pollRepository;


    @Autowired
    PollService pollService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/list")
    public PagedResponse<PollResponse> list(@RequestParam(name = "page", defaultValue =
            AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                            @RequestParam(name = "sidx", defaultValue = "createTime") String sidx,
                                            @RequestParam(name = "order", defaultValue = "desc") String order) {
        return pollService.getAllPolls(page, size, sidx, order);
    }

    @GetMapping("/search")
    public PagedResponse<PollResponse> search(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        return pollService.getAllPollsByKeyword(page, size, keyword);
    }

    @GetMapping("/{pollId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PollInfoResponse> info(@PathVariable("pollId") Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new AppException(
                "poll not exist."));
        PollInfoResponse pollInfoResponse = new PollInfoResponse();
        pollInfoResponse.setTitle(poll.getTitle());
        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        List<Choice> choices = choiceRepository.findChoicesByPollIdIn(pollId);
        for (Choice choice : choices) {
            long count = voteRepository.countByChoiceId(choice.getId());
            labels.add(choice.getText());
            values.add((int) count);
        }
        pollInfoResponse.setValues(values);
        pollInfoResponse.setLabels(labels);

        return new ResponseEntity(pollInfoResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> save(@RequestBody PollRequest pollRequest, Principal principal) {
        // save tags
        for (String tagName : pollRequest.getTags()) {
            if (!tagRepository.existsByName(tagName)) {
                Tag t = new Tag();
                t.setName(tagName);
                t.setCreateTime(new Date());
                tagRepository.save(t);
            }
        }
        List<Tag> tags =
                tagRepository.findTagsByNameIn(Arrays.asList(pollRequest.getTags()));

        Category category =
                categoryRepository.findById(pollRequest.getCategoryId()).orElseThrow(() -> new AppException(
                        "Category not exist."));
        category.setRank(category.getRank() + 1);
        categoryRepository.save(category);

        Poll poll = new Poll();
        poll.setCategory(category);
        poll.setTags(tags);
        poll.setCreateTime(new Date());
        poll.setExpirationDateTime(pollRequest.getExpirationDateTime());
        poll.setMode(pollRequest.getMode());
        poll.setLimitCount(pollRequest.getLimitCount());
        poll.setTitle(pollRequest.getTitle());
        poll.setSummary(pollRequest.getSummary());
        poll.setCount(0);

        String name = principal.getName();
        User user = userRepository.findByUsernameOrEmail(name, name).orElseThrow(() -> new AppException(
                "User not exist."));
        poll.setUser(user);
        // save choices
        pollRequest.getChoices().forEach(text -> {
            poll.addChoice(new Choice(text));
        });

        pollRepository.save(poll);

        return new ResponseEntity(new ApiResponse(true, "Poll save successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new ResponseEntity(new ApiResponse(false, "ids is null!"),
                    HttpStatus.BAD_REQUEST);
        }
        pollRepository.deletePollsByIdIn(Arrays.asList(ids));

        return new ResponseEntity(new ApiResponse(true, "delete polls successfully"),
                HttpStatus.OK);
    }


    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody PollRequest pollRequest) {
        // save tags
        for (String tagName : pollRequest.getTags()) {
            if (!tagRepository.existsByName(tagName)) {
                Tag t = new Tag();
                t.setName(tagName);
                t.setCreateTime(new Date());
                tagRepository.save(t);
            }
        }
        List<Tag> tags =
                tagRepository.findTagsByNameIn(Arrays.asList(pollRequest.getTags()));

        Category category =
                categoryRepository.findById(pollRequest.getCategoryId()).orElseThrow(() -> new AppException(
                        "Category not exist."));

        // save
        Poll poll = pollRepository.findById(pollRequest.getId()).orElseThrow(() -> new AppException(
                "poll not exist."));
        poll.setCategory(category);
        poll.setTags(tags);
        poll.setExpirationDateTime(pollRequest.getExpirationDateTime());
        poll.setMode(pollRequest.getMode());
        poll.setLimitCount(pollRequest.getLimitCount());
        poll.setTitle(pollRequest.getTitle());
        poll.setSummary(pollRequest.getSummary());

        // delete old choices
        choiceRepository.deleteChoicesByPollId(pollRequest.getId());
        // save choices
        pollRequest.getChoices().forEach(text -> {
            poll.addChoice(new Choice(text));
        });
        pollRepository.save(poll);
        return new ResponseEntity(new ApiResponse(true, "Poll update successfully"),
                HttpStatus.OK);
    }


}
