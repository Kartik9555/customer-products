package com.ecommerce.shopping.customerproducts.service.impl;

import com.ecommerce.shopping.customerproducts.dataaccessobject.CustomerRepository;
import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.mapper.CustomerMapper;
import com.ecommerce.shopping.customerproducts.mapper.ProductMapper;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some customer specific things.
 * <p/>
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Find customer by customerId.
     *
     * @param customerId of the customer
     */
    @Override
    public CustomerDO findCustomer(UUID customerId) throws EntityNotFoundException {
        return findCustomerById(customerId);
    }

    /**
     * Creates a new customer.
     *
     * @param customerDO customer object
     * @return Customer with updated id
     * @throws ConstraintsViolationException if a customer already exists with the given title, ... .
     */
    @Override
    @Transactional
    public CustomerDO createCustomer(CustomerDO customerDO) throws ConstraintsViolationException {
        CustomerDO customer;
        try {
            customer = customerRepository.save(customerDO);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return customer;
    }

    /**
     * Deletes an existing customer by id.
     *
     * @param customerId of the customer
     * @throws EntityNotFoundException if no customer with the given id was found.
     */
    @Override
    @Transactional
    public void deleteCustomer(UUID customerId) throws EntityNotFoundException {
        CustomerDO customerDO = findCustomerById(customerId);
        customerDO.setIsDeleted(true);
        customerRepository.save(customerDO);
    }

    /**
     * Find all customers.
     *
     * @param pageSize of the records to return
     */
    @Override
    public List<CustomerDTO> findAllCustomers(Integer pageSize) {
        List<CustomerDTO> customer = new ArrayList<>();
        for (CustomerDO customerDO : customerRepository.findAll(Pageable.ofSize(pageSize))) {
            customer.add(CustomerMapper.makeCustomerDTO(customerDO));
        }
        return customer;
    }


    /**
     * Update the title for a customer.
     *
     * @param customerId of the customer
     * @param customer   updated details
     * @throws EntityNotFoundException id no customer is found
     */
    @Override
    @Transactional
    public void updateCustomer(UUID customerId, CustomerDO customer) throws EntityNotFoundException {
        CustomerDO customerDO = findCustomerById(customerId);
        customerDO.setTitle(customer.getTitle());
        customerDO.setModifiedAt(Instant.now());
        customerRepository.save(customerDO);
    }

    /**
     * Find product by productId.
     *
     * @param productId of the product
     */
    @Override
    public ProductDO findProduct(UUID productId) throws EntityNotFoundException {
        CustomerDO customer = findCustomerByProductId(productId);
        List<ProductDO> products = customer.getProducts().stream().filter(product -> product.getId().compareTo(productId) == 0).collect(Collectors.toList());
        return products.get(0);
    }

    /**
     * Creates a new product for a customer.
     *
     * @param customerId of the customer
     * @param productDO details of the product
     * @return new created product
     * @throws EntityNotFoundException if a customer does not exist .
     */
    @Transactional
    @Override
    public ProductDO createProduct(UUID customerId, ProductDO productDO) throws ConstraintsViolationException, EntityNotFoundException {
        CustomerDO customer = findCustomerById(customerId);
        try {
            if (CollectionUtils.isEmpty(customer.getProducts())) {
                customer.setProducts(Collections.singleton(productDO));
            } else {
                customer.getProducts().add(productDO);
            }
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return productDO;
    }

    /**
     * Deletes an existing product by id.
     *
     * @param productId of the product
     * @throws EntityNotFoundException if no product with the given id was found.
     */
    @Override
    @Transactional
    public void deleteProduct(UUID productId) throws EntityNotFoundException {
        CustomerDO customer = findCustomerByProductId(productId);
        List<ProductDO> products = customer.getProducts().stream().filter(productDO -> productDO.getId().compareTo(productId) == 0).collect(Collectors.toList());
        products.get(0).setIsDeleted(true);
        customerRepository.save(customer);
    }

    /**
     * Find all products for a customer.
     *
     * @param customerId of the customer
     * @param pageSize of the no of records to return
     * @throws EntityNotFoundException if no customer with the given id was found.
     */
    @Override
    public List<ProductDTO> findAllProducts(UUID customerId, Integer pageSize) throws EntityNotFoundException {
        CustomerDO customer = findCustomerById(customerId);
        if (CollectionUtils.isEmpty(customer.getProducts())) {
            return new ArrayList<>();
        }
        List<ProductDO> products = customer.getProducts().stream().limit(pageSize).collect(Collectors.toList());
        return ProductMapper.makeProductDTOList(products);
    }

    /**
     * Update the details of the product.
     *
     * @param productId of the product
     * @param productDO product details to update
     * @throws EntityNotFoundException if no product with the given id was found.
     */
    @Transactional
    @Override
    public void updateProduct(UUID productId, ProductDO productDO) throws EntityNotFoundException {
        CustomerDO customer = findCustomerByProductId(productId);
        List<ProductDO> products = customer.getProducts().stream().filter(product -> product.getId().compareTo(productId) == 0).collect(Collectors.toList());
        ProductDO existing = products.get(0);
        existing.setModifiedAt(Instant.now());
        existing.setPrice(productDO.getPrice());
        existing.setDescription(productDO.getDescription());
        existing.setTitle(productDO.getTitle());
        customerRepository.save(customer);
    }

    private CustomerDO findCustomerById(UUID customerId) throws EntityNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + customerId));
    }

    private CustomerDO findCustomerByProductId(UUID productId) throws EntityNotFoundException {
        return customerRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + productId));
    }
}

