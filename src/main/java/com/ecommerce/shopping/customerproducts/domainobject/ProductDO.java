package com.ecommerce.shopping.customerproducts.domainobject;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

//@Entity
//@Table(name = "PRODUCT")
public class ProductDO {

    @Id
    private UUID id = UUID.randomUUID();

    private String title;

    private String description;

    private BigDecimal price;

    private Boolean isDeleted;

    private Instant createdAt;

    private Instant modifiedAt;

    public ProductDO() {
    }

    public ProductDO(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.isDeleted = Boolean.FALSE;
        this.createdAt = Instant.now();
        this.modifiedAt = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
