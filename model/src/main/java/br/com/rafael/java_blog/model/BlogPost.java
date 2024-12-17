package br.com.rafael.java_blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "blog_post",
        indexes = @Index(name = "idx_title", columnList = "title")
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogPost extends BaseEntity {

   @Column(name = "title", nullable = false, length = 255)
   @NotNull(message = "'title' cannot be null")
   @NotBlank(message = "'title' cannot be blank")
   @Size(max = 255, message = "'title' must not exceed 255 characters")
   @JsonProperty("title")
   @JsonView(ViewMode.class)
   private String title;

   @Column(name = "content", nullable = false, length = 10000, columnDefinition = "TEXT")
   @NotNull(message = "'content' cannot be null")
   @NotBlank(message = "'content' cannot be blank")
   @Size(max = 10000, message = "'content' must not exceed 10,000 characters")
   @JsonProperty("content")
   @JsonView(ViewMode.class)
   private String content;

   @OneToMany(mappedBy = "blogPost", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
   @JsonProperty("comments")
   @Builder.Default
   @JsonView(ViewMode.ModeDetails.class)
   private List<Comment> comments = new ArrayList<>();

   @JsonProperty("commentsCount")
   @JsonView(ViewMode.ModeSummary.class)
   public long getTotalComments() {
      return Objects.requireNonNullElse(comments, Collections.emptySet()).size();
   }

}