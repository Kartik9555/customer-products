package com.ecommerce.shopping.customerproducts.controller;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private CustomerController controller;

    @MockBean
    private CustomerService customerService;

    @Test
    void testFindAll() {
        when(customerService.findAllCustomers(20)).thenReturn(new ArrayList<>());
        assertNotNull(controller.findAll(20));
    }

    @Test
    void testCreateCustomer() throws ConstraintsViolationException {
        CustomerDTO customer = new CustomerDTO();
        customer.setTitle("Mock");
        when(customerService.createCustomer(any(CustomerDO.class))).thenReturn(new CustomerDO());
        assertNotNull(controller.createCustomer(customer));
    }

    @Test
    void testUpdateCustomer() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        CustomerDTO customer = new CustomerDTO();
        customer.setTitle("Mock");
        doNothing().when(customerService).updateCustomer(any(UUID.class), any(CustomerDO.class));
        controller.updateCustomer(customerId, customer);
        verify(customerService, times(1)).updateCustomer(any(UUID.class), any(CustomerDO.class));
    }

    @Test
    void testDeleteCustomer() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        doNothing().when(customerService).deleteCustomer(customerId);
        controller.deleteCustomer(customerId);
        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void testFindCustome() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        when(customerService.findCustomer(customerId)).thenReturn(new CustomerDO());
        assertNotNull(controller.findCustomer(customerId));
    }
}
