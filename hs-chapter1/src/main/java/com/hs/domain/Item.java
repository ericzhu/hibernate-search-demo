package com.hs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Item implements Serializable{

   private static final long serialVersionUID = -7147396673001233348L;

   @Id
   @GeneratedValue
   private Long id;

   @Column
   @Field
   private String name;

   @Column(length = 1000)
   @Field
   private String description;

   @Column
   private String image;

   public Item(String name, String description, String image) {
      super();
      this.name = name;
      this.description = description;
      this.image = image;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }
}
