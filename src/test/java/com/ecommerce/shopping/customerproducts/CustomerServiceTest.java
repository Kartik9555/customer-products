package com.ecommerce.shopping.customerproducts;

import com.ecommerce.shopping.customerproducts.dataaccessobject.CustomerRepository;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testFindCustomerById() throws EntityNotFoundException {
        CustomerDO customerDO = new CustomerDO();
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerDO));
        assertEquals(customerDO, customerService.find(customerId));
    }

    @Test
    void testCreateCustomerOK() throws Exception {
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.save(customerDO)).thenReturn(customerDO);
        assertEquals(customerDO, customerService.create(customerDO));
    }

    @Test
    void testCreateCustomerKO() {
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.save(customerDO)).thenThrow(new DataIntegrityViolationException("Test Message"));
        assertThrows(ConstraintsViolationException.class, () -> customerService.create(customerDO));
    }

    @Test
    void testDeleteCustomerOK() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerDO));
        customerService.delete(customerId);
        assertNotNull(customerId);
    }

    @Test
    void testDeleteCustomerKO() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenThrow(new DataIntegrityViolationException("Test Message"));
        assertThrows(DataIntegrityViolationException.class, () -> customerService.delete(customerId));
    }

    @Test
    void testFindAllCustomerOK() {
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(Page.empty());
        assertNotNull(customerService.findAllCustomers(20));
    }

    @Test
    void testFindAllCustomerKO() {
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenThrow(new NullPointerException("TEST"));
        assertThrows(NullPointerException.class, () -> customerService.findAllCustomers(20));
    }

    @Test
    void testUpdateCustomerOK() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerDO));
        customerService.updateCustomer(customerId, customerDO);
        assertNotNull(customerId);
    }

    @Test
    void testUpdateCustomerKO() {
        UUID customerId = UUID.randomUUID();
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.findById(customerId)).thenThrow(new NullPointerException("TEST"));
        assertThrows(NullPointerException.class, () -> customerService.updateCustomer(customerId, customerDO));
    }
}
