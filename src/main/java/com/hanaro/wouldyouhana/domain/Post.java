package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String communityName;
    @ManyToOne // 다대일 관계 설정
    @JoinColumn(name = "category_id", nullable = false) // 외래 키 설정
    private Category category; // Category 객체 추가

    private String title;

    @Column(name="customer_id")
    private Long customerId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private Long likeCount = 0L;
    @Builder.Default
    private Long scrapCount = 0L;
    @Builder.Default
    private Long viewCount = 0L;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환 참조 방지
    private List<Comment> comments;

    private List<String> filePaths;

    // 조회수 증가
    public void incrementViewCount() {
        this.viewCount++;
    }

    // 좋아요 수 증가
    public void incrementLikesCount() {
        this.likeCount++;
    }
    // 좋아요 수 감소
    public void decrementLikesCount() {
        this.likeCount--;
    }

    // 스크랩 수 증가
    public void incrementScrapCount() {
        this.scrapCount++;
    }
    // 스크랩 수 감소
    public void decrementScrapCount() {
        this.scrapCount--;
    }

    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComments(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

}
