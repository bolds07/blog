package br.com.rafael.java_blog.datasources.repositories;

import br.com.rafael.java_blog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    BlogPost findByTitle(String title);

    boolean existsByTitle(String title);
}