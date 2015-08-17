package com.hs.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
public class Item implements Serializable {

   private static final long   serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long                id;

   @Column
   @Field
   private String              name;

   @Column(length = 1000)
   @Field
   private String              description;

   @Column
   private String              image;

   @Column
   private boolean             active;

   @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
   @IndexedEmbedded(depth = 1)
   private Set<Category>       categories;

   @ElementCollection(fetch = FetchType.EAGER)
   @Fetch(FetchMode.SELECT)
   @IndexedEmbedded(depth = 1, includePaths = { "comments" })
   private Set<CustomerReview> customerReviews;

   public Item() {}

   public Item(String name, String image, String description) {
      this.name = name;
      this.image = image;
      this.description = description;
      this.active = true;
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

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public Set<Category> getCategories() {
      return categories;
   }

   public void setCategories(Set<Category> categories) {
      this.categories = categories;
   }

   public Set<CustomerReview> getCustomerReviews() {
      return customerReviews;
   }

   public void setCustomerReviews(Set<CustomerReview> customerReviews) {
      this.customerReviews = customerReviews;
   }
}
