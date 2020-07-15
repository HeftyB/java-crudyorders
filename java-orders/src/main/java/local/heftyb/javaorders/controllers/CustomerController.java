package local.heftyb.javaorders.controllers;

import local.heftyb.javaorders.models.Customer;
import local.heftyb.javaorders.services.CustomerService;
import local.heftyb.javaorders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllOrders ()
    {
        List<Customer> allCustomers = customerService.findAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{id}", produces = {"application/json"})
    public ResponseEntity<?> listCustomerById (@PathVariable Long id)
    {
        Customer cust = customerService.findCustomerById(id);
        return new ResponseEntity<>(cust, HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{name}", produces = {"application/json"})
    public ResponseEntity<?> listCustomersLike (@PathVariable String name)
    {
        List<Customer> likeCustomers = customerService.findByNameLike(name);
        return new ResponseEntity<>(likeCustomers, HttpStatus.OK);
    }

    @GetMapping(value = "/orders/count", produces = {"application/json"})
    public ResponseEntity<?> listCustomerOrderCount ()
    {
        List<OrderCount> list = customerService.getOrderCounts();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = {"application/json"})
    public ResponseEntity<?> addNewCustomer (@Valid @RequestBody Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custcode}").buildAndExpand(newCustomer.getCustcode())
            .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{customerid}", consumes = {"application/json"})
    public ResponseEntity<?> updateWholeCustomer (@Valid @RequestBody Customer customer, @PathVariable long customerid)
    {
        customer.setCustcode(customerid);
        customerService.save(customer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{customerid}", consumes = {"application/json"})
    public ResponseEntity<?> updateCustomer (@RequestBody Customer customer, @PathVariable long customerid)
    {
        customerService.update(customer, customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{customerid}")
    public ResponseEntity<?> deleteCustomer (@PathVariable long customerid)
    {
        customerService.delete(customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
