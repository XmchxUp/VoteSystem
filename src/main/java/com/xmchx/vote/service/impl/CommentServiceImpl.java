package com.xmchx.vote.service.impl;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.Comment;
import com.xmchx.vote.payload.CommentResponse;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.repository.CommentRepository;
import com.xmchx.vote.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.xmchx.vote.util.ValidUtil.validatePageNumberAndSize;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/2/1 18:53
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public PagedResponse<CommentResponse> getAllComments(int page, int size, String sidx, String order) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable;
        if ("desc".equals(order)) {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sidx);
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, sidx);
        }

        Page<Comment> comments = commentRepository.findAll(pageable);

        if (comments.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), comments.getNumber(),
                    comments.getSize(), comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
        }

        return new PagedResponse(comments.getContent(), comments.getNumber(),
                comments.getSize(), comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
    }

    @Override
    public boolean reply(Long commentId, String replyBody) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new AppException(
                "Comment not exist."));
        comment.setReplyBody(replyBody);
        comment.setReplyCreateTime(new Date());
        commentRepository.save(comment);
        return true;
    }

    @Override
    public boolean checkDone(List<Long> ids) {
        commentRepository.updateCommentStatus(ids);
        return true;
    }
}
