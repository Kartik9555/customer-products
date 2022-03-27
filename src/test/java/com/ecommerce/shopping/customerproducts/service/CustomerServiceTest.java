package com.ecommerce.shopping.customerproducts.service;

import com.ecommerce.shopping.customerproducts.dataaccessobject.CustomerRepository;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        assertEquals(customerDO, customerService.findCustomer(customerId));
    }

    @Test
    void testCreateCustomerOK() throws Exception {
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.save(customerDO)).thenReturn(customerDO);
        assertEquals(customerDO, customerService.createCustomer(customerDO));
    }

    @Test
    void testCreateCustomerKO() {
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.save(customerDO)).thenThrow(new DataIntegrityViolationException("Test Message"));
        assertThrows(ConstraintsViolationException.class, () -> customerService.createCustomer(customerDO));
    }

    @Test
    void testDeleteCustomerOK() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerDO));
        customerService.deleteCustomer(customerId);
        assertNotNull(customerId);
    }

    @Test
    void testDeleteCustomerKO() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenThrow(new DataIntegrityViolationException("Test Message"));
        assertThrows(DataIntegrityViolationException.class, () -> customerService.deleteCustomer(customerId));
    }

    @Test
    void testFindAllCustomerOK() {
        List<CustomerDO> customers = new ArrayList<>();
        customers.add(new CustomerDO());
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(customers));
        assertNotNull(customerService.findAllCustomers(20));
    }

    @Test
    void testFindAllCustomerNoDataOK() {
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

    @Test
    void testFindProductByProductIdOK() throws EntityNotFoundException {
        UUID productId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        ProductDO product = new ProductDO();
        product.setId(productId);
        customer.setProducts(Collections.singleton(product));
        when(customerRepository.findByProductId(productId)).thenReturn(Optional.of(customer));
        ProductDO found = customerService.findProduct(productId);
        assertEquals(productId, found.getId());
    }

    @Test
    void testFindProductByProductIdKO() {
        UUID productId = UUID.randomUUID();
        when(customerRepository.findByProductId(productId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.findProduct(productId));
    }

    @Test
    void testCreateProductWithExistingProductOK() throws EntityNotFoundException, ConstraintsViolationException {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        customer.setId(customerId);
        ProductDO product = new ProductDO();
        UUID productId = UUID.randomUUID();
        product.setId(productId);
        ProductDO productNew = new ProductDO();
        productNew.setTitle("MOCK");
        productNew.setDescription("Moc");
        productNew.setPrice(BigDecimal.TEN);
        Set<ProductDO> products = new HashSet<>();
        products.add(product);
        customer.setProducts(products);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        assertNotNull(customerService.createProduct(customerId, productNew));
    }

    @Test
    void testCreateProductWithOutProductOK() throws EntityNotFoundException, ConstraintsViolationException {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        customer.setId(customerId);
        ProductDO productNew = new ProductDO();
        productNew.setTitle("MOCK");
        productNew.setDescription("Moc");
        productNew.setPrice(BigDecimal.TEN);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        assertNotNull(customerService.createProduct(customerId, productNew));
    }

    @Test
    void testCreateProductWithOutProductKO() {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        customer.setId(customerId);
        ProductDO productNew = new ProductDO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.createProduct(customerId, productNew));
    }

    @Test
    void testCreateProductWithSaveErrorKO() {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        customer.setId(customerId);
        ProductDO productNew = new ProductDO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenThrow(new DataIntegrityViolationException("TEST"));
        assertThrows(ConstraintsViolationException.class, () -> customerService.createProduct(customerId, productNew));
    }

    @Test
    void testDeleteProductOK() throws EntityNotFoundException {
        CustomerDO customer = new CustomerDO();
        UUID productId = UUID.randomUUID();
        ProductDO product = new ProductDO();
        product.setId(productId);
        Set<ProductDO> products = new HashSet<>();
        products.add(product);
        customer.setProducts(products);
        when(customerRepository.findByProductId(productId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        customerService.deleteProduct(productId);
        verify(customerRepository, times(1)).findByProductId(productId);
    }

    @Test
    void testDeleteProductNotFoundKO() {
        UUID productId = UUID.randomUUID();
        when(customerRepository.findByProductId(productId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.deleteProduct(productId));
    }

    @Test
    void testFindAllProductsNoDataOK() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        CustomerDO customerDO = new CustomerDO();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerDO));
        assertNotNull(customerService.findAllProducts(customerId, 20));
    }

    @Test
    void testFindAllProductsWithDataOK() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        CustomerDO customerDO = new CustomerDO();
        Set<ProductDO> products = new HashSet<>();
        products.add(new ProductDO());
        customerDO.setProducts(products);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerDO));
        assertNotNull(customerService.findAllProducts(customerId, 20));
    }

    @Test
    void testFindAllProductsCustomerNotFoundKO() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.findAllProducts(customerId, 20));
    }

    @Test
    void testUpdateProductOK() throws EntityNotFoundException {
        UUID pruductId = UUID.randomUUID();
        ProductDO product = new ProductDO();
        product.setId(pruductId);
        CustomerDO customerDO = new CustomerDO();
        Set<ProductDO> products = new HashSet<>();
        products.add(product);
        customerDO.setProducts(products);
        when(customerRepository.findByProductId(pruductId)).thenReturn(Optional.of(customerDO));
        when(customerRepository.save(customerDO)).thenReturn(customerDO);
        customerService.updateProduct(pruductId, product);
        verify(customerRepository, times(1)).findByProductId(pruductId);
    }

    @Test
    void testUpdateProductKO() {
        UUID pruductId = UUID.randomUUID();
        ProductDO product = new ProductDO();
        when(customerRepository.findByProductId(pruductId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.updateProduct(pruductId, product));
    }
}
