package com.appsdeveloperblog.estore.productservice.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class ProductEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @Column(unique = true, nullable = false)
    private  String productId;

    @Column(unique = true, nullable = false)
    private  String title;
    private BigDecimal price;
    private  int quantity;
}
