package com.xmchx.vote.controller.api;

import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.payload.CommentResponse;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.repository.CommentRepository;
import com.xmchx.vote.service.CommentService;
import com.xmchx.vote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/7 20:03
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @GetMapping("/list")
    public PagedResponse<CommentResponse> list(
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sidx", defaultValue = "createTime") String sidx,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return commentService.getAllComments(page, size, sidx, order);
    }



    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new ResponseEntity(new ApiResponse(false, "ids is null!"),
                    HttpStatus.BAD_REQUEST);
        }
        commentRepository.deleteCommentsByIdIn(Arrays.asList(ids));

        return new ResponseEntity(new ApiResponse(true, "delete comments successfully"),
                HttpStatus.OK);
    }
}
