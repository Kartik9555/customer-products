package com.ecommerce.shopping.customerproducts.controller;

import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.mapper.ProductMapper;
import com.ecommerce.shopping.customerproducts.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")
@Api(value = "Products",tags = {"Products"})
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/customers/{customerId}/products")
    @ApiOperation(value = "Return paginated list of customer products", httpMethod = "GET")
    public List<ProductDTO> getProductsByCustomer(@PathVariable UUID customerId, @RequestParam(defaultValue = "20") Integer pageSize) throws EntityNotFoundException {
        return productService.findAllProducts(customerId, pageSize);
    }

    @PostMapping(value = "/customers/{customerId}/products")
    @ApiOperation(value = "Create a new product for customer", httpMethod = "POST")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@PathVariable UUID customerId, @Valid @RequestBody ProductDTO product) throws EntityNotFoundException, ConstraintsViolationException {
        ProductDO productDO = ProductMapper.makeProductDO(product);
        return ProductMapper.makeProductDTO(productService.create(customerId, productDO));
    }

    @PutMapping("/products/{productId}")
    @ApiOperation(value = "Edit product", httpMethod = "PUT")
    public void updateProduct(@PathVariable UUID productId, @Valid @RequestBody ProductDTO productDTO) throws EntityNotFoundException {
        ProductDO productDO = ProductMapper.makeProductDO(productDTO);
        productService.updateProduct(productId, productDO);
    }

    @DeleteMapping("/products/{productId}")
    @ApiOperation(value = "Delete product", httpMethod = "DELETE")
    public void deleteProduct(@PathVariable UUID productId) throws EntityNotFoundException {
        productService.delete(productId);
    }

    @GetMapping("/products/{productId}")
    @ApiOperation(value = "Return product by id", httpMethod = "GET")
    public ProductDTO findProduct(@PathVariable UUID productId) throws EntityNotFoundException {
        return ProductMapper.makeProductDTO(productService.find(productId));
    }

}
