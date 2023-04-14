package org.simelabs.catelog.application.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "product")
public class ProductEntity {
    @Id
    private String id;

    @Column(name = "name")
    private String name;
    private Category category;
    private Brand brand;
    private BigDecimal price;
    private String color;
    private String description;
    private ProductSize size;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Version
    private Integer version;


}


















