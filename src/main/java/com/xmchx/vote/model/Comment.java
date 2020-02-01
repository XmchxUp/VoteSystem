package com.xmchx.vote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 16:47
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String commentator;

    @Size(max = 40)
    private String commentatorIp;


    @Email
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String content;

    @Size(max = 200)
    private String avatar;

    private Date createTime;

    /**
     * 是否审核通过 0-未审核 1-审核通过
     */
    private Byte commentStatus;

    private String replyBody;

    private Date replyCreateTime;


    @ManyToOne
    @JsonIgnore
    private Poll poll;


    public String getCommentatorIp() {
        return commentatorIp;
    }

    public void setCommentatorIp(String commentatorIp) {
        this.commentatorIp = commentatorIp;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReplyBody() {
        return replyBody;
    }

    public void setReplyBody(String replyBody) {
        this.replyBody = replyBody;
    }

    public Date getReplyCreateTime() {
        return replyCreateTime;
    }

    public void setReplyCreateTime(Date replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String nickname) {
        this.commentator = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }


}
