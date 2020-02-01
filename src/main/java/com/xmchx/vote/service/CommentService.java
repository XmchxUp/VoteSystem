package com.xmchx.vote.service;

import com.xmchx.vote.payload.CommentResponse;
import com.xmchx.vote.payload.PagedResponse;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/2/1 18:53
 */

public interface CommentService {
    PagedResponse<CommentResponse> getAllComments(int page, int size, String sidx, String order);

    boolean reply(Long commentId, String replyBody);

    boolean checkDone(List<Long> ids);
}
