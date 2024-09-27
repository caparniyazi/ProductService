package com.appsdeveloperblog.estore.productservice.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "product_lookup")
@AllArgsConstructor
@NoArgsConstructor
public class ProductLookupEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Data fields
    @Id
    private String productId;
    @Column(unique=true)
    private String title;
}
