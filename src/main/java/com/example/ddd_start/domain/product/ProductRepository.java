package com.example.ddd_start.domain.product;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query(value = "select p from Product p "
      + "join ProductCategory pc on pc.productId = p.id "
      + "where p.categoryId = :categoryId")
  List<Product> findByCategoryId(Long categoryId);
}
