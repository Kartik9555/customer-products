package com.ecommerce.shopping.customerproducts.service;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDO find(UUID customerId) throws EntityNotFoundException;

    CustomerDO create(CustomerDO customerDO) throws ConstraintsViolationException;

    void delete(UUID customerId) throws EntityNotFoundException;

    List<CustomerDTO> findAllCustomers(Integer pageSize);

    void updateCustomer(UUID customerId, CustomerDO customerDO) throws EntityNotFoundException;
}
