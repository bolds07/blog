package br.com.rafael.java_blog.configuration;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class DebugConfiguration {

   @Bean(name = {"debug"})
   public boolean getDebugOption(@Autowired @NonNull final Environment environment) {
      return Arrays.stream(environment.getActiveProfiles()).anyMatch(a -> a.equals("test") || a.equals("development") || a.equals("dev") || a.equals("local"));
   }
}
