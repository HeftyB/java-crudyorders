package local.heftyb.javaorders.controllers;

import local.heftyb.javaorders.models.Order;
import local.heftyb.javaorders.services.OrderService;
import local.heftyb.javaorders.views.OutstandingAdvance;
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
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    OrderService orderService;

    @GetMapping(value = "/order/{id}", produces = {"application/json"})
    public ResponseEntity<?> listOrderById (@PathVariable long id)
    {
        Order order = orderService.findOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value = "/advanceamount", produces = {"application/json"})
    public ResponseEntity<?> listOutstandingAdvances ()
    {
        List <OutstandingAdvance> list = orderService.findAllOutstandingAdvance();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/order", consumes = {"application/json"})
    public ResponseEntity<?> addNewOrder (@Valid @RequestBody Order order)
    {
        order.setOrdnum(0);
        order = orderService.save(order);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ordnum}").buildAndExpand(order.getOrdnum()).toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{ordnum}", consumes = {"application/json"})
    public ResponseEntity<?> updateOrder (@Valid @RequestBody Order order, @PathVariable long ordnum)
    {
        order.setOrdnum(ordnum);
        orderService.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrder (@PathVariable long ordnum)
    {
        orderService.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
