package com.ecommerce.shopping.customerproducts.domainobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Document(collection = "CUSTOMER")
public class CustomerDO {
    @Id
    private UUID id = UUID.randomUUID();

    private String title;

    private Boolean isDeleted;

    private Instant createdAt;

    private Instant modifiedAt;

    private Set<ProductDO> products;

    public CustomerDO() {
    }

    public CustomerDO(String title) {
        this.title = title;
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

    public Set<ProductDO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDO> products) {
        this.products = products;
    }
}
