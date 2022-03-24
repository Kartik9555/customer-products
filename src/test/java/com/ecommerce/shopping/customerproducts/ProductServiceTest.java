package com.ecommerce.shopping.customerproducts;

import com.ecommerce.shopping.customerproducts.dataaccessobject.ProductRepository;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import com.ecommerce.shopping.customerproducts.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CustomerService customerService;

    @Test
    void testFindProductByProductIdOK() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDO product = new ProductDO();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Assertions.assertEquals(product, productService.find(productId));
    }

    @Test
    void testFindProductByProductIdKO() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.find(productId));
    }

    @Test
    void testCreateProductOK() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        ProductDO productDO = new ProductDO();
        when(customerService.find(customerId)).thenReturn(customer);
        when(productRepository.save(productDO)).thenReturn(productDO);
        productService.create(customerId, productDO);
        assertNotNull(customerId);
    }

    @Test
    void testCreateProductCustomerKO() throws Exception {
        UUID customerId = UUID.randomUUID();
        ProductDO productDO = new ProductDO();
        when(customerService.find(customerId)).thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class, () -> productService.create(customerId, productDO));
    }

    @Test
    void testCreateProductKO() throws Exception {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        ProductDO productDO = new ProductDO();
        when(customerService.find(customerId)).thenReturn(customer);
        when(productRepository.save(productDO)).thenThrow(new DataIntegrityViolationException("MOCK"));
        assertThrows(ConstraintsViolationException.class, () -> productService.create(customerId, productDO));
    }

    @Test
    void deleteProductOK() throws EntityNotFoundException {
        UUID productId = UUID.randomUUID();
        ProductDO productDO = new ProductDO();
        when(productRepository.findById(productId)).thenReturn(Optional.of(productDO));
        productService.delete(productId);
        assertNotNull(productId);
    }

    @Test
    void deleteProductKO() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->productService.delete(productId));
    }

    @Test
    void testFindAllProductByCustomerOK() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        CustomerDO customer = new CustomerDO();
        customer.setProducts(Collections.singleton(new ProductDO()));
        when(customerService.find(customerId)).thenReturn(customer);
        assertNotNull(productService.findAllProducts(customerId, 20));
    }

    @Test
    void testFindAllProductByCustomerKO() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        when(customerService.find(customerId)).thenThrow(new EntityNotFoundException("MOCK"));
        assertThrows(EntityNotFoundException.class, () -> productService.findAllProducts(customerId, 20));
    }

    @Test
    void testUpdateProductOK() throws EntityNotFoundException {
        UUID productId = UUID.randomUUID();
        ProductDO product = new ProductDO();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productService.updateProduct(productId, product);
        assertNotNull(productId);
    }

    @Test
    void testUpdateProductKO() {
        UUID productId = UUID.randomUUID();
        ProductDO product = new ProductDO();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(productId, product));
    }

}
