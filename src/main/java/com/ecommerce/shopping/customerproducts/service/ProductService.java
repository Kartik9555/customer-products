package com.ecommerce.shopping.customerproducts.service;

import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDO find(UUID productId) throws EntityNotFoundException;

    ProductDO create(UUID customerId, ProductDO productDO) throws ConstraintsViolationException, EntityNotFoundException;

    void delete(UUID productId) throws EntityNotFoundException;

    List<ProductDTO> findAllProducts(UUID customerId, Integer pageSize) throws EntityNotFoundException;

    void updateProduct(UUID productId, ProductDO productDO) throws EntityNotFoundException;
}
