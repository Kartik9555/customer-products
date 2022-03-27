package com.ecommerce.shopping.customerproducts.service;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDO findCustomer(UUID customerId) throws EntityNotFoundException;

    CustomerDO createCustomer(CustomerDO customerDO) throws ConstraintsViolationException;

    void deleteCustomer(UUID customerId) throws EntityNotFoundException;

    List<CustomerDTO> findAllCustomers(Integer pageSize);

    void updateCustomer(UUID customerId, CustomerDO customerDO) throws EntityNotFoundException;

    ProductDO findProduct(UUID productId) throws EntityNotFoundException;

    ProductDO createProduct(UUID customerId, ProductDO productDO) throws ConstraintsViolationException, EntityNotFoundException;

    void deleteProduct(UUID productId) throws EntityNotFoundException;

    List<ProductDTO> findAllProducts(UUID customerId, Integer pageSize) throws EntityNotFoundException;

    void updateProduct(UUID productId, ProductDO productDO) throws EntityNotFoundException;
}
