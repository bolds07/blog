package br.com.rafael.java_blog.controllers;

import br.com.rafael.java_blog.datasources.BlogPostService;
import br.com.rafael.java_blog.model.BlogPost;
import br.com.rafael.java_blog.model.Comment;
import br.com.rafael.java_blog.model.ViewMode;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BlogPostController {

   private final BlogPostService service;

   @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
   @JsonView(ViewMode.ModeSummary.class)
   public ResponseEntity<BasicResponse<Collection<BlogPost>>> getAllBlogPosts(@NonNull final Pageable page) {
      final Page<BlogPost> result = service.getAllBlogPosts(page);
      return ResponseEntity.ok(new BasicResponse<>(result.hasNext(), result.getContent()));
   }

   @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<BlogPost> createBlogPost(@RequestBody final BlogPost blogPost) {
      return ResponseEntity.status(HttpStatus.CREATED).body(service.createBlogPost(blogPost));
   }

   @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   @JsonView(ViewMode.ModeDetails.class)
   public ResponseEntity<BlogPost> getBlogPostById(@PathVariable(required = true) final long id) {
      return ResponseEntity.ok(service.getBlogPostById(id));
   }

   @PostMapping(path = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Comment> addCommentToPost(@PathVariable final Long id, @RequestBody final Comment comment) {
      return ResponseEntity.status(HttpStatus.CREATED).body(service.addCommentToPost(id, comment));
   }

   @Data
   @RequiredArgsConstructor
   @Builder
   @JsonView({ViewMode.ModeSummary.class, ViewMode.ModeDetails.class})
   private static final class BasicResponse<E> {
      private final boolean hasMore;
      private final E data;
   }
}