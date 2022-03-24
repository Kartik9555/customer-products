package com.ecommerce.shopping.customerproducts.service.impl;

import com.ecommerce.shopping.customerproducts.dataaccessobject.ProductRepository;
import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.mapper.ProductMapper;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import com.ecommerce.shopping.customerproducts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some customer products specific things.
 * <p/>
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CustomerService customerService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CustomerService customerService) {
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    /**
     * Find product by productId.
     *
     * @param productId of the product
     */
    @Override
    public ProductDO find(UUID productId) throws EntityNotFoundException {
        return findProduct(productId);
    }

    /**
     * Creates a new product for a customer.
     *
     * @param customerId of the customer
     * @param productDO details of the product
     * @return new created product
     * @throws EntityNotFoundException if a customer does not exist .
     */
    @Override
    @Transactional
    public ProductDO create(UUID customerId, ProductDO productDO) throws ConstraintsViolationException, EntityNotFoundException {
        CustomerDO customer = customerService.find(customerId);
        ProductDO product;
        try {
            productDO.setCustomer(customer);
            product = productRepository.save(productDO);
        } catch (DataIntegrityViolationException e){
            throw new ConstraintsViolationException(e.getMessage());
        }
        return product;
    }

    /**
     * Deletes an existing product by id.
     *
     * @param productId of the product
     * @throws EntityNotFoundException if no product with the given id was found.
     */
    @Override
    @Transactional
    public void delete(UUID productId) throws EntityNotFoundException {
        ProductDO product = findProduct(productId);
        product.setIsDeleted(true);
    }

    /**
     * Find all products for a customer.
     *
     * @param customerId of the customer
     * @param pageSize of the no of records to return
     * @throws EntityNotFoundException if no customer with the given id was found.
     */
    @Override
    @Transactional
    public List<ProductDTO> findAllProducts(UUID customerId, Integer pageSize) throws EntityNotFoundException {
        CustomerDO customer = customerService.find(customerId);
        Set<ProductDO> products = customer.getProducts();
        return ProductMapper.makeProductDTOList(products.stream().limit(pageSize.longValue()).collect(Collectors.toList()));
    }

    /**
     * Update the details of the product.
     *
     * @param productId of the product
     * @param productDO product details to update
     * @throws EntityNotFoundException if no product with the given id was found.
     */
    @Override
    @Transactional
    public void updateProduct(UUID productId, ProductDO productDO) throws EntityNotFoundException {
        ProductDO product = findProduct(productId);
        product.setTitle(productDO.getTitle());
        product.setDescription(productDO.getDescription());
        product.setPrice(productDO.getPrice());
        product.setModifiedAt(ZonedDateTime.now());
    }

    private ProductDO findProduct(UUID productId) throws EntityNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + productId));
    }
}
