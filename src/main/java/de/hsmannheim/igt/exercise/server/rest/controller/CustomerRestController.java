package de.hsmannheim.igt.exercise.server.rest.controller;


import de.hsmannheim.igt.exercise.controller.CustomerController;
import de.hsmannheim.igt.exercise.controller.DistrictController;
import de.hsmannheim.igt.exercise.models.Customer;
import de.hsmannheim.igt.exercise.models.District;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("/customer")
public class CustomerRestController {


    @RequestMapping(value="/createCustomer",method = RequestMethod.GET)
    public @ResponseBody
    Customer createCustomer(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "district", required = true) String district) {
        CustomerController custController = new CustomerController();
        Customer c = new Customer();
        DistrictController districtController = new DistrictController();
        District d = districtController.getDistrictByName(district);
        c.setC_NAME(name);
        c.setC_DISTRICT(d);
        c = custController.createCustomer(c);
        return c;
    }

    @RequestMapping(value="/getCustomer",method = RequestMethod.GET)
    public @ResponseBody
    Customer getCustomer(@RequestParam(value = "id", required = true) String id) {
        CustomerController custController = new CustomerController();
        Customer c = custController.getCustomer(id);
        return c;
    }
    

    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteCustomer(@RequestParam(value = "id", required = true) String id) {
        CustomerController customerController = new CustomerController();
        boolean wasDeleted = customerController.deleteCustomer(String.valueOf(id));
        if (wasDeleted) {
            return String.format("Customer with id %s was deleted successfully!\n", id);
        } else {
            return String.format("Customer with id %s could NOT be deleted!\n", id);
        }
    }

    @RequestMapping(value = "/updateCustomerName", method = RequestMethod.PUT)
    public @ResponseBody
    String updateCustomerName(@RequestParam(value = "id") String id, @RequestParam(value = "newname") String newName) {
        CustomerController customerController = new CustomerController();
        Customer customer = customerController.getCustomer(id);
        if (customer == null) {
            return "The given customer id was NOT found!";
        }
        customer.setC_NAME(newName);
        customerController.updateCustomer(id, customer);
        return String.format("Customer with ID %s updated!\n", id);
    }

    @RequestMapping(value = "/getAllCustomer", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Customer> getAllCustomer() {
        CustomerController customerController = new CustomerController();
        return customerController.getAllCustomer();
    }

}
