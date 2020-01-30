package com.xmchx.vote.payload;

import lombok.Data;

import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/26 10:46
 */
@Data
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private String avatar;
    private String motto;
    private Date joinedAt;
    private Long pollCount;
    private Long voteCount;

    public UserProfile(Long id, String username, String name, Date createTime, Long pollCount,
                       Long voteCount,String avatar) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = createTime;
        this.pollCount = pollCount;
        this.voteCount = voteCount;
        this.avatar = avatar;
    }
}
