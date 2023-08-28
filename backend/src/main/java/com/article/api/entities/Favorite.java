package com.article.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "favorite")
@Where(clause = "is_deleted = false")
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  @CreationTimestamp
  private Date createdAt;

  @UpdateTimestamp
  private Date updateAt;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted;
}
