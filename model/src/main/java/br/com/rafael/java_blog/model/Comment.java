package br.com.rafael.java_blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "comment", indexes = {@Index(name = "idx_author", columnList = "author")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseEntity {

   @Column(name = "author", nullable = false, length = 255)
   @NotNull(message = "'author' cannot be null")
   @NotBlank(message = "'author' be blank")
   @Size(max = 255, message = "'author' must not exceed 255 characters")
   @JsonProperty("author")
   @JsonView(ViewMode.class)
   private String author;

   @Column(name = "content", nullable = false, length = 5000, columnDefinition = "TEXT")
   @NotNull(message = "'content' cannot be null")
   @NotBlank(message = "'content' cannot be blank")
   @Size(max = 5000, message = "'content' must not exceed 5000 characters")
   @JsonProperty("content")
   @JsonView(ViewMode.class)
   private String content;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "blog_post_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_comment_blog_post"))
   @JsonIgnore
   @Setter
   private BlogPost blogPost;

   @JsonProperty("blogPostId")
   public Long getBlogPostId() {
      return Objects.requireNonNullElse(blogPost, new BlogPost()).getId();
   }

}