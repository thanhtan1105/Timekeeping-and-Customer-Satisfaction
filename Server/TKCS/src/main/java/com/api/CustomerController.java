package com.api;

import com.entity.Customer;
import com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lethanhtan on 9/1/16.
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/getLastName", method = RequestMethod.GET)
    @ResponseBody
    public String getLastName(String lastname) {

        List<Customer> customers = customerService.findByLastName(lastname);
        String result = "";
        for (Customer customer : customers) {
            result += " " + customer.toString();
        }
        return result;
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create(String firstname, String lastname) {
        Customer newCustomer = customerService.save(firstname, lastname);
        return newCustomer.toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(String id) {
        Long newId = Long.valueOf(id);
        return customerService.deleteById(newId);
    }

    @RequestMapping("/all")
    public List<Customer> findAll(){
        return customerService.findAll();
    }

}
