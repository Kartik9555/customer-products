package com.ecommerce.shopping.customerproducts.dataaccessobject;

import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<CustomerDO, UUID> {

    @Query("{'products.id': ?0}")
    Optional<CustomerDO> findByProductId(UUID productId);
}
