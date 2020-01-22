package com.xmchx.vote.service;

import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.payload.UserResponse;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/8 19:19
 */
public interface UserService {
    PagedResponse<UserResponse> getAllUsers(int page, int size,String sidx,String order);

    PagedResponse<UserResponse> getAllUsersByKeyword(int page, int size, String keyword);
}
