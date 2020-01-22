package com.xmchx.vote.service;

import com.xmchx.vote.payload.PagedResponse;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/14 19:11
 */
public interface TagService {

    PagedResponse<?> getAllTags(int page, int size, String sidx, String order);
}
