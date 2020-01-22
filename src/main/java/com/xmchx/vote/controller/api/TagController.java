package com.xmchx.vote.controller.api;

import com.xmchx.vote.exception.AppException;
import com.xmchx.vote.model.Tag;
import com.xmchx.vote.payload.ApiResponse;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.repository.TagRepository;
import com.xmchx.vote.service.TagService;
import com.xmchx.vote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/14 20:05
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public PagedResponse<?> list(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sidx", defaultValue = "createTime") String sidx,
            @RequestParam(name = "order", defaultValue = "desc") String order

    ) {
        return tagService.getAllTags(page, size, sidx, order);
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody Tag tagRequest) {
        if (tagRepository.existsByName(tagRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "name is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        Tag t = new Tag();
        t.setName(tagRequest.getName());
        t.setCreateTime(new Date());
        tagRepository.save(t);
        return new ResponseEntity(new ApiResponse(true, "tag save successfully"),
                HttpStatus.OK);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return new ResponseEntity(new ApiResponse(false, "ids is null!"),
                    HttpStatus.BAD_REQUEST);
        }
        tagRepository.deleteTagsByIdIn(Arrays.asList(ids));

        return new ResponseEntity(new ApiResponse(true, "delete tags successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Tag tagRequest) {
        Tag t =
                tagRepository.findById(tagRequest.getId()).orElseThrow(() -> new AppException(
                        "Tag not exist."));
        t.setName(tagRequest.getName());
        tagRepository.save(t);
        return new ResponseEntity(new ApiResponse(true, "Tag update successfully"),
                HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> info(@PathVariable("id") Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new AppException(
                "tag not exist."));
        return new ResponseEntity(tag, HttpStatus.OK);
    }
}
