package com.ecommerce.shopping.customerproducts.domainobject;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Customers")
public class CustomerDO {
    @Id
    @Type(type="uuid-char")
    private UUID id = UUID.randomUUID();

    @Column(unique = true)
    private String title;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Column(nullable = false, name = "CREATED_AT")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime modifiedAt;

    @OneToMany(mappedBy="customer")
    private Set<ProductDO> products;

    public CustomerDO() {
    }

    public CustomerDO(String title) {
        this.title = title;
        this.isDeleted = Boolean.FALSE;
        this.createdAt = ZonedDateTime.now();
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<ProductDO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDO> products) {
        this.products = products;
    }
}
