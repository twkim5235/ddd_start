package com.example.ddd_start.store.domain;

import static com.example.ddd_start.store.domain.StoreStatus.CLOSED;

import com.example.ddd_start.common.domain.Money;
import com.example.ddd_start.common.domain.exception.StoreBlockedException;
import com.example.ddd_start.product.domain.Product;
import com.example.ddd_start.product.domain.ProductInfo;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store {

  @Id
  @GeneratedValue
  private Long id;
  private String storeName;
  @OneToMany(mappedBy = "store")
  private Set<Product> products = new HashSet<>();

  @Enumerated(value = EnumType.STRING)
  private StoreStatus storeStatus;

  public Store(String storeName, StoreStatus storeStatus) {
    this.storeName = storeName;
    this.storeStatus = storeStatus;
  }

  public Product createProduct(ProductInfo productInfo) {
    if (isStoreBlocked()) {
      throw new StoreBlockedException();
    }
    Product product = new Product(productInfo.getName(), new Money(productInfo.getPrice()),
        productInfo.getCategoryId(), this);
    products.add(product);
    return product;
  }

  private boolean isStoreBlocked() {
    return this.storeStatus == CLOSED;
  }
}
