package br.com.rafael.java_blog.datasources.repositories;

import br.com.rafael.java_blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {



   List<Comment> findByAuthor(String author);
}