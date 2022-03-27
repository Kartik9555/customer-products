package com.ecommerce.shopping.customerproducts.mapper;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerDTO makeCustomerDTO(CustomerDO customerDO) {
        return new CustomerDTO(customerDO.getId(), customerDO.getTitle(), customerDO.getCreatedAt(), customerDO.getModifiedAt());
    }

    public static CustomerDO makeCustomerDO(CustomerDTO customer) {
        return new CustomerDO(customer.getTitle());
    }

}
