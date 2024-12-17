package br.com.rafael.java_blog;


import br.com.rafael.java_blog.datasources.repositories.BlogPostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }
}

