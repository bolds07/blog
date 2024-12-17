package br.com.rafael.java_blog.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Setter(AccessLevel.NONE)
@MappedSuperclass
@Data
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {

   @Id
   @GeneratedValue
   @JsonView(ViewMode.class)
   private Long id;


   @CreatedDate
   @JsonView(ViewMode.class)
   private Date createdDate;


   @LastModifiedDate
   @JsonView(ViewMode.class)
   private Date lastModifiedDate;

   @CreatedBy
   @JsonView(ViewMode.class)
   private Long createdBy;

   @LastModifiedBy
   @JsonView(ViewMode.class)
   private Long lastModifiedBy;



}
