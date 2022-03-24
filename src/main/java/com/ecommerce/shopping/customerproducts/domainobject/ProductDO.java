package com.ecommerce.shopping.customerproducts.domainobject;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "PRODUCT")
public class ProductDO {

    @Id
    @Column(updatable = false, nullable = false)
    @Type(type="uuid-char")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerDO customer;

    private String title;

    @Column(length = 1024)
    private String description;

    @Column(precision=10, scale=2)
    private BigDecimal price;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Column(nullable = false, name = "CREATED_AT")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime modifiedAt;

    public ProductDO() {
    }

    public ProductDO(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
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

    public CustomerDO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDO customer) {
        this.customer = customer;
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
}
