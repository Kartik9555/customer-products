package com.ecommerce.shopping.customerproducts.controller;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.domainobject.CustomerDO;
import com.ecommerce.shopping.customerproducts.exception.ConstraintsViolationException;
import com.ecommerce.shopping.customerproducts.exception.EntityNotFoundException;
import com.ecommerce.shopping.customerproducts.mapper.CustomerMapper;
import com.ecommerce.shopping.customerproducts.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/customers")
@Api(value = "Customers",tags = {"Customers"})
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ApiOperation(value = "Return paginated list of all customers", httpMethod = "GET")
    public List<CustomerDTO> findAll(@RequestParam(defaultValue = "20") Integer pageSize){
        return customerService.findAllCustomers(pageSize);
    }

    @PostMapping
    @ApiOperation(value = "Create a new customer", httpMethod = "POST")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws ConstraintsViolationException {
        CustomerDO customerDO = CustomerMapper.makeCustomerDo(customerDTO);
        return CustomerMapper.makeCustomerDTO(customerService.create(customerDO));
    }

    @PutMapping("/{customerId}")
    @ApiOperation(value = "Edit customer", httpMethod = "PUT")
    public void updateCustomer(@PathVariable UUID customerId, @Valid @RequestBody CustomerDTO customerDTO) throws EntityNotFoundException {
        CustomerDO customerDO = CustomerMapper.makeCustomerDo(customerDTO);
        customerService.updateCustomer(customerId, customerDO);
    }

    @DeleteMapping("/{customerId}")
    @ApiOperation(value = "Delete customer", httpMethod = "DELETE")
    public void deleteCustomer(@PathVariable UUID customerId) throws EntityNotFoundException {
        customerService.delete(customerId);
    }

    @GetMapping("/{customerId}")
    @ApiOperation(value = "Return customer by id", httpMethod = "GET")
    public CustomerDTO findCustomer(@PathVariable UUID customerId) throws EntityNotFoundException {
        CustomerDO customerDO = customerService.find(customerId);
        return CustomerMapper.makeCustomerDTO(customerDO);
    }
}
