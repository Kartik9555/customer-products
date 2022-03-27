package com.ecommerce.shopping.customerproducts.controller;

import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController controller;

    @MockBean
    private CustomerService customerService;

    @Test
    void testGetProductsByCustomer() throws EntityNotFoundException {
        UUID customerId = UUID.randomUUID();
        when(customerService.findAllProducts(customerId, 20)).thenReturn(new ArrayList<>());
        assertNotNull(controller.getProductsByCustomer(customerId, 20));
    }

    @Test
    void testCreateProduct() throws EntityNotFoundException, ConstraintsViolationException {
        UUID customerId = UUID.randomUUID();
        ProductDTO dto = new ProductDTO();
        dto.setTitle("Mock");
        dto.setDescription("Mock");
        dto.setPrice(BigDecimal.TEN);
        when(customerService.createProduct(any(UUID.class), any(ProductDO.class))).thenReturn(new ProductDO());
        assertNotNull(controller.createProduct(customerId, dto));
    }

    @Test
    void testUpdateProduct() throws EntityNotFoundException {
        UUID productId = UUID.randomUUID();
        ProductDTO dto = new ProductDTO();
        dto.setTitle("Mock");
        dto.setDescription("Mock");
        dto.setPrice(BigDecimal.TEN);
        doNothing().when(customerService).updateProduct(any(UUID.class), any(ProductDO.class));
        controller.updateProduct(productId, dto);
        verify(customerService, times(1)).updateProduct(any(UUID.class), any(ProductDO.class));
    }

    @Test
    void testDeleteProduct() throws EntityNotFoundException {
        UUID productId = UUID.randomUUID();
        doNothing().when(customerService).deleteProduct(productId);
        controller.deleteProduct(productId);
        verify(customerService, times(1)).deleteProduct(productId);
    }

    @Test
    void testFindProduct() throws EntityNotFoundException {
        UUID productId = UUID.randomUUID();
        when(customerService.findProduct(productId)).thenReturn(new ProductDO());
        assertNotNull(controller.findProduct(productId));
    }
}
