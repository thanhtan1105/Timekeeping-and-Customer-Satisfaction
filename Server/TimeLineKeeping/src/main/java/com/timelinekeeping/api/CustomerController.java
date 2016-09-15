package com.timelinekeeping.api;

import com.timelinekeeping.entity.CustomerEntity;
import com.timelinekeeping.service.CustomerService;
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

        List<CustomerEntity> customerEntities = customerService.findByLastName(lastname);
        String result = "";
        for (CustomerEntity customerEntity : customerEntities) {
            result += " " + customerEntity.toString();
        }
        return result;
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create(String firstname, String lastname) {
        CustomerEntity newCustomerEntity = customerService.save(firstname, lastname);
        return newCustomerEntity.toString();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(String id) {
        Long newId = Long.valueOf(id);
        return customerService.deleteById(newId);
    }

    @RequestMapping("/all")
    public List<CustomerEntity> findAll(){
        return customerService.findAll();
    }

}
