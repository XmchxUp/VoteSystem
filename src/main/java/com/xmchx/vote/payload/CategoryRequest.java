package com.xmchx.vote.payload;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/14 18:04
 */
public class CategoryRequest {
    private Long id;
    private String name;
    private Integer rank;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
