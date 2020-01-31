package com.xmchx.vote.service.impl;

import com.xmchx.vote.model.Poll;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.payload.PollResponse;
import com.xmchx.vote.repository.PollRepository;
import com.xmchx.vote.service.PollService;
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
 * @date 2020/1/18 19:51
 */
@Service
public class PollServiceImpl implements PollService {
    @Autowired
    PollRepository pollRepository;


    @Override
    public PagedResponse<PollResponse> getAllPolls(int page, int size, String sidx, String order) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable;
        if ("desc".equals(order)) {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sidx);
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, sidx);
        }

        Page<Poll> polls = pollRepository.findAll(pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        return new PagedResponse(polls.getContent(), polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollResponse> getAllPollsByKeyword(int page, int size, String keyword) {
        validatePageNumberAndSize(page - 1, size);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createTime");
        String k = "%" + keyword + "%";
        Page<Poll> polls = pollRepository.findByTitleLikeOrSummaryLike(k,
                pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        return new PagedResponse(polls.getContent(), polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollResponse> getAllPollsByTagName(int page, int size, String tagName) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "create_time");

        Page<Poll> polls = pollRepository.findPollsByTagName(tagName, pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        return new PagedResponse(polls.getContent(), polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollResponse> getAllPollsByCategoryName(int page, int size, String categoryName) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createTime");

        Page<Poll> polls = pollRepository.findPollsByCategoryName(categoryName, pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        return new PagedResponse(polls.getContent(), polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }
}
