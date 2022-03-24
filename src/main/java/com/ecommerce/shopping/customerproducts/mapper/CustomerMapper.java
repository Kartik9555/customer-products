package com.ecommerce.shopping.customerproducts.mapper;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerDTO makeCustomerDTO(CustomerDO customerDO){
        return new CustomerDTO(customerDO.getId(), customerDO.getTitle(), customerDO.getCreatedAt(), customerDO.getModifiedAt());
    }

    public static CustomerDO makeCustomerDo(CustomerDTO customer){
        return new CustomerDO(customer.getTitle());
    }

    public static List<CustomerDTO> makeCustomerDTOList(Collection<CustomerDO> customers){
        return customers.stream()
                .map(CustomerMapper::makeCustomerDTO)
                .collect(Collectors.toList());
    }

}
