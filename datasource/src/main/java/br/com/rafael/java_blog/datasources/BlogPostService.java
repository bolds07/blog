package br.com.rafael.java_blog.datasources;

import br.com.rafael.java_blog.datasources.repositories.BlogPostRepository;
import br.com.rafael.java_blog.datasources.repositories.CommentRepository;
import br.com.rafael.java_blog.model.BlogPost;
import br.com.rafael.java_blog.model.Comment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Validator;

@RequiredArgsConstructor
@Service
public class BlogPostService {

   private final BlogPostRepository blogPostRepository;
   private final CommentRepository commentRepository;
   private final Validator validator;

   public Page<BlogPost> getAllBlogPosts(@NonNull final Pageable page) {
      return blogPostRepository.findAll(page);
   }


   public BlogPost createBlogPost(@NonNull final BlogPost blogPost) {
      validator.validate(blogPost).stream().findFirst().ifPresent(blogPostConstraintViolation -> {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, blogPostConstraintViolation.getMessage());
      });
      return blogPostRepository.save(blogPost);
   }

   public BlogPost getBlogPostById(final long id) {

      return blogPostRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found post with given id"));
   }

   @Transactional
   public Comment addCommentToPost(final long id, @NonNull final Comment comment) {
      validator.validate(comment).stream().findFirst().ifPresent(commentConstraintViolation -> {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, commentConstraintViolation.getMessage());
      });

      comment.setBlogPost(getBlogPostById(id));

      return commentRepository.save(comment);
   }

}
