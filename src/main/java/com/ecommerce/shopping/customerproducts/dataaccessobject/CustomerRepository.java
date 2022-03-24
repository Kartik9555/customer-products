package com.ecommerce.shopping.customerproducts.dataaccessobject;

import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerDO, UUID> {
}
