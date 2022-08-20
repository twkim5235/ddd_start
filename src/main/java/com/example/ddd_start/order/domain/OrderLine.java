package com.example.ddd_start.order.domain;

import com.example.ddd_start.common.domain.Money;
import com.example.ddd_start.product.domain.Product;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long product_id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orders_id")
  private Orders orders;
  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "amount"))
  private Money amount;
  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "price"))
  private Money price;
  private Integer quantity;

  public OrderLine(Product product, Orders orders, Integer quantity) {
    changeProduct(product);
    changeOrder(orders);
    this.price = product.getPrice();
    this.amount = calculateAmount();
    this.quantity = quantity;
  }

  private Money calculateAmount() {
    return this.amount.multiply(quantity);
  }

  private void changeOrder(Orders orders) {
    this.orders = orders;
    orders.getOrderLines().add(this);
  }

  private void changeProduct(Product product) {
    this.product_id = product.getId();
  }
}