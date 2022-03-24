package com.ecommerce.shopping.customerproducts.dataaccessobject;

import com.ecommerce.shopping.customerproducts.domainobject.ProductDO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepository extends PagingAndSortingRepository<ProductDO, UUID> {
}
