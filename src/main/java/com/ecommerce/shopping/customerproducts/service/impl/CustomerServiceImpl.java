package com.ecommerce.shopping.customerproducts.service.impl;

import com.ecommerce.shopping.customerproducts.dataaccessobject.CustomerRepository;
import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.mapper.CustomerMapper;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public CustomerDO find(UUID customerId) throws EntityNotFoundException {
        return findCustomer(customerId);
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
    public CustomerDO create(CustomerDO customerDO) throws ConstraintsViolationException {
        CustomerDO customer;
        try {
            customer = customerRepository.save(customerDO);
        } catch (DataIntegrityViolationException e){
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
    public void delete(UUID customerId) throws EntityNotFoundException {
        CustomerDO customerDO = findCustomer(customerId);
        customerDO.setIsDeleted(true);
    }

    /**
     * Find all customers.
     *
     * @param pageSize of the records to return
     */
    @Override
    public List<CustomerDTO> findAllCustomers(Integer pageSize){
        List<CustomerDTO> customer = new ArrayList<>();
        for(CustomerDO customerDO: customerRepository.findAll(Pageable.ofSize(pageSize))){
            customer.add(CustomerMapper.makeCustomerDTO(customerDO));
        }
        return customer;
    }


    /**
     * Update the title for a customer.
     *
     * @param customerId of the customer
     * @param customer updated details
     * @throws EntityNotFoundException id no customer is found
     */
    @Override
    @Transactional
    public void updateCustomer(UUID customerId, CustomerDO customer) throws EntityNotFoundException {
        CustomerDO customerDO = findCustomer(customerId);
        customerDO.setTitle(customer.getTitle());
        customerDO.setModifiedAt(ZonedDateTime.now());
    }

    private CustomerDO findCustomer(UUID customerId) throws EntityNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + customerId));
    }
}

