package com.xmchx.vote.model;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 18:47
 */
public class ChoiceVoteCount {
    private Long choiceId;
    private Long voteCount;
    private String choiceName;

    public ChoiceVoteCount(Long choiceId, Long voteCount) {
        this.choiceId = choiceId;
        this.voteCount = voteCount;
    }

    public ChoiceVoteCount(Long choiceId, Long voteCount, String choiceName) {
        this.choiceId = choiceId;
        this.voteCount = voteCount;
        this.choiceName = choiceName;
    }

    public String getChoiceName() {
        return choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
