package com.xmchx.vote.service.impl;

import com.xmchx.vote.model.Tag;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.repository.TagRepository;
import com.xmchx.vote.service.TagService;
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
 * @date 2020/1/14 20:06
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public PagedResponse<?> getAllTags(int page, int size, String sidx, String order) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable;
        if ("desc".equals(order)) {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sidx);
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, sidx);
        }

        Page<Tag> tags = tagRepository.findAll(pageable);

        if (tags.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), tags.getNumber(),
                    tags.getSize(), tags.getTotalElements(), tags.getTotalPages(), tags.isLast());
        }

        return new PagedResponse(tags.getContent(), tags.getNumber(),
                tags.getSize(), tags.getTotalElements(), tags.getTotalPages(), tags.isLast());
    }
}
