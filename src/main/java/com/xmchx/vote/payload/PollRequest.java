package com.xmchx.vote.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/15 19:05
 */
public class PollRequest {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 140)
    private String summary;

    @NotNull
    private Byte Mode;

    private Integer LimitCount = 1;

    @NotNull
    private Date expirationDateTime;

    @NotNull
    private Long categoryId;

    @NotNull
    @Size(min = 2)
    private List<String> choices = new ArrayList<>();

    private String[] tags;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Byte getMode() {
        return Mode;
    }

    public void setMode(Byte mode) {
        Mode = mode;
    }

    public Integer getLimitCount() {
        return LimitCount;
    }

    public void setLimitCount(Integer limitCount) {
        LimitCount = limitCount;
    }

    public Date getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Date expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
