package org.ecommerce.productcatalogservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String productName;
    private String productDescription;
    private Double productPrice;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category productCategory;
}
