package com.varshith.controller;


import com.varshith.entities.Customer;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.records.CustomerUpdateRequest;
import com.varshith.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private  final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public List<Customer> getCustomers (){
        return  customerService.getAllCustomers();
    }
    @GetMapping("{customerId}")
    public Customer getCustomer (@PathVariable Integer customerId){
        return  customerService.getCustomerById(customerId);
    }

    @PostMapping("")
    public void  addCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.addCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("{customerId}")
    public  void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }


    @PutMapping("{customerId}")
    public void  updateCustomer
            (@PathVariable Integer customerId ,
             @RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(customerId,customerUpdateRequest);
    }


}
