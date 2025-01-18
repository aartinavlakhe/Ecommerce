package org.ecommerce.productcatalogservice.repository;

import org.ecommerce.productcatalogservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByproductCategory_Id(Long categoryId);

    /**
     * Search products by name or description.
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProductsByKeyword(String keyword);
}
