package com.xmchx.vote.service.impl;

import com.xmchx.vote.model.Category;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.repository.CategoryRepository;
import com.xmchx.vote.service.CategoryService;
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
 * @date 2020/1/14 19:12
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public PagedResponse<?> getAllCategories(int page, int size, String sidx, String order) {
        validatePageNumberAndSize(page - 1, size);
        Pageable pageable;
        if ("desc".equals(order)) {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sidx);
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, sidx);
        }

        Page<Category> categories = categoryRepository.findAll(pageable);

        if (categories.getNumberOfElements() == 0) {
            return new PagedResponse(Collections.emptyList(), categories.getNumber(),
                    categories.getSize(), categories.getTotalElements(), categories.getTotalPages(), categories.isLast());
        }

        return new PagedResponse(categories.getContent(), categories.getNumber(),
                categories.getSize(), categories.getTotalElements(), categories.getTotalPages(), categories.isLast());
    }


}
