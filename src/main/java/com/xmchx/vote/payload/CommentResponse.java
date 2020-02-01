package com.xmchx.vote.payload;

import lombok.Data;

import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/9 20:30
 */
@Data
public class CommentResponse {
    private Long id;
    private String content;
    private String avatar;
    private Byte commentStatus;
    private String replyBody;
    private Date createTime;
    private String email;
    private String commentator;
}
