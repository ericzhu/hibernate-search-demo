package com.hs.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

@Entity
public class Category implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long              id;

   @Column
   @Field
   private String            manufacturer;

   @Column
   @Field
   private String            name;

   @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
   @ContainedIn
   private Set<Item>         items;

   public Category() {}

   public Category(String manufacturer, String name, Set<Item> items) {
      this.manufacturer = manufacturer;
      this.name = name;
      this.items = items;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getManufacturer() {
      return manufacturer;
   }

   public void setManufacturer(String manufacturer) {
      this.manufacturer = manufacturer;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Set<Item> getItems() {
      return items;
   }

   public void setItems(Set<Item> items) {
      this.items = items;
   }
}
