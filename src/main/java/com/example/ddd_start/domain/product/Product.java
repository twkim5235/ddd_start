package com.example.ddd_start.domain.product;

import com.example.ddd_start.domain.common.Money;
import com.example.ddd_start.domain.store.Store;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price"))
  private Money price;
  private Long categoryId;
  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;

  public Product(String name, Money price, Long categoryId, Store store) {
    this.name = name;
    this.price = new Money(price.getValue());
    this.categoryId = categoryId;
    this.store = store;
  }

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true,
  mappedBy = "product")
  private List<Image> images = new ArrayList<>();

  public void changeImages(List<Image> newImages) {
    images.clear();
    images.addAll(newImages);
  }
}
