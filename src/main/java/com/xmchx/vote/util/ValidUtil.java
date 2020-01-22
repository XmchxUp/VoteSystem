package com.xmchx.vote.util;

import com.xmchx.vote.exception.BadRequestException;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/14 19:14
 */
public class ValidUtil {
    /**
     * 验证 page 和 size 的合法性
     *
     * @param page
     * @param size
     * @return void
     */
    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
