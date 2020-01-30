package com.xmchx.vote.service;

import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.payload.PollResponse;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/18 19:39
 */
public interface PollService {

    PagedResponse<PollResponse> getAllPolls(int page, int size, String sidx, String order);

    PagedResponse<PollResponse> getAllPollsByKeyword(int page, int size, String keyword);
}
