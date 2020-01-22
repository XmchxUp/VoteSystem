package com.xmchx.vote.payload;

import com.xmchx.vote.model.Choice;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/18 19:41
 */
@Getter
public class PollResponse {
    private Long id;
    private String summary;
    private String title;
    private Byte mode;
    private Integer limitCount;
    private Date expirationDateTime;
    private Date createTime;
    private List<Choice> choices;
    private Integer count;

}
