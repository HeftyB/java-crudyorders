package local.heftyb.javaorders.services;

import local.heftyb.javaorders.models.Order;
import local.heftyb.javaorders.models.Payment;
import local.heftyb.javaorders.repositories.OrderRepository;
import local.heftyb.javaorders.repositories.PaymentRepository;
import local.heftyb.javaorders.views.OutstandingAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class OrderServiceImp implements OrderService
{

    @Autowired
    OrderRepository ordrepo;

    @Autowired
    PaymentRepository payrepo;

    @Override
    public Order findOrderById(long id) throws EntityNotFoundException
    {
        return ordrepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
    }

    @Override
    public List<OutstandingAdvance> findAllOutstandingAdvance()
    {
        return ordrepo.findAllOutstandingAdvance();
    }

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            ordrepo.findById(order.getOrdnum()).orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found!"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setCustomer(order.getCustomer());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        for (Payment p :
            order.getPayments())

        {
            Payment pay = payrepo.findById(p.getPaymentid()).orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
            newOrder.addPayments(pay);
            pay.addOrders(newOrder);
        }

        return ordrepo.save(newOrder);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (ordrepo.findById(id).isPresent())
        {
            ordrepo.deleteById(id);
        } else
        {
            throw new  EntityNotFoundException("Order " + id + " Not Found!");
        }
    }
}
