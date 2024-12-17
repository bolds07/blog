package br.com.rafael.java_blog;


import br.com.rafael.java_blog.datasources.repositories.BlogPostRepository;
import br.com.rafael.java_blog.model.BlogPost;
import br.com.rafael.java_blog.model.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class IntegrationTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @Autowired
   private BlogPostRepository repository;

   @AfterEach
   void cleanDatabase() {
      repository.deleteAll();
   }

   @Test
   void testGetAllBlogPosts() throws Exception {
      repository.saveAll(List.of(BlogPost.builder().title("Sample Title").content("Sample Content").build(), BlogPost.builder().title("c").content("d").build(), BlogPost.builder().title("e").content("f").build()));

      mockMvc.perform(get("/api/posts")
                              .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.data").isArray())
             .andExpect(jsonPath("$.data", hasSize(3)))
             .andExpect(jsonPath("$.hasMore").exists());
   }

   @Test
   void shoulCreateBlogPost() throws Exception {
      final String title = "Sample title";
      final String content = "Sample Content";
      final BlogPost blogPost = BlogPost.builder().title(title).content(content).build();

      final String blogPostJson = objectMapper.writeValueAsString(blogPost);

      final MvcResult result = mockMvc.perform(post("/api/posts")
                                                       .contentType(MediaType.APPLICATION_JSON)
                                                       .content(blogPostJson))
                                      .andExpect(status().isCreated())
                                      .andExpect(jsonPath("$.title").value(title))
                                      .andExpect(jsonPath("$.content").value(content))
                                      .andReturn();

      final String responseContent = result.getResponse().getContentAsString();
      final Number id = JsonPath.read(responseContent, "$.id");

      mockMvc.perform(get("/api/posts/{id}", id)
                              .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.id").value(id))
             .andExpect(jsonPath("$.title").value(title))
             .andExpect(jsonPath("$.content").value(content));
   }

   @Test
   void shouldThrowNotFoundError() throws Exception {
      final long testId = 1L;

      mockMvc.perform(get("/api/posts/{id}", testId)
                              .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isNotFound());
   }


   @Test
   void shouldGetBlogPost() throws Exception {
      final BlogPost blogPost = repository.save(BlogPost.builder().title("Sample Title").content("Sample Content").build());

      mockMvc.perform(get("/api/posts/{id}", blogPost.getId())
                              .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.id").value(blogPost.getId()))
             .andExpect(jsonPath("$.title").value(blogPost.getTitle()))
             .andExpect(jsonPath("$.content").value(blogPost.getContent()));

   }

   @Test
   void shouldAddCommentToPost() throws Exception {
      final BlogPost blogPost = repository.save(BlogPost.builder().title("Sample Title").content("Sample Content").build());

      final Comment comment = Comment.builder().author("Sample Author").content("sample comment").build();

      final String commentJson = objectMapper.writeValueAsString(comment);

      mockMvc.perform(post("/api/posts/{id}/comments", blogPost.getId())
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(commentJson))
             .andExpect(status().isCreated())
             .andExpect(jsonPath("$.author").value(comment.getAuthor()))
             .andExpect(jsonPath("$.content").value(comment.getContent()));

      mockMvc.perform(get("/api/posts/{id}", blogPost.getId())
                              .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.id").value(blogPost.getId()))
             .andExpect(jsonPath("$.comments", hasSize(1)))
             .andExpect(jsonPath("$.comments[0].author").value(comment.getAuthor()))
             .andExpect(jsonPath("$.comments[0].content").value(comment.getContent()));

      mockMvc.perform(get("/api/posts", blogPost.getId())
                              .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.data", hasSize(1)))
             .andExpect(jsonPath("$.data[0].commentsCount").value(1));
   }
}
