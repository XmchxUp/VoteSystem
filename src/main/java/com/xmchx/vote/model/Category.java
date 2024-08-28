package com.xmchx.vote.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/13 16:46
 */
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;


    /**
     * 分类的排序值 被使用的越多数值越大
     */
    @Column(name = "`rank`")
    private Integer rank;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Poll> polls = new ArrayList<>();

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", createTime=" + createTime +
                '}';
    }
}
